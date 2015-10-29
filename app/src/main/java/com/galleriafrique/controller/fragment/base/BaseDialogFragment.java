package com.galleriafrique.controller.fragment.base;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.galleriafrique.controller.activity.base.HomeActivity;

/**
 * Created by osifo on 9/25/15.
 */
public class BaseDialogFragment extends DialogFragment {

    private HomeActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
    }

    public String getTagText() {
        return getClass().getSimpleName();
    }

    public void retryAction (DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        activity.retryAction(positive, negative);
    }
}