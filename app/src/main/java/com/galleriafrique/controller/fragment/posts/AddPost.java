package com.galleriafrique.controller.fragment.posts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galleriafrique.controller.fragment.base.BaseFragment;

/**
 * Created by osifo on 9/23/15.
 */
public class AddPost extends BaseFragment {

    public static AddPost newInstance(int viewID) {
        AddPost fragment = new AddPost();
        Bundle bundle = new Bundle();

        //bundle.putString("path", a);
        return fragment;
    }

    @Override
    public String getTitleText() {
        return "Add post";
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
