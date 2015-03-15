package com.navpal.feedback.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.navpal.feedback.R;

import java.util.List;

import com.navpal.feedback.util.Utils;
import com.squareup.okhttp.internal.Util;
import com.zendesk.sdk.model.Request;

/**
 * Author Pallavi
 */
public class BaseTicketsAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private Context context;
    private List<Request> tickets;

    public BaseTicketsAdapter(Context context,List<Request> tickets){
        this.context = context;
        this.tickets = tickets;
        inflater = LayoutInflater.from(context);


    }
    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        return tickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View rowview = convertView;
        if(rowview == null){
            rowview = inflater.inflate(R.layout.ticket_overview_row, parent, false);
            holder = new ViewHolder();

            holder.date = (TextView) rowview.findViewById(R.id.ticketdate);
            holder.ticketno = (TextView) rowview.findViewById(R.id.ticketid);
            holder.subject = (TextView) rowview.findViewById(R.id.ticketsubject);
            holder.description = (TextView) rowview.findViewById(R.id.ticketdescription);
            //holder.status = (TextView) rowview.findViewById(R.id.ticketstatus);
            rowview.setTag(holder);
        }else{
            holder = (ViewHolder) rowview.getTag();
        }

        Request ticket = (Request)getItem(position);
        holder.date.setText(Utils.formatDate(ticket.getCreatedAt()));
        holder.ticketno.setText(ticket.getPriority());
        holder.subject.setText(ticket.getSubject());
        holder.description.setText(ticket.getDescription());
       //holder.status.setText(Utils.trimStatus(ticket.getStatus()));

        return rowview;
    }

    static class ViewHolder{
        TextView date;
        TextView ticketno;
        TextView subject;
        TextView description;
        TextView status;
    }
}
