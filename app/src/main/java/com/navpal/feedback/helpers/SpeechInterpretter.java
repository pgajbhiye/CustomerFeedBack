package com.navpal.feedback.helpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;

import com.navpal.feedback.R;

import java.util.Locale;

/**
 * Created by fissionlabs on 15-03-2015.
 */
public class SpeechInterpretter {
    private int REQ_CODE_SPEECH_INPUT = 100;
    private Context context;

    public SpeechInterpretter(Context context, int code){
        this.REQ_CODE_SPEECH_INPUT = code;
        this.context = context;
    }

    /**
     * Showing google speech input dialog
     * */
    public Intent promptSpeechInput()  {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
        intent.putExtra("android.speech.extra.GET_AUDIO", true);
        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra (RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        return intent;
        //context.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
    }

}
