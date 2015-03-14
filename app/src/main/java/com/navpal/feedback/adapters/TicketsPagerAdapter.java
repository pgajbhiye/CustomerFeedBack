package com.navpal.feedback.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.navpal.feedback.fragments.OpenTicketsFragment;
import com.navpal.feedback.fragments.PendingTicketsFragment;
import com.navpal.feedback.fragments.SolvedTicketsFragment;

/**
 * Author Pallavi
 */
public class TicketsPagerAdapter extends FragmentStatePagerAdapter {

    public TicketsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                 return new OpenTicketsFragment();
            case 1:
                 return new PendingTicketsFragment();
            case 2:
                 return new SolvedTicketsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
