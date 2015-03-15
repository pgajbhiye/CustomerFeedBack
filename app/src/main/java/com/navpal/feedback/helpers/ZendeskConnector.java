package com.navpal.feedback.helpers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.navpal.feedback.util.Config;
import com.navpal.feedback.util.Utils;
import com.squareup.okhttp.internal.Util;
import com.zendesk.sdk.feedback.impl.BaseZendeskFeedbackConfiguration;
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
public class ZendeskConnector {


    RequestProvider provider;

    public ZendeskConnector initZendeskSdk(Context context){
        ZendeskConfig.INSTANCE.init(context, "https://navpal.zendesk.com", Config.APP_ID, Config.OAUTH_ID);
        // Sets the configuration used by the Contact Zendesk component
        ZendeskConfig.INSTANCE.setContactConfiguration(new BaseZendeskFeedbackConfiguration(){

            @Override
            public String getRequestSubject() {
                return "Raise Your Ticket";
            }
        });
        provider = new ZendeskRequestProvider();
        zendeskUser();
        return this;
    }

    private void zendeskUser(){
         Identity anonymousIdentity = new AnonymousIdentity.Builder()
                .withEmailIdentifier("pallavi.mp88@gmail.com")
                .withExternalIdentifier("pallavi_id")
                .withNameIdentifier("Pallavi G")
                .build();

        ZendeskConfig.INSTANCE.setIdentity(anonymousIdentity);
    }


    public void fetchTicketsByStatus(String status, ZendeskCallback callback){
        //String statuses = StringUtils.toCsvString("pending", "hold");
        provider.getRequests(status, callback);
    }

    public void createTicket(final Context context,final String subject,final String desc,final File file,final ZendeskCallback callback){
        if(file!=null){
            UploadProvider uploadProvider = new ZendeskUploadProvider();
            uploadProvider.uploadAttachment(file.getName(), file, "test/png", new ZendeskCallback<UploadResponse>() {
                @Override
                public void onSuccess(UploadResponse result) {
                    // Handle success
                    createTicket(context,subject, desc,result, callback);
                }
                @Override
                public void onError(ErrorResponse error) {
                    // Handle error
                    Utils.showToast(context, "Fail to upload the attachment");
                    callback.onError(error);
                }
            });
        } else {
            UploadResponse result = null;
            createTicket(context,subject, desc, result, callback);
        }
    }

    private void createTicket(final Context context,final String subject,final String desc,final UploadResponse result,final ZendeskCallback callback){
        ArrayList<String> attachments = null;
        if(result!=null) {
            attachments = new ArrayList<String>() {{
                add(result.getToken());
            }};
        }

        CreateRequest createRequest = new CreateRequest();
        createRequest.setEmail("pallavi.mp88@gmail.com");
        createRequest.setSubject(subject);
        createRequest.setDescription(desc);
        if(attachments!=null) {
            createRequest.setAttachments(attachments);
        }
        provider.createRequest(createRequest, new ZendeskCallback<CreateRequest>() {
            @Override
            public void onSuccess(CreateRequest result) {
                // Handle success
                Utils.showToast(context,"Ticket created successfully");
                callback.onSuccess(result);
            }

            @Override
            public void onError(ErrorResponse error) {
                // Handle error
                Utils.showToast(context, "Fail to create ticket");
                callback.onError(error);
            }
        });
    }
}
