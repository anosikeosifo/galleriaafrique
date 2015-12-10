package com.galleriafrique.controller.fragment.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.galleriafrique.controller.activity.base.BaseActivity;
import com.galleriafrique.controller.activity.base.HomeActivity;

/**
 * Created by osifo on 8/23/15.
 */
public abstract class BaseFragment extends Fragment {
    private BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (BaseActivity) getActivity();
    }

    public String getTagText() {
        return getClass().getSimpleName();
    }

    public abstract String getTitleText();


    public abstract boolean onBackPressed();

    public void retryAction (DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        activity.retryAction(positive, negative);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setToolbarTitle(getTitleText());
    }
}
