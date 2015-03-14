package com.navpal.feedback.helpers;

import android.content.Context;
import android.util.Log;

import com.navpal.feedback.util.Config;
import com.navpal.feedback.util.Utils;
import com.zendesk.sdk.feedback.impl.BaseZendeskFeedbackConfiguration;
import com.zendesk.sdk.model.Request;
import com.zendesk.sdk.model.network.AnonymousIdentity;
import com.zendesk.sdk.model.network.ErrorResponse;
import com.zendesk.sdk.model.network.Identity;
import com.zendesk.sdk.network.RequestProvider;
import com.zendesk.sdk.network.impl.ZendeskCallback;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.network.impl.ZendeskRequestProvider;

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

}
