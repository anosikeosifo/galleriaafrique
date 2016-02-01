package com.galleriafrique.util.repo;

import android.content.Context;

import com.galleriafrique.Constants;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.model.user.UserResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.api.LoginAPI;
import com.galleriafrique.util.network.NetworkHelper;
import com.galleriafrique.util.tools.RepoUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


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

    public void login(final String email, final String password) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        LoginAPI loginAPI =  restAdapter.create(LoginAPI.class);

        if(loginAPI != null) {
            loginAPI.login(CommonUtils.getSafeString(email), CommonUtils.getSafeString(password), new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    CommonUtils.log("message: " + userResponse.getMessage());
                    if (loginRepoListener != null && userResponse != null) {
                        if (userResponse.isSuccess()) {
                            loginRepoListener.loginSuccessful(userResponse.getData().get(0));
                        } else {
                            String message = userResponse.getMessage();
                            CommonUtils.log("message2: " + message);
                            if (message == null) {
                                message = Constants.CREATE_POSTS_FAILED;
                            }
                            loginRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (loginRepoListener != null) {
                            loginRepoListener.retryLogin(email, password);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            loginRepoListener.requestFailed();
        }

    }

    public interface LoginRepoListener {
        void loginSuccessful(User user);
        void retryLogin(String email, String password);
        void showErrorMessage(String message);
        void requestFailed();
    }
}
