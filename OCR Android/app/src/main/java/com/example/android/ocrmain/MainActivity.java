package com.example.android.ocrmain;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.ocrmain.adapter.MainPagerAdapter;
import com.example.android.ocrmain.view.SnapTabViews;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final View background = findViewById(R.id.am_background_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.am_view_pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int colorWhite= ContextCompat.getColor(this,R.color.white);
        final int colorPurple = ContextCompat.getColor(this, R.color.light_purple);
        //final TabLayout tablayout = (TabLayout) findViewById(R.id.am_tab_layout);
        //tablayout.setupWithViewPager(viewPager);
        SnapTabViews snapTabViews= (SnapTabViews) findViewById(R.id.am_snap_tabs);
        snapTabViews.setUpWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0)
                {
                    background.setBackgroundColor(colorWhite);
                    background.setAlpha(1-positionOffset);
                    //tablayout.setBackgroundColor(colorBlue);
                    //tablayout.setAlpha(1-positionOffset);
                }
                else if(position==1)
                {
                    background.setBackgroundColor(colorWhite);
                    background.setAlpha(positionOffset);
                    //tablayout.setBackgroundColor(colorPurple);
                    //tablayout.setAlpha(positionOffset);
                }
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
