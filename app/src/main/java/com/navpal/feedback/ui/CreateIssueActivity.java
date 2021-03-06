package com.navpal.feedback.ui;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.navpal.feedback.R;
import com.navpal.feedback.helpers.SpeechInterpretter;
import com.navpal.feedback.helpers.ZendeskConnector;
import com.navpal.feedback.util.Config;
import com.navpal.feedback.util.Utils;
import com.squareup.okhttp.internal.Util;
import com.zendesk.sdk.model.network.ErrorResponse;
import com.zendesk.sdk.network.impl.ZendeskCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CreateIssueActivity extends ActionBarActivity {

    AutoCompleteTextView subject;
    EditText description;
    Button submitNewTicket;
    TextView newpriority;
    ImageButton btnSpeak, btnCapture, btnImgDel;
    SpeechInterpretter speechInterpretter;
    static final int REQ_CODE_SPEECH_INPUT = 100;
    static final int CAMERA_REQUEST =111;
    File imageAttchment, audioAttchment;
    LinearLayout imgCaptureCntr;
    RelativeLayout imgPrevCntr;
    ImageView imgThumb;

    ZendeskConnector zendeskConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createissue);
        setUpActionBar();
        subject = (AutoCompleteTextView) findViewById(R.id.subject);
        description = (EditText) findViewById(R.id.description);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);

        imgCaptureCntr = (LinearLayout)findViewById(R.id.imgCaptureCntr);
        imgPrevCntr = (RelativeLayout)findViewById(R.id.imgPrevCntr);
        imgThumb = (ImageView) findViewById(R.id.imgThumb);
        btnImgDel = (ImageButton) findViewById(R.id.btnImgDel);

        submitNewTicket = (Button) findViewById(R.id.submitNewTicket);
        submitNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidDetails()) {
                    submitNewTicket();
                }
            }
        });

        final String [] items = getResources().getStringArray(R.array.ticket_priority_value);
        newpriority = (TextView) findViewById(R.id.newpriority);
        newpriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateIssueActivity.this);
                builder.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                newpriority.setText(items[which]);
                                newpriority.setTextColor(Color.BLACK);
                            }
                        });
                builder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        speechInterpretter = new SpeechInterpretter(CreateIssueActivity.this, REQ_CODE_SPEECH_INPUT);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(speechInterpretter.promptSpeechInput(), REQ_CODE_SPEECH_INPUT);
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnImgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImageAttachment();
            }
        });

        zendeskConnector = new ZendeskConnector(CreateIssueActivity.this);//.initZendeskSdk(CreateIssueActivity.this);

    }

    private void submitNewTicket() {
        String ts = subject.getText().toString(),
                tp = newpriority.getText().toString(),
                td = description.getText().toString();

        /*if(audioAttchment!=null){
            Utils.showToast(getApplicationContext(), "Audio file is available");
        }*/

        zendeskConnector.createTicket(CreateIssueActivity.this,ts,td,imageAttchment, new ZendeskCallback() {
            @Override
            public void onSuccess(Object o) {
                finish();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {

            }
        });

    }

    private boolean isValidDetails() {
        if (subject.getText().toString().trim().length() == 0) {
            subject.setFocusable(true);
            subject.setFocusableInTouchMode(true);
            Utils.showToast(CreateIssueActivity.this, "Enter subject");
            return false;
        }

        String tp = newpriority.getText().toString().trim();
        if (tp.length() == 0 || tp.equalsIgnoreCase("Select Priority")) {
            Utils.showToast(CreateIssueActivity.this, "Enter priority");
            return false;
        }

        if (description.getText().toString().trim().length() == 0) {
            description.setFocusable(true);
            description.setFocusableInTouchMode(true);
            Utils.showToast(CreateIssueActivity.this, "Enter description");
            return false;
        }

        return true;
    }

    private void removeImageAttachment(){
        imageAttchment = null;
        imgCaptureCntr.setVisibility(View.VISIBLE);
        imgPrevCntr.setVisibility(View.GONE);
    }

    private void renderImageThumbnail(Bitmap bitmap){
        Drawable d =new BitmapDrawable(getResources(),bitmap);
        imgCaptureCntr.setVisibility(View.GONE);
        imgPrevCntr.setVisibility(View.VISIBLE);
        imgThumb.setImageDrawable(d);
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    //ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));

                    // the resulting text is in the getExtras:
                    Bundle bundle = data.getExtras();
                    ArrayList<String> result = bundle.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
                    description.setText(result.get(0));

                    // the recording url is in getData:
                    Uri audioUri = data.getData();
                    if (audioUri == null) {
                        return;
                    }
                    ContentResolver contentResolver = getContentResolver();
                    try {
                        InputStream filestream = contentResolver.openInputStream(audioUri);
                        File file = SaveFileToStorage(filestream, ".amr");
                        if(file!=null && file.length()>0){
                            audioAttchment = file;
                        }
                    } catch (Exception e) {
                    }
                }
                break;
            }
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK && null != data) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = Utils.getImageUri(getApplicationContext(), photo);
                    if (photo != null) {
                        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(photo, Config.THUMBNAIL_W, Config.THUMBNAIL_H);
                        renderImageThumbnail(thumbnail);
                    }
                    if (tempUri == null) {
                        return;
                    }
                    ContentResolver contentResolver = getContentResolver();
                    try {
                        InputStream filestream = contentResolver.openInputStream(tempUri);
                        File file = SaveFileToStorage(filestream, ".jpg");
                        if(file!=null && file.length()>0){
                            imageAttchment = file;
                        }
                    } catch (Exception e) {
                    }
                }
                break;
            }


        }
    }

    private File SaveFileToStorage(InputStream filestream, String extension) throws Exception {
        try {
            final File file = new File(getCacheDir(), System.currentTimeMillis() + extension);
            final OutputStream output = new FileOutputStream(file);
            try {
                try {
                    final byte[] buffer = new byte[1024];
                    int read;

                    while ((read = filestream.read(buffer)) != -1)
                        output.write(buffer, 0, read);

                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        } finally {
            filestream.close();
        }
    }


    private void setUpActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
