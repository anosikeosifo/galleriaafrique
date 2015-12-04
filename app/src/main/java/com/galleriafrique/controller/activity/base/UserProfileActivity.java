package com.galleriafrique.controller.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.tools.CircleTransform;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;

        String userData = getIntent().getExtras().getString(User.USER_DATA);
        if(!userData.isEmpty()) {
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

//        setSupportActionBar(toolbar);//this sets my toolbar as the custom actionBar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        setToolbarTitle(user.getName());
        setUIContent();
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
