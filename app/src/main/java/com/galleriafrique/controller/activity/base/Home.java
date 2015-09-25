package com.galleriafrique.controller.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.galleriafrique.R;
import com.galleriafrique.model.post.Post;

/**
 * Created by osifo on 8/23/15.
 */
public class Home extends BaseActivity {

    private FragmentSwitcher fragmentSwitcher;
    private Home activity;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        activity = this;
        initUI();

        fragmentSwitcher = new FragmentSwitcher(this, getSupportFragmentManager());
        fragmentSwitcher.showPostFeed();
    }

    public void setClickListeners() {

    }

    public void initUI() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//this sets my toolbar as the custom actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }


    public void showPosts() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }
}
