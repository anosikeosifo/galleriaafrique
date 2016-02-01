package com.galleriafrique.controller.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.users.Favorites;
import com.galleriafrique.controller.fragment.users.Followers;
import com.galleriafrique.controller.fragment.users.Following;
import com.galleriafrique.controller.fragment.users.UserPosts;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.helpers.AccountManager;
import com.galleriafrique.util.tools.CircleTransform;
import com.galleriafrique.view.adapters.UserProfilePagerAdapter;
import com.google.gson.reflect.TypeToken;
import com.astuetz.PagerSlidingTabStrip;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 12/1/15.
 */
public class UserProfileActivity extends BaseActivity {
    private UserProfileActivity activity;
    private android.support.v7.widget.Toolbar toolbar;
    private User user;
    private ImageView userProfileAvatar;
    private ImageView userBackgroundImage;
    private TextView userName;
    private TextView userCountry;
//    private ActionBar actionBar;
    private ViewPager layoutViewPager;
    private UserProfilePagerAdapter profilePagerAdapter;
    private PagerSlidingTabStrip tab;
    public User currentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;

        String userData = getIntent().getExtras().getString(User.USER_DATA);
        if(!userData.isEmpty()) {
            currentUser = AccountManager.getUser();
            user = CommonUtils.getGson().fromJson(userData, new TypeToken<User>(){}.getType());
            initUI();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initUI() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        userProfileAvatar = (ImageView)findViewById(R.id.user_profile_image);
        userName = (TextView)findViewById(R.id.user_profile_name);
        userCountry = (TextView)findViewById(R.id.user_profile_country);

        setUIContent();

        layoutViewPager = (ViewPager)findViewById(R.id.user_profile_pager);
        tab = (PagerSlidingTabStrip) findViewById(R.id.tab);
        tab.setUnderlineColorResource(R.color.primary_dark);
        tab.setDividerColorResource(R.color.primary);
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        fragmentList.add(new Favorites());
        fragmentList.add(new UserPosts());
        fragmentList.add(new Following());
        fragmentList.add(new Followers());

        profilePagerAdapter = new UserProfilePagerAdapter(getSupportFragmentManager(), fragmentList);
        layoutViewPager.setAdapter(profilePagerAdapter);

        // Bind the tabs to the ViewPager
        tab.setViewPager(layoutViewPager);

    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setUIContent() {
        Glide.with(activity).load(user.getAvatar()).transform(new CircleTransform(activity)).fitCenter().error(R.drawable.ic_avatar)
                .placeholder(R.drawable.ic_avatar).into(userProfileAvatar);

        userName.setText(CommonUtils.getSafeString(user.getName()));
        userCountry.setText(CommonUtils.getSafeString("NG"));
    }

}
