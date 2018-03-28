package com.example.android.ocrmain.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ocrmain.R;

/**
 * Created by prathmeshmhapsekar on 17/01/18.
 */

public class FormTabView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ImageView mStartImage;
    private ImageView mEndImage;
    int mNewColor,mOldColor;
    private TextView mStartText;
    private TextView mEndText;

    public FormTabView(@NonNull Context context) {
        this(context,null);
    }

    public FormTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormTabView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setUpWithViewPager(final ViewPager viewPager)
    {
        viewPager.addOnPageChangeListener(this);
        mStartImage.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 0)
                {
                    viewPager.setCurrentItem(0);
                }
            }
        });

        mEndImage.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 2)
                {
                    viewPager.setCurrentItem(2);
                }
            }
        });

        mStartText.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 0)
                {
                    viewPager.setCurrentItem(0);
                }
            }
        });

        mEndText.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 2)
                {
                    viewPager.setCurrentItem(2);
                }
            }
        });

    }


    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_form_tabs,this,true);
        mStartImage = (ImageView)findViewById(R.id.vst_start_image);
        mEndImage = (ImageView)findViewById(R.id.vst_end_image);
        mStartText=(TextView)findViewById(R.id.vst_start_text);
        mEndText = (TextView)findViewById(R.id.vst_end_text);
        mNewColor= ContextCompat.getColor(getContext(),R.color.red);
        mOldColor=ContextCompat.getColor(getContext(),R.color.black);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        setColor(position);

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setColor(int pos) {
        if (pos == 0) {
            mStartImage.setColorFilter(mNewColor);
            mStartText.setTextColor(mNewColor);

            mEndImage.setColorFilter(mOldColor);
            mEndText.setTextColor(mOldColor);

        }  else if (pos == 1)
        {
            mEndImage.setColorFilter(mNewColor);
            mEndText.setTextColor(mNewColor);
            mStartImage.setColorFilter(mOldColor);
            mStartText.setTextColor(mOldColor);
        }

    }

}
