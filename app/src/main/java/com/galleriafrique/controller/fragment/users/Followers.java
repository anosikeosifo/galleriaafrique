package com.galleriafrique.controller.fragment.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.galleriafrique.Constants;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.UserProfileActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.model.user.UserResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.UserRepo;
import com.galleriafrique.view.adapters.UserFollowersAdapter;
import com.galleriafrique.view.adapters.UsersListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 12/3/15.
 */
public class Followers extends BaseFragment implements UserRepo.UserRepoListener, UserFollowersAdapter.UserFollowersAdapterListener {
    private UserProfileActivity activity;
    private UserRepo userRepo;
    private List<User> followersList;
    private com.galleriafrique.controller.fragment.base.ListView followersListView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  UserFollowersAdapter followersListAdapter;
    private User user;
    private boolean isLoading = true;

    @Override
    public String getTitleText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_followers_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUser();
        this.userRepo = new UserRepo(this);
        this.activity = (UserProfileActivity) getActivity();
        this.followersList = new ArrayList<User>();
        initUI(view);
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFollowers();
    }

    private void setUser() {
        String userData = getActivity().getIntent().getExtras().getString(User.USER_DATA);
        if(!userData.isEmpty()) {
            user = CommonUtils.getGson().fromJson(userData, new TypeToken<User>(){}.getType());
        }
    }

    public void initUI(View view) {
        followersListView = (com.galleriafrique.controller.fragment.base.ListView) view.findViewById(R.id.followers_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_Layout);
        followersListAdapter = new UserFollowersAdapter(this, followersList);
        followersListView.setAdapter(followersListAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFollowers();
            }
        });
    }

    private void loadFollowers() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        userRepo.getFollowers(String.valueOf(user.getId()));
    }

    private void reloadFollowers() {
        if (followersList != null && followersListAdapter != null) {
            followersList.clear();
            followersListAdapter.notifyDataSetChanged();
        }

        userRepo.getFollowers(String.valueOf(user.getId()));
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void updateFollowers(List<User> followers) {
        for(User user : followers) {
            if(!followersList.contains(user)) {
                followersList.add(user);
            }
        }

        showFollowers();

        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showFollowers() {
        if (followersListAdapter == null) {
            followersListAdapter = new UserFollowersAdapter(this, followersList);
        } else {
            followersListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        CommonUtils.toast(getActivity(), message);
    }

    @Override
    public void requestFailed() {
        CommonUtils.toast(getActivity(), Constants.GET_FOLLOWERS_FAILED);
    }

    @Override
    public void retryGetFollowers() {
        userRepo.getFollowers(String.valueOf(user.getId()));
    }

    @Override
    public void retryGetFollowing() { }

    @Override
    public void retryFollowUser(String follower_id, String followed_id) {

    }

    @Override
    public void retryUnfollowUser(String follower_id, String followed_id) {

    }


    @Override
    public void updateFollowing(List<User> following) {

    }

    @Override
    public void showUserProfile(User user) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(User.USER_DATA, CommonUtils.getGson().toJson(user).toString());
        getActivity().finish();
        getActivity().startActivity(intent);
    }

    @Override
    public void followUser(int user_id) {
        userRepo.follow("12", String.valueOf(user_id));
    }

    @Override
    public void unfollowUser(int user_id) {
        userRepo.unfollow("12", String.valueOf(user_id));
    }

    @Override
    public void updateFollowAction(UserResponse response) {
        if(response.isSuccess()) {
            followersListAdapter.notifyDataSetChanged();
        }
    }
}