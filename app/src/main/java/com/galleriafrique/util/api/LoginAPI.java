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
 * Created by osifo on 1/27/16.
 */
public interface LoginAPI {
    @FormUrlEncoded
    @POST("/sessions")
    void login(@Field(Constants.PARAM_USER_EMAIL) String user_email, @Field(Constants.PARAM_USER_PASSWORD) String user_password, Callback<UserResponse> callback);

    @GET("/users/profile")
    void getInfo(@Query(Constants.PARAM_USER_EMAIL) String user_email, Callback<UserResponse> callback);

    @POST("/omniauth_sessions")
    void loginWithOauth(@Query(Constants.PARAMS_OAUTH_PROVIDER) String provider, @Query(Constants.PARAM_USER_EMAIL) String user_email, @Query(Constants.PARAM_USER_NAME) String username, @Query(Constants.PARAM_USER_UID) String uid, @Query(Constants.PARAM_USER_TOKEN) String token, @Query(Constants.PARAM_USER_PROFILE_PHOTO_URL) String profile_photo_url, Callback<UserResponse> callback);
}
