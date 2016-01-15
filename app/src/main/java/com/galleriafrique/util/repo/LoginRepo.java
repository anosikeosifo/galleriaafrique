package com.galleriafrique.util.repo;

import android.content.Context;

import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.util.network.NetworkHelper;

/**
 * Created by osifo on 1/14/16.
 */
public class LoginRepo {

    private NetworkHelper networkHelper;
    public LoginRepoListener loginRepoListener;

    public Context context;
    private int retryCount = 0;

    public LoginRepo(Context context) {
        this.context = context;
        networkHelper = new NetworkHelper(context);
        loginRepoListener = (LoginRepoListener)context;
    }




    public interface LoginRepoListener {
        void authenticate();
    }
}
