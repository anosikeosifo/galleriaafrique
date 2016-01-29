package com.galleriafrique.view.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.model.user.UserResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.tools.CircleTransform;
import com.galleriafrique.view.holders.UserHolder;

import java.util.List;

/**
 * Created by osifo on 1/6/16.
 */
public class UserFollowersAdapter  extends BaseAdapter {
    protected Context context;
    protected List<User> userList;
    protected UserHolder userHolder;
    private UserFollowersAdapter.UserFollowersAdapterListener userFollowersAdapterListener;
    private LayoutInflater inflater;

    public UserFollowersAdapter(BaseFragment fragment, List<User> userList) {
        this.context = fragment.getActivity();
        this.userList = userList;
        this.userFollowersAdapterListener = (UserFollowersAdapter.UserFollowersAdapterListener)fragment;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = this.userList.get(position);
        final UserHolder userHolder;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_list_item, null);
            userHolder = new UserHolder(convertView);

            convertView.setTag(userHolder);

        } else {
            userHolder = (UserHolder)convertView.getTag();
        }

        setContent(userHolder, user);
        setListeners(userHolder, user);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }


    private void setTags(UserHolder userHolder, int position) {
        userHolder.followActionBtn.setTag(position);
        userHolder.unfollowActionBtn.setTag(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected void setContent(UserHolder userHolder, User user) {
        userHolder.usernameText.setText(user.getName());
        userHolder.userLocationText.setText("Not available");

        if (user.getFollowBackStatus()) {
            userHolder.unfollowActionBtn.setVisibility(View.GONE);
            userHolder.followActionBtn.setVisibility(View.VISIBLE);
        } else {
            userHolder.followActionBtn.setVisibility(View.GONE);
            userHolder.unfollowActionBtn.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(user.getAvatar()).centerCrop().placeholder(R.drawable.ic_avatar).transform(new CircleTransform(context)).into(userHolder.userAvatar);
    }


    public void updateFollowUIBeforeAPICall(UserHolder holder, User user) {
        user.setFollowBackStatus(false);
        holder.followActionBtn.setImageResource(R.drawable.ic_followed);
    }


    public void updateFollowUIAfterAPICall(UserResponse response, UserHolder holder) {
        if(response.isSuccess()) {
            holder.followActionBtn.setImageResource(R.drawable.ic_followed);
        } else {
            holder.followActionBtn.setImageResource(R.drawable.ic_follow);
        }
    }

    protected void setListeners(final UserHolder holder, final User user) {
        holder.userItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFollowersAdapterListener.showUserProfile(user);
            }
        });

        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFollowersAdapterListener.showUserProfile(user);
            }
        });
        holder.followActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFollowUIBeforeAPICall(holder, user);
                userFollowersAdapterListener.followUser(user.getId());
            }
        });
        holder.unfollowActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfollowUser(user);
            }
        });
    }

    private void unfollowUser(final User user) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Confirm Action");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to unfollow?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        userFollowersAdapterListener.unfollowUser(user.getId());
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public interface UserFollowersAdapterListener {
        void showUserProfile(User user);
        void followUser(int user_id);
        void unfollowUser(int user_id);
    }
}
