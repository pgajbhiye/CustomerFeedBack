package com.navpal.feedback.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.navpal.feedback.R;
import com.navpal.feedback.adapters.IntroFragmentsAdapter;
import com.navpal.feedback.listeners.PageChangeListener;


public class WelcomeActivity extends FragmentActivity {

    private IntroFragmentsAdapter wpfAdapter;
    private ViewPager pager;
    private ImageView imageBG, imageFG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        pager = (ViewPager) findViewById(R.id.viewpager);
        imageBG = (ImageView) findViewById(R.id.imageViewBg);
        imageFG = (ImageView) findViewById(R.id.imageViewFg);
        prepareIntroSlides();
        PageChangeListener.getInst(getApplicationContext(), pager, imageBG, imageFG);
    }

    private void prepareIntroSlides(){
        wpfAdapter = new IntroFragmentsAdapter(getSupportFragmentManager());
        pager.setAdapter(wpfAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
