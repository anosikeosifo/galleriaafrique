package com.galleriafrique.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.galleriafrique.R;

/**
 * Created by osifo on 8/3/15.
 */
public class UserHolder {
    public TextView usernameText;
    public TextView userLocationText;
    public ImageButton followActionBtn;
    public ImageButton unfollowActionBtn;
    public View userItemView;
    public ImageView userAvatar;

    public UserHolder(View userItem) {
        this.userItemView = userItem.findViewById(R.id.user_item_container);
        this.userAvatar = (ImageView)userItem.findViewById(R.id.user_avatar);
        this.usernameText = (TextView)userItem.findViewById(R.id.user_name);
        this.userLocationText = (TextView)userItem.findViewById(R.id.user_location);
        this.followActionBtn = (ImageButton)userItem.findViewById(R.id.follow_user_btn);
        this.unfollowActionBtn = (ImageButton)userItem.findViewById(R.id.unfollow_user_btn);
    }
}