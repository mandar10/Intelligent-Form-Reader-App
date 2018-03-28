package com.example.android.ocrmain.templatefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ocrmain.R;

public class CameraFragment extends TBaseFragment {
    public static CameraFragment create()
    {
        return new CameraFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_camera;
    }

    @Override
    public void inOnCreate(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
