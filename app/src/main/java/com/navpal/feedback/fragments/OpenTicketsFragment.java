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

/**
 * Author Pallavi
 */
public class OpenTicketsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.openticketsfragment, container, false);
        ListView listView =(ListView)view.findViewById(R.id.ticketlist);
        listView.setAdapter(new BaseTicketsAdapter(getActivity()));

        return view;
    }


}
