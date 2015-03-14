package com.navpal.feedback.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.navpal.feedback.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Locale;

public class AudioFeedbackActivity extends Activity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final String LOG_TAG = AudioFeedbackActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_feedback);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    /**
     * Showing google speech input dialog
     * */
    /*private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        intent.putExtra("android.speech.extra.GET_AUDIO", true);
        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra (RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName ());
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }*/

    private void promptSpeechInput(){
        boolean available = SpeechRecognizer.isRecognitionAvailable(this);
        if (available) {
            SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(this);
            sr.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onResults(Bundle bundle) {
                    ArrayList<String> result = bundle.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    // process results here
                }
                // define your other overloaded listener methods here
            });

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
            intent.putExtra("android.speech.extra.GET_AUDIO", true);
            intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
            //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //intent.putExtra (RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName ());
            //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());

            // start playback of audio clip here

            // this will start the speech recognizer service in the background
            // without starting a separate activity
            sr.startListening(intent);
        }
    }

    /**
     * Receiving speech input
     * */
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
                    txtSpeechInput.setText(result.get(0));

                    // the recording url is in getData:
                    Uri audioUri = data.getData();
                    ContentResolver contentResolver = getContentResolver();
                    try {
                        Log.d(LOG_TAG, "getting content provider"+audioUri);
                        InputStream filestream = contentResolver.openInputStream(audioUri);
                        SaveAudioFileToStorage(filestream);
                    } catch (Exception e){
                        Log.d(LOG_TAG, e.getMessage());
                    }

                    // TODO: read audio file from inputstream
                }
                break;
            }

        }
    }

    private void SaveAudioFileToStorage(InputStream filestream) throws Exception{
        try {
            final File file = new File(getCacheDir(), System.currentTimeMillis()+".amr");
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
            Log.d(LOG_TAG, "New file crated "+file.length());
        } finally {
            filestream.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio_feedback, menu);
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
}
