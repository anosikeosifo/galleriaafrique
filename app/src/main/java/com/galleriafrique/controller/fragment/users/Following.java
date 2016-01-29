package com.galleriafrique.controller.fragment.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
public class Following extends BaseFragment implements UserRepo.UserRepoListener, UserFollowersAdapter.UserFollowersAdapterListener {
    private UserProfileActivity activity;
    private UserRepo userRepo;
    private List<User> followingList;
    private com.galleriafrique.controller.fragment.base.ListView followingListView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserFollowersAdapter followingListAdapter;
    private User user;
    private boolean isLoading = false;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_following_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUser();
        this.userRepo = new UserRepo(this);
        this.activity = (UserProfileActivity) getActivity();
        this.followingList = new ArrayList<User>();
        initUI(view);
        setListeners();
        loadFollowing();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (followingList.size() < 1) {
            loadFollowing();
        }
    }

    private void setUser() {
        String userData = getActivity().getIntent().getExtras().getString(User.USER_DATA);
        if(!userData.isEmpty()) {
            user = CommonUtils.getGson().fromJson(userData, new TypeToken<User>(){}.getType());
        }
    }

    public void initUI(View view) {
        followingListView = (com.galleriafrique.controller.fragment.base.ListView) view.findViewById(R.id.following_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_Layout);
        followingListAdapter = new UserFollowersAdapter(this, followingList);
        followingListView.setAdapter(followingListAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFollowing();
            }
        });
    }

    private void loadFollowing() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        userRepo.getFollowing(String.valueOf(user.getId()));
    }

    private void reloadFollowing() {
        if (followingList != null && followingListAdapter != null) {
            followingList.clear();
            followingListAdapter.notifyDataSetChanged();
        }

        userRepo.getFollowing(String.valueOf(user.getId()));
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showFollowing() {
        if (followingListAdapter == null) {
            followingListAdapter = new UserFollowersAdapter(this, followingList);
        } else {
            followingListAdapter.notifyDataSetChanged();
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
    public void updateFollowing(List<User> following) {
        for(User user : following) {
            if(!followingList.contains(user)) {
                followingList.add(user);
            }
        }

        showFollowing();

        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void retryGetFollowing() {
        userRepo.getFollowing(String.valueOf(user.getId()));
    }

    @Override
    public void retryGetFollowers() {

    }

    @Override
    public void updateFollowers(List<User> followers) {

    }


    @Override
    public void retryFollowUser(String follower_id, String followed_id) {

    }

    @Override
    public void retryUnfollowUser(String follower_id, String followed_id) {

    }

    @Override
    public void showUserProfile(User user) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(User.USER_DATA, CommonUtils.getGson().toJson(user).toString());
        getActivity().finish();
        getActivity().startActivity(intent);
    }

    @Override
    public void updateFollowAction(UserResponse response) {

    }

    @Override
    public void updateUnfollowAction(UserResponse response) {
        if(response.isSuccess()) {
            followingList.remove(response.getData());
            followingListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void unfollowUser(int user_id) {
        userRepo.unfollow("12", String.valueOf(user_id));
    }

    @Override
    public void followUser(int user_id) {
        userRepo.follow("12", String.valueOf(user_id));
    }
}
