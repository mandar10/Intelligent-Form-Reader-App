package com.example.android.ocrmain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.ocrmain.fragment.ChatFragment;
import com.example.android.ocrmain.fragment.EmptyFragment;
import com.example.android.ocrmain.fragment.StoryFragment;

/**
 * Created by prathmeshmhapsekar on 01/12/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter{

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return ChatFragment.create();
            case 1:
                return EmptyFragment.create();
            case 2:
                return StoryFragment.create();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position)
        {
            case 0:
                return "Camera";
            case 1:
                return "Templates";
            case 2:
                return "Story";
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
