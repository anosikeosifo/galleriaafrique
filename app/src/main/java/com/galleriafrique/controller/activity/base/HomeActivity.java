package com.galleriafrique.controller.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.account.LoginActivity;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.helpers.AccountManager;
import com.galleriafrique.util.tools.Strings;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.reflect.TypeToken;

/**
 * Created by osifo on 8/23/15.
 */
public class HomeActivity extends BaseActivity {

    private FragmentSwitcher fragmentSwitcher;
    private HomeActivity activity;
    private android.support.v7.widget.Toolbar toolbar;
    private MenuItem viewProfileBtn;
    private MenuItem signoutBtn;
    private GoogleApiClient googleApiClient;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initUI() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewProfileBtn = (MenuItem)findViewById(R.id.view_my_profile);
        signoutBtn = (MenuItem)findViewById(R.id.signout);
        setSupportActionBar(toolbar);//this sets my toolbar as the custom actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_my_profile:
                fragmentSwitcher.showUserProfile(AccountManager.getUser());
                return true;
            case R.id.signout:
                AccountManager.signout();
//                if (mGoogleApiClient.isConnected()) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(activity, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}
