package com.example.android.ocrmain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.ocrmain.templatefragment.ListFragment;
import com.example.android.ocrmain.templatefragment.CameraFragment;

/**
 * Created by prathmeshmhapsekar on 17/01/18.
 */

public class TemplatePagerAdapter extends FragmentPagerAdapter{
    public TemplatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return ListFragment.create();
            case 1:
                return CameraFragment.create();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position)
        {
            case 0:
                return "Forms";
            case 1:
                return "Camera";
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
