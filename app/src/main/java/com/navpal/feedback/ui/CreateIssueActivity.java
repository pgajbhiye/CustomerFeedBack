package com.navpal.feedback.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.navpal.feedback.R;
import com.navpal.feedback.util.Utils;
import com.squareup.okhttp.internal.Util;

public class CreateIssueActivity extends ActionBarActivity {

    AutoCompleteTextView subject;
    EditText description;
    Spinner priority;
    Button submitNewTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createissue);
        subject = (AutoCompleteTextView)findViewById(R.id.subject);
        description = (EditText)findViewById(R.id.description);
        priority = (Spinner) findViewById(R.id.priority);
        priority.setPrompt(getString(R.string.ticket_priority));
        submitNewTicket = (Button) findViewById(R.id.submitNewTicket);
        submitNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(isValidDetails()){
                      submitNewTicket();
                  }
            }
        });
    }

    private void submitNewTicket(){
           String ts = subject.getText().toString(),
                   tp = priority.getSelectedItem().toString(),
                   td = description.getText().toString();

        Utils.showToast(CreateIssueActivity.this, "Ticket Created successfully");

    }

    private boolean isValidDetails(){
        if(subject.getText().toString().trim().length()==0){
            subject.setFocusable(true);
            subject.setFocusableInTouchMode(true);
            Utils.showToast(CreateIssueActivity.this, "Enter subject");
            return false;
        }

        String tp = priority.getSelectedItem().toString().trim();
        if(tp.length()==0 || tp.equalsIgnoreCase("Priority")){
            priority.setFocusable(true);
            priority.setFocusableInTouchMode(true);
            Utils.showToast(CreateIssueActivity.this, "Enter priority");
            return false;
        }

        if(description.getText().toString().trim().length()==0){
            description.setFocusable(true);
            description.setFocusableInTouchMode(true);
            Utils.showToast(CreateIssueActivity.this, "Enter description");
            return false;
        }

        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_issue, menu);
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
