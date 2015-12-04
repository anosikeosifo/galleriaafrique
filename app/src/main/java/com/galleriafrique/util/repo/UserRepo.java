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
        void updateFollowers(List<User> followers);
        void updateFollowing(List<User> following);
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

//                            //save the data into the database
//                            try {
//                                UserHandler.savePostData(context, userResponse.getData());
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                            } catch (OperationApplicationException e) {
//                                e.printStackTrace();
//                            }

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

//                            //save the data into the database
//                            try {
//                                UserHandler.savePostData(context, userResponse.getData());
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                            } catch (OperationApplicationException e) {
//                                e.printStackTrace();
//                            }

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
}
