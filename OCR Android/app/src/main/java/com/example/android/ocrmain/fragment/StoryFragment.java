package com.example.android.ocrmain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ocrmain.R;

/**
 * Created by prathmeshmhapsekar on 01/12/17.
 */

public class StoryFragment extends BaseFragment {

    public static StoryFragment create()
    {
        return new StoryFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_story;
    }

    @Override
    public void inOnCreate(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
