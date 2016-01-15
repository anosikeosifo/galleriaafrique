package com.galleriafrique.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.galleriafrique.util.tools.Strings;
import com.galleriafrique.view.holders.PostHolder;
import com.galleriafrique.view.holders.UserHolder;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class UsersListAdapter extends BaseAdapter {

    protected Context context;
    protected List<User> userList;
    protected UserHolder userHolder;
    protected UserListAdapterListener userListAdapterListener;

    public UsersListAdapter(){

    }
    public UsersListAdapter(BaseFragment fragment, List<User> userList) {
        this.context = fragment.getActivity();
        this.userListAdapterListener = (UserListAdapterListener)fragment;
        this.userList = userList;
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
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final User user = this.userList.get(position);

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            userHolder = new UserHolder(view);
            setContent(userHolder, user);
            view.setTag(userHolder);
            setListeners(userHolder, user);
        } else {
            userHolder = (UserHolder)view.getTag();
        }

        return view;
    }

    protected void setContent(UserHolder userHolder, User user) {
        userHolder.usernameText.setText(user.getName());
        userHolder.userLocationText.setText("Not available");
        userHolder.followActionBtn.setVisibility(View.GONE);
        userHolder.unfollowActionBtn.setVisibility(View.GONE);
    }

    protected void setListeners(final UserHolder holder, final User user) {
        holder.userItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userListAdapterListener.showUserProfile(user);
            }
        });

        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userListAdapterListener.showUserProfile(user);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public interface UserListAdapterListener  {
        void showUserProfile(User user);
    }

}
