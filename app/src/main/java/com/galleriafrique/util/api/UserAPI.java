package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.user.UserResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by osifo on 8/3/15.
 */
public interface UserAPI {

    @GET("/users/followers")
    void getFollowers(@Query(Constants.PARAM_USER_ID) String user_id, Callback<UserResponse> callback);

    @GET("/users/following")
    void getFollowing(@Query(Constants.PARAM_USER_ID) String user_id, Callback<UserResponse> callback);

}
