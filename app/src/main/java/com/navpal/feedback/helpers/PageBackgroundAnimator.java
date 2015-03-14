package com.navpal.feedback.helpers;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.navpal.feedback.R;

/**
 * Author Naveen I
 */
public class PageBackgroundAnimator implements AnimationListener {
    ImageView imageBg, imageFg;

    int currentIndx=0;

    // Animation
    Animation animFadein;

    int[] mImages = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four
    };

    public PageBackgroundAnimator(Context context, ImageView imageBg, ImageView imageFg) {
        this.imageBg = imageBg;
        this.imageFg = imageFg;
        // load the animation
        animFadein = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        // set animation listener
        animFadein.setAnimationListener(this);
    }

    public void showImageAt(int indx){
        if(indx<0 || indx > mImages.length){
            indx = 0;
        }
        currentIndx = indx;
        imageFg.setVisibility(View.INVISIBLE);
        imageFg.setImageResource(mImages[currentIndx]);
        imageFg.startAnimation(animFadein);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        imageBg.setImageResource(mImages[currentIndx]);
        imageFg.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
