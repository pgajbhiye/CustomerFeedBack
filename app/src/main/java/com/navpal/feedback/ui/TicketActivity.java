package com.navpal.feedback.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.navpal.feedback.R;
import com.navpal.feedback.util.Config;

import java.io.ByteArrayOutputStream;


public class TicketActivity extends Activity {

    private static final String  LOG_TAG = TicketActivity.class.getName();
    private static final int CAMERA_REQUEST =111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        //captureImage();
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this,CreateNewTicketActivity.class);
                startActivity(intent);
            }
        });

        Button launchAudioRecord = (Button)findViewById(R.id.launchAudioRecord);
        launchAudioRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this,AudioFeedbackActivity.class);
                startActivity(intent);
            }
        });

        Button launchhome = (Button)findViewById(R.id.launchhome);
        launchhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createNewTicket();
                Intent intent = new Intent(TicketActivity.this,Home.class);
                startActivity(intent);
            }
        });

      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void captureImage(){
        //Check if the user has camera
        //hasSystemFeature(PackageManager.FEATURE_CAMERA)
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //Uri tempUri = getImageUri(getApplicationContext(), photo);
            if(photo!=null) {
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(photo, Config.THUMBNAIL_W, Config.THUMBNAIL_H);
            }else{
                Log.e(LOG_TAG,"Captured bitmap is null");
                Toast.makeText(this,"Captured bitmap is null",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
