package com.navpal.feedback.listeners;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.navpal.feedback.helpers.PageBackgroundAnimator;

/**
 * Created by NIhapu-GSW8-HYD on 15-01-2015.
 */
public class PageChangeListener implements ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private ImageView imageBg, imageFg;
    private PageBackgroundAnimator pbgAnim;
    private Context context;

    public static PageChangeListener getInst(Context context,ViewPager pager, ImageView imageBg, ImageView imageFg){
        return new PageChangeListener(context, pager, imageBg, imageFg);
    }

    public PageChangeListener(Context context, ViewPager pager, ImageView imageBg, ImageView imageFg) {
        this.pager = pager;
        this.imageBg = imageBg;
        this.imageFg = imageFg;
        this.pager.setOnPageChangeListener(this);
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
