package com.navpal.feedback.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request eachTicket = (Request)parent.getItemAtPosition(position);
                buildTicketDetailsView(eachTicket);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        zendConnector.fetchTicketsByStatus(this.ticketType, new ZendeskCallback<List<Request>>() {
            @Override
            public void onSuccess(List<Request> result) {
                Log.d(BaseTicketFragment.class.getName(), "result"+ result.size());
                if (result!=null && result.size() > 0) {
                    listView.setAdapter(new BaseTicketsAdapter(getActivity(), result));
                }
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


    private void buildTicketDetailsView(Request ticket){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ticketdetails, null);

        TextView detailsubject = (TextView)dialogView.findViewById(R.id.detailsubject);
        detailsubject.setText("Subject :");
        TextView detailsubjectVal = (TextView)dialogView.findViewById(R.id.detailsubjectVal);
        detailsubjectVal.setText(ticket.getSubject());


        TextView detaildescription = (TextView)dialogView.findViewById(R.id.detaildescription);
        detaildescription.setText("Description :");
        TextView detaildescriptionVal = (TextView)dialogView.findViewById(R.id.detaildescriptionVal);
        detaildescriptionVal.setText(ticket.getDescription());

        TextView detaildatecreated = (TextView)dialogView.findViewById(R.id.detaildatecreated);
        detaildatecreated.setText("Ticket Created :");
        TextView detaildatecreatedVal = (TextView)dialogView.findViewById(R.id.detaildatecreatedVal);
        detaildatecreatedVal.setText(ticket.getCreatedAt().toString());

        TextView detailstatus = (TextView)dialogView.findViewById(R.id.detailstatus);
        detailstatus.setText("Ticket Status :");
        TextView detailstatusVal = (TextView)dialogView.findViewById(R.id.detailstatusVal);
        if(ticket.getStatus()!=null && !ticket.getStatus().isEmpty())
            detailstatusVal.setText(ticket.getStatus());
        else
            detailstatusVal.setText("Nil");

        TextView detailpriority = (TextView)dialogView.findViewById(R.id.detailpriority);
        detailpriority.setText("Ticket Priority :");
        TextView detailpriorityVal = (TextView)dialogView.findViewById(R.id.detailpriorityVal);
        if(ticket.getPriority()!=null && !ticket.getPriority().isEmpty())
             detailpriorityVal.setText(ticket.getPriority());
        else
            detailpriorityVal.setText("Nil");

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }
}
