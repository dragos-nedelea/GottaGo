package com.example.gottago.Onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gottago.MainActivity;
import com.example.gottago.R;

public class OnboardingNavigation extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    ViewPagerAdapter viewPagerAdapter;
    Button backButton, skipButton, nextButton;
    TextView[] dots;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setDotIndicator(position);

            if( position > 0) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.INVISIBLE);
            }

            if(position == 2) {
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_navigation);

        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0) > 0) {
                    slideViewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0) < 2) {
                    slideViewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent intent = new Intent(OnboardingNavigation.this, OnboardingGetStarted.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingNavigation.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);

        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);

        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);


    }

    public void setDotIndicator(int position) {
        dots = new TextView[3];
        dotIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++ ) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.orange, getApplicationContext().getTheme()));
    }
    private int getItem(int i){

        return slideViewPager.getCurrentItem() + i;
    }


    String prevStarted = "yes";
    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        if (!sharedpreferences.getBoolean(prevStarted, false)) {
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putBoolean(prevStarted, Boolean.TRUE);
//            editor.apply();
//        } else {
//            moveToSecondary();
//        }
    }

    public void moveToSecondary(){
        // use an intent to travel from one activity to another.
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}