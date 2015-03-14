package com.navpal.feedback.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.navpal.feedback.R;
import com.navpal.feedback.util.Config;
import com.zendesk.sdk.feedback.impl.BaseZendeskFeedbackConfiguration;
import com.zendesk.sdk.feedback.ui.ContactZendeskActivity;
import com.zendesk.sdk.model.network.AnonymousIdentity;
import com.zendesk.sdk.model.network.Identity;
import com.zendesk.sdk.network.impl.ZendeskConfig;

/**
 * Author Pallavi
 */
public class CreateNewTicketActivity extends Activity {

    private static final String LOG_TAG = CreateNewTicketActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket2);
        ZendeskConfig.INSTANCE.init(this, "https://navpal.zendesk.com", Config.APP_ID, Config.OAUTH_ID);

        Button user = (Button)findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zendeskUser();
            }
        });

        Button newTicket = (Button)findViewById(R.id.newticket);
        newTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTicket();
            }
        });

    }

    private void zendeskUser(){
        Log.d(LOG_TAG,"Registering zendesk User");
        Identity anonymousIdentity = new AnonymousIdentity.Builder()
                .withEmailIdentifier("pallavi.mp88@gmail.com")
                .withExternalIdentifier("pallavi_id")
                .withNameIdentifier("Pallavi G")
                .build();

        ZendeskConfig.INSTANCE.setIdentity(anonymousIdentity);

    }

    private  void createNewTicket(){
        Log.d(LOG_TAG,"Creating new ticket");
        // Sets the configuration used by the Contact Zendesk component
        ZendeskConfig.INSTANCE.setContactConfiguration(new SampleFeedbackConfiguration());

        // Starts the Contact Zendesk component
        Intent intent = new Intent(this, ContactZendeskActivity.class);
        startActivity(intent);
        Log.d(LOG_TAG,"Started new ticket");


    }

    // Configures the Contact Zendesk component
    class SampleFeedbackConfiguration extends BaseZendeskFeedbackConfiguration {
        @Override
        public String getRequestSubject() {
            return "Customer Feedback";
        }
    }

}
