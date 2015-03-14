package com.navpal.feedback.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.navpal.feedback.R;
import com.navpal.feedback.fragments.IntroFragment;

/**
 * Author Naveen I
 */
public class IntroFragmentsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    int[][] mTexts = {
            {
                    R.string.welcome_title_one,
                    R.string.welcome_caption_one

            },
            {
                    R.string.welcome_title_two,
                    R.string.welcome_caption_two
            },
            {
                    R.string.welcome_title_three,
                    R.string.welcome_caption_three
            },
            {
                    R.string.welcome_title_four,
                    R.string.welcome_caption_four
            }
    };

    private List<Fragment> getIntroFragments(){
        List<Fragment> introFrags = new ArrayList<Fragment>();
        for (int i=0; i<mTexts.length; i+=1){
            introFrags.add(IntroFragment.newInstance(mTexts[i][0], mTexts[i][1]));
        }
        return  introFrags;
    }

    public IntroFragmentsAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = getIntroFragments();
    }
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}

