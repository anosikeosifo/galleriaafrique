package com.galleriafrique.controller.fragment.users;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.UserProfileActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 12/3/15.
 */
public class Following extends BaseFragment implements UserRepo.UserRepoListener {
    private UserProfileActivity activity;
    private UserRepo userRepo;
    private List<User> userList;

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
        this.userRepo = new UserRepo(this);
        this.activity = (UserProfileActivity) getActivity();
        this.userList = new ArrayList<User>();
//        initUI(view);
//        setListeners();
//        loadPosts();
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
    public void updateFollowers(List<User> followers) {

    }

    @Override
    public void updateFollowing(List<User> following) {

    }
}
