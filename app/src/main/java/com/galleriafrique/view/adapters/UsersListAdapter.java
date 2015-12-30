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

    private LayoutInflater inflater;
    private Context context;
    private List<User> userList;
    private UserHolder userHolder;
    public UserListAdapterListener userListAdapterListener;

    public UsersListAdapter(BaseFragment fragment, List<User> userList) {
        this.context = fragment.getActivity();
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final User comment = this.userList.get(position);

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            //view = inflater.inflate(R.layout.post_comments_list_item, parent, false);
            userHolder = new UserHolder(view);
            setContent(userHolder, comment);
            view.setTag(userHolder);
        } else {
            userHolder = (UserHolder)view.getTag();
        }

        return view;
    }

    private void setContent(UserHolder userHolder, User user) {
        userHolder.usernameText.setText(user.getName());
        userHolder.userLocationText.setText("Not available");
        Glide.with(context).load(user.getAvatar()).centerCrop().placeholder(R.drawable.ic_avatar).transform(new CircleTransform(context)).into(userHolder.userAvatar);

//        setContentForActionUI(postHolder, post);
    }

    private void setListeners(final UserHolder holder, final User user) {
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

    private void setTags(UserHolder userHolder, int position) {
        userHolder.followActionBtn.setTag(position);
    }

    public void updateFollowUIBeforeAPICall(PostHolder holder) {
//        holder.favoriteButton.setEnabled(false);
//        holder.favoriteButton.setImageResource(R.drawable.ic_favorite_true);
    }

    public void updateFollowUIAfterAPICall(UserHolder userHolder, User user) {
//        if(post.isFavorite()) {
//            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_true);
//            holder.favoriteCount.setText(post.getFavoriteCount());
//        } else {
//            holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
//        }
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public interface UserListAdapterListener  {
        void showUserProfile(User user);
        void followUser(User user);
        void unfollowUser(User user);
    }

}
