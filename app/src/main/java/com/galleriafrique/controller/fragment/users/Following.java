package com.galleriafrique.controller.fragment.users;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.UserProfileActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.UserRepo;
import com.galleriafrique.view.adapters.UsersListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 12/3/15.
 */
public class Following extends BaseFragment implements UserRepo.UserRepoListener, UsersListAdapter.UserListAdapterListener {
    private UserProfileActivity activity;
    private UserRepo userRepo;
    private List<User> followingList;
    private com.galleriafrique.controller.fragment.base.ListView followingListView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UsersListAdapter followingListAdapter;
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
        followingListAdapter = new UsersListAdapter(this, followingList);
        followingListView.setAdapter(followingListAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFollowers();
            }
        });
    }

    private void loadFollowing() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        userRepo.getFollowing(String.valueOf(user.getId()));
    }

    private void reloadFollowers() {
        if (followingList != null && followingListAdapter != null) {
            followingList.clear();
            followingListAdapter.notifyDataSetChanged();
        }

        loadFollowing();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showFollowers() {
        if (followingListAdapter == null) {
            followingListAdapter = new UsersListAdapter(this, followingList);
        } else {
            followingListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void requestFailed() {

    }

    @Override
    public void retryGetFollowers() {

    }

    @Override
    public void updateFollowers(List<User> following) {
        for(User user : following) {
            if(!followingList.contains(user)) {
                followingList.add(user);
            }
        }

        showFollowers();

        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateFollowing(List<User> following) {

    }

    @Override
    public void showUserProfile(User user) {

    }

    @Override
    public void followUser(User user) {

    }

    @Override
    public void unfollowUser(User user) {

    }
}
