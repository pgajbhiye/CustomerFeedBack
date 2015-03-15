package com.navpal.feedback.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
public class BaseTicketFragment extends Fragment {

    ZendeskConnector zendConnector;

    public String ticketType;
    private ListView listView;
    private TextView msg;

    public BaseTicketFragment(){
        this.ticketType = "open";
    }

    public BaseTicketFragment setType(String ticketType){
        this.ticketType = ticketType;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.openticketsfragment, container, false);
        listView =(ListView)view.findViewById(R.id.ticketlist);
        msg = (TextView)view.findViewById(R.id.noTicket);

        zendConnector = new ZendeskConnector(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        zendConnector.fetchTicketsByStatus(this.ticketType, new ZendeskCallback<List<Request>>() {
            @Override
            public void onSuccess(List<Request> result) {
                Log.d(BaseTicketFragment.class.getName(), "result"+ result.size());
                if (result!=null && result.size() > 0)
                    listView.setAdapter(new BaseTicketsAdapter(getActivity(), result));
                else
                    msg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(ErrorResponse error) {
                //Utils.showToast(getActivity(), "Unable to fetch tickets. Please try after sometime");
                msg.setVisibility(View.VISIBLE);
            }
        });
    }
}
