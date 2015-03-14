package com.navpal.feedback.listeners;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.navpal.feedback.helpers.PageBackgroundAnimator;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Author Naveen I
 */
public class PageChangeListener implements ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private ImageView imageBg, imageFg;
    private PageBackgroundAnimator pbgAnim;
    private Context context;

    public static PageChangeListener getInst(Context context,ViewPager pager, ImageView imageBg, ImageView imageFg, CirclePageIndicator circlePageIndicator){
        return new PageChangeListener(context, pager, imageBg, imageFg, circlePageIndicator);
    }

    public PageChangeListener(Context context, ViewPager pager, ImageView imageBg, ImageView imageFg, CirclePageIndicator circlePageIndicator) {
        this.pager = pager;
        this.imageBg = imageBg;
        this.imageFg = imageFg;
        if(circlePageIndicator!=null){
            circlePageIndicator.setOnPageChangeListener(this);
        } else {
            this.pager.setOnPageChangeListener(this);
        }
        this.context = context;
        pbgAnim = new PageBackgroundAnimator(context, imageBg, imageFg);
        pbgAnim.showImageAt(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        pbgAnim.showImageAt(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
