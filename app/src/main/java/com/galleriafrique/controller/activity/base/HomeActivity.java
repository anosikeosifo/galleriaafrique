package com.galleriafrique.controller.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.galleriafrique.R;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.tools.Strings;
import com.google.gson.reflect.TypeToken;

/**
 * Created by osifo on 8/23/15.
 */
public class HomeActivity extends BaseActivity {

    private FragmentSwitcher fragmentSwitcher;
    private HomeActivity activity;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String intentExtra = getIntent().getStringExtra("fragment");

        if(intentExtra == null) {
            setContentView(R.layout.activity_post);
            activity = this;
            initUI();

            fragmentSwitcher = new FragmentSwitcher(this, getSupportFragmentManager());
            fragmentSwitcher.showPostFeed();

        } else {
            switch (intentExtra) {
                case "PostDetails":
                    String postData = getIntent().getExtras().getString(Post.POST_DATA);
                    Post post = CommonUtils.getGson().fromJson(postData, new TypeToken<Post>(){}.getType());
                    fragmentSwitcher.showPostDetails(post);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initUI() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//this sets my toolbar as the custom actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}
