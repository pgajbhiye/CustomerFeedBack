package com.navpal.feedback.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.navpal.feedback.R;
import com.navpal.feedback.util.Config;
import com.zendesk.sdk.feedback.impl.BaseZendeskFeedbackConfiguration;
import com.zendesk.sdk.feedback.ui.ContactZendeskActivity;
import com.zendesk.sdk.logger.Logger;
import com.zendesk.sdk.model.CreateRequest;
import com.zendesk.sdk.model.Request;
import com.zendesk.sdk.model.network.AnonymousIdentity;
import com.zendesk.sdk.model.network.ErrorResponse;
import com.zendesk.sdk.model.network.Identity;
import com.zendesk.sdk.model.network.UploadResponse;
import com.zendesk.sdk.network.RequestProvider;
import com.zendesk.sdk.network.UploadProvider;
import com.zendesk.sdk.network.impl.ZendeskCallback;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.network.impl.ZendeskRequestProvider;
import com.zendesk.sdk.network.impl.ZendeskUploadProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Pallavi
 */
public class CreateNewTicketActivity extends Activity {

    private static final String LOG_TAG = CreateNewTicketActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket2);

        initZendeskSdk();

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
                //createNewTicket();
                createNewTicketWithAttachment();
            }
        });

        Button fetch = (Button)findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createNewTicket();
                getAllTickets();
            }
        });

    }

    RequestProvider provider;
    private void initZendeskSdk(){
        ZendeskConfig.INSTANCE.init(this, "https://navpal.zendesk.com", Config.APP_ID, Config.OAUTH_ID);
        // Sets the configuration used by the Contact Zendesk component
        ZendeskConfig.INSTANCE.setContactConfiguration(new BaseZendeskFeedbackConfiguration(){

            @Override
            public String getRequestSubject() {
                return "Raise Your Ticket";
            }
        });
        provider = new ZendeskRequestProvider();

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

    private  void createNewTicket_1(){
        Log.d(LOG_TAG,"Creating new ticket");

        // Starts the Contact Zendesk component
        Intent intent = new Intent(this, ContactZendeskActivity.class);
        startActivity(intent);
        Log.d(LOG_TAG,"Started new ticket");


    }

    private  void createNewTicket() {
        Log.d(LOG_TAG, "Creating new ticket");
        ArrayList<String> tags = new ArrayList<String>() {{
            add("idli");
            add("bonda");
        }};

        provider.createRequest("pallavi.mp88@gmail.com", "First Ticket", "Ticket description", tags, new ZendeskCallback<CreateRequest>() {
            @Override
            public void onSuccess(CreateRequest result) {
                // Handle the success
                Toast.makeText(CreateNewTicketActivity.this,"New Ticket Created ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorResponse error) {
                // Handle the error
                // Log the error
                Logger.e(LOG_TAG, error);
            }
        });
    }

    private void createNewTicketWithAttachment(){
        UploadProvider uploadProvider = new ZendeskUploadProvider();
        File sdcard = Environment.getExternalStorageDirectory();
        Log.d(LOG_TAG,"sdcard"+sdcard);
//Get the text file
        File file = new File(sdcard,"testaudio.aac");

        if(file.exists()){
            Toast.makeText(CreateNewTicketActivity.this,"File doesnt exist",Toast.LENGTH_SHORT).show();

        }

        uploadProvider.uploadAttachment(file.getName(), file, "test/png", new ZendeskCallback<UploadResponse>() {
            @Override
            public void onSuccess(UploadResponse result) {
                // Handle success
                Toast.makeText(CreateNewTicketActivity.this,"Attachemnts uploaded",Toast.LENGTH_SHORT).show();
                abc(result);
            }

            @Override
            public void onError(ErrorResponse error) {
                // Handle error
                Log.d(LOG_TAG,"error "+error.getReason());
                Toast.makeText(CreateNewTicketActivity.this,"Attachemnts Error"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Create Request with attachments
    private void abc(final UploadResponse result){

        ArrayList<String> attachments = new ArrayList<String>(){{
            add(result.getToken());
        }};

        ArrayList<String> tags = new ArrayList<String>() {{
            add("printer");
            add("fire");
        }};

        CreateRequest createRequest = new CreateRequest();
        createRequest.setEmail("pallavi.mp88@gmail.com");
        createRequest.setSubject("Ticket with text attachment");
        createRequest.setDescription("Ticket TEST AUDIO");
        createRequest.setTags(tags);
        createRequest.setAttachments(attachments);


        provider.createRequest(createRequest, new ZendeskCallback<CreateRequest>() {
            @Override
            public void onSuccess(CreateRequest result) {
                // Handle success
                Toast.makeText(CreateNewTicketActivity.this,"Request Create ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorResponse error) {
                // Handle error
            }
        });

    }

    private void getAllTickets(){
        provider.getAllRequests(new ZendeskCallback<List<Request>>() {
            @Override
            public void onSuccess(List<Request> result) {
                // Handle success
                for(Request r:result){
                    Log.d(LOG_TAG,"fetching all request :" +"description "+r.getDescription()+"ticket tyype"+r.getType());

                }
            }

            @Override
            public void onError(ErrorResponse error) {
                // Handle the error

                // Log the error
                Logger.e("MyLogTag", error);
            }
        });




    }

}
