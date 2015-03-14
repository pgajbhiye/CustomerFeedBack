package com.navpal.feedback.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.navpal.feedback.R;
import com.navpal.feedback.adapters.BaseTicketsAdapter;
import com.navpal.feedback.helpers.ZendeskConnector;
import com.navpal.feedback.util.Utils;
import com.zendesk.sdk.model.Request;
import com.zendesk.sdk.model.network.ErrorResponse;
import com.zendesk.sdk.network.impl.ZendeskCallback;

import java.util.List;

/**
 * Author Pallavi
 */
public class OpenTicketsFragment extends Fragment {

    ZendeskConnector zendConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.openticketsfragment, container, false);
        final ListView listView =(ListView)view.findViewById(R.id.ticketlist);

        zendConnector = new ZendeskConnector().initZendeskSdk(getActivity());

        zendConnector.fetchTicketsByStatus("open", new ZendeskCallback<List<Request>>() {
            @Override
            public void onSuccess(List<Request> result) {
                listView.setAdapter(new BaseTicketsAdapter(getActivity(),result));
            }

            @Override
            public void onError(ErrorResponse error) {
                Utils.showToast(getActivity(), "Unable to fetch tickets. Please try after sometime");
            }
        });
        return view;
    }

}
