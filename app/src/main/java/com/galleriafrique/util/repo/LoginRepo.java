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

    public void loginWithFacebook(final String email, final String name, final String uid, final String token, final String photo_url) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        final LoginAPI loginAPI =  restAdapter.create(LoginAPI.class);

        if(loginAPI != null) {
            loginAPI.loginWithOauth("facebook", email, name, uid, token, photo_url, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (loginRepoListener != null && userResponse != null) {
                        if (userResponse.isSuccess()) {
                            CommonUtils.log(userResponse.getMessage());
                            if (userResponse.getMessage() == "existing_user") {
                                loginRepoListener.loginSuccessful(userResponse.getData().get(0));
                                CommonUtils.log(userResponse.getData().get(0));
                                CommonUtils.toast(context, userResponse.getData().get(0).toString());
                            } else {
                                //show user onboarding view
                                loginRepoListener.loginSuccessful(userResponse.getData().get(0));

                            }
                        } else {
                            String message = userResponse.getMessage();
                            CommonUtils.log("fb login message: " + message);
                            if (message == null) {
                                message = Constants.FACEBOOK_LOGIN_FAILED;
                            }
                            loginRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (loginRepoListener != null) {
                            loginRepoListener.retryFacebookLogin(email, name, uid, token, photo_url);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            loginRepoListener.requestFailed();
        }
    }

    public void loginWithGoogle(final String email, final String name, final String uid, final String token, final String photo_url) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        final LoginAPI loginAPI =  restAdapter.create(LoginAPI.class);

        if(loginAPI != null) {
            loginAPI.loginWithOauth("google", email, name, uid, token, photo_url, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (loginRepoListener != null && userResponse != null) {
                        if (userResponse.isSuccess()) {
                            CommonUtils.log(userResponse.getMessage());
                            if (userResponse.getMessage() == "existing_user") {
                                loginRepoListener.loginSuccessful(userResponse.getData().get(0));
                                CommonUtils.log(userResponse.getData().get(0));
                                CommonUtils.toast(context, userResponse.getData().get(0).toString());
                            } else {
                                //show user onboarding view
                                loginRepoListener.loginSuccessful(userResponse.getData().get(0));

                            }
                        } else {
                            String message = userResponse.getMessage();
                            CommonUtils.log("fb login message: " + message);
                            if (message == null) {
                                message = Constants.FACEBOOK_LOGIN_FAILED;
                            }
                            loginRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (loginRepoListener != null) {
                            loginRepoListener.retryGoogleLogin(email, name, uid, token, photo_url);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            loginRepoListener.requestFailed();
        }
    }

    public void signup(final String email, final String password) {
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
        void retryGetInfo(String email);
        void facebookLoginSuccessful(UserResponse response);
        void retryFacebookLogin(String email, String uid, String name, String token, String photoUrl);
        void retryGoogleLogin(String email, String uid, String name, String token, String photoUrl);
    }
}
