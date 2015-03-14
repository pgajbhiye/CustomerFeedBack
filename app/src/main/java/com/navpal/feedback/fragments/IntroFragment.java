package com.navpal.feedback.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navpal.feedback.R;

/**
 * Author Naveen I
 */
public class IntroFragment extends Fragment {
    public static final String TITLE = "title", CAPTION = "caption";

    public static final IntroFragment newInstance(int titleResId, int captionResId)
    {
        IntroFragment f = new IntroFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TITLE, titleResId);
        bdl.putInt(CAPTION, captionResId);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.welcome_viewpager_view, container, false);
        TextView titleTextView = (TextView)v.findViewById(R.id.title);
        titleTextView.setText(getArguments().getInt(TITLE));
        TextView captionTextView = (TextView)v.findViewById(R.id.caption);
        captionTextView.setText(getArguments().getInt(CAPTION));

        return v;
    }
}
