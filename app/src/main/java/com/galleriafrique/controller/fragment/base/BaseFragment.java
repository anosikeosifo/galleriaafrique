package com.galleriafrique.controller.fragment.base;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.galleriafrique.controller.activity.base.Home;

/**
 * Created by osifo on 8/23/15.
 */
public class BaseFragment extends Fragment {
    private Home activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Home) getActivity();
    }

    public String getTagText() {
        return getClass().getSimpleName();
    }
}
