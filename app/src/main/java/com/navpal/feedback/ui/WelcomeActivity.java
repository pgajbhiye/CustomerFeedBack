package com.navpal.feedback.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.navpal.feedback.R;
import com.navpal.feedback.adapters.IntroFragmentsAdapter;
import com.navpal.feedback.helpers.UserDetails;
import com.navpal.feedback.listeners.PageChangeListener;
import com.navpal.feedback.util.Utils;
import com.viewpagerindicator.CirclePageIndicator;
import com.zendesk.sdk.model.User;


public class WelcomeActivity extends FragmentActivity {

    private IntroFragmentsAdapter wpfAdapter;
    private ViewPager pager;
    private CirclePageIndicator circlePageIndicator;
    private ImageView imageBG, imageFG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDetails userDetails = Utils.getUser(getApplicationContext());

        if(!userDetails.isFirstTime()){
            showLoginScreen();
            return;
        }
        userDetails.setFirstTime(false);
        Utils.saveUser(getApplicationContext(), userDetails);


        setContentView(R.layout.activity_welcome);
        pager = (ViewPager) findViewById(R.id.viewpager);
        imageBG = (ImageView) findViewById(R.id.imageViewBg);
        imageFG = (ImageView) findViewById(R.id.imageViewFg);

        Button getstarted = (Button) findViewById(R.id.getstarted);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginScreen();
            }
        });

        prepareIntroSlides();

        //Bind the title indicator to the adapter
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.viewpagerindicator);
        circlePageIndicator.setViewPager(pager);
        PageChangeListener.getInst(getApplicationContext(), pager, imageBG, imageFG, circlePageIndicator);
    }

    private void showLoginScreen(){
        Intent getStartIntent = new Intent(WelcomeActivity.this, GetstartedActivity.class);
        startActivity(getStartIntent);
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
