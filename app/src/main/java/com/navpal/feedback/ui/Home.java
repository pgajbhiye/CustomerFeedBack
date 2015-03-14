package com.navpal.feedback.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.navpal.feedback.R;
import com.navpal.feedback.adapters.TicketsPagerAdapter;

/**
 * Author Pallavi
 */
public class Home extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ViewPager ticketsPager = (ViewPager)findViewById(R.id.ticketspager);
        ticketsPager.setAdapter(new TicketsPagerAdapter(getSupportFragmentManager()));
        ticketsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
