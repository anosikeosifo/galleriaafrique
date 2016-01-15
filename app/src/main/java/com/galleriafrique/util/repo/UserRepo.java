package com.galleriafrique.util.repo;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.galleriafrique.Constants;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.model.user.User;
import com.galleriafrique.model.user.UserResponse;
import com.galleriafrique.util.api.PostAPI;
import com.galleriafrique.util.api.UserAPI;
import com.galleriafrique.util.handler.PostHandler;
import com.galleriafrique.util.network.NetworkHelper;
import com.galleriafrique.util.tools.RepoUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by osifo on 8/3/15.
 */
public class UserRepo {

    private NetworkHelper networkHelper;
    public UserRepoListener userRepoListener;

    public Context context;
    private int retryCount = 0;

    public UserRepo(BaseFragment fragment) {
        this.context = fragment.getActivity();
        networkHelper = new NetworkHelper(this.context);
        userRepoListener = (UserRepoListener) fragment;
    }

    public interface UserRepoListener {
        void showErrorMessage(String message);
        void requestFailed();
        void retryGetFollowers();
        void retryGetFollowing();
        void retryFollowUser(String follower_id, String followed_id);
        void retryUnfollowUser(String follower_id, String followed_id);
        void updateFollowers(List<User> followers);
        void updateFollowing(List<User> following);
        void updateFollowAction(UserResponse response);
    }

    public void getFollowers(final String user_id) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        UserAPI userAPI =  restAdapter.create(UserAPI.class);

        if(userAPI != null) {
            userAPI.getFollowers(user_id, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (userRepoListener != null && userResponse != null) {

                        Log.d("FOLLOWERS_LIST", "followers' response: " + String.valueOf(userResponse.isSuccess()));
                        if (userResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(userResponse.getData()));

                            userRepoListener.updateFollowers(userResponse.getData());
                        } else {
                            String message = userResponse.getMessage();
                            if (message == null) {
                                message = Constants.GET_FOLLOWERS_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            userRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (userRepoListener != null) {
                            userRepoListener.retryGetFollowers();
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            userRepoListener.requestFailed();
        }
    }


    public void getFollowing(final String user_id) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        UserAPI userAPI =  restAdapter.create(UserAPI.class);

        if(userAPI != null) {
            userAPI.getFollowing(user_id, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (userRepoListener != null && userResponse != null) {

                        Log.d("FOLLOWERS_LIST", "followers' response: " + String.valueOf(userResponse.isSuccess()));
                        if (userResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(userResponse.getData()));
                            userRepoListener.updateFollowing(userResponse.getData());
                        } else {
                            String message = userResponse.getMessage();
                            if (message == null) {
                                message = Constants.GET_FOLLOWING_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            userRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (userRepoListener != null) {
                            userRepoListener.retryGetFollowing();
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            userRepoListener.requestFailed();
        }
    }

    public void follow(final String follower_id, final String followed_id) {
        if(followed_id == follower_id) {
            userRepoListener.showErrorMessage("You cannot follow yourself");
            return;
        }

        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        UserAPI userAPI =  restAdapter.create(UserAPI.class);

        if(userAPI != null) {
            userAPI.follow(follower_id, followed_id, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (userRepoListener != null && userResponse != null) {

                        Log.d("FOLLOW_USER", "" + String.valueOf(userResponse.isSuccess()));
                        if (userResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(userResponse.getData()));
                            userRepoListener.updateFollowAction(userResponse);
                        } else {
                            String message = userResponse.getMessage();
                            if (message == null) {
                                message = Constants.FOLLOW_USER_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            userRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (userRepoListener != null) {
                            userRepoListener.retryFollowUser(follower_id, followed_id);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            userRepoListener.requestFailed();
        }
    }

    public void unfollow(final String follower_id, final String followed_id) {
        if(followed_id == follower_id) {
            userRepoListener.showErrorMessage("You cannot unfollow yourself");
            return;
        }

        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        UserAPI userAPI =  restAdapter.create(UserAPI.class);

        if(userAPI != null) {
            userAPI.unfollow(follower_id, followed_id, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    if (userRepoListener != null && userResponse != null) {

                        Log.d("UNFOLLOW_USER", "" + String.valueOf(userResponse.isSuccess()));
                        if (userResponse.isSuccess()) {
                            userRepoListener.updateFollowAction(userResponse);
                        } else {
                            String message = userResponse.getMessage();
                            if (message == null) {
                                message = Constants.UNFOLLOW_USER_FAILED;
                            }
                            userRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (userRepoListener != null) {
                            userRepoListener.retryUnfollowUser(follower_id, followed_id);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            userRepoListener.requestFailed();
        }
    }
}
