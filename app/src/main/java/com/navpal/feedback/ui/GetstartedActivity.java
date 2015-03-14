package com.navpal.feedback.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.navpal.feedback.R;
import com.navpal.feedback.util.Utils;
import com.squareup.okhttp.internal.Util;

/**
 * A login screen that offers login via email/password.
 */
public class GetstartedActivity extends Activity {

    AutoCompleteTextView fullName;
    AutoCompleteTextView email;
    EditText password;
    Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        fullName = (AutoCompleteTextView)findViewById(R.id.fullname);
        email = (AutoCompleteTextView)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password); //Not using right now
        signIn = (Button)findViewById(R.id.email_sign_in_button);

        email.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signIn.performClick();
                    return true;
                }
                return false;
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullName.toString()!=null && !fullName.toString().isEmpty() &&
                        Utils.isValidEmail(email.getText().toString())){
                        Intent intent = new Intent(GetstartedActivity.this,Home.class);
                        startActivity(intent);
                }else{
                    Utils.showToast(GetstartedActivity.this,"Please Enter valid details");
                }
            }
        });
    }
}



