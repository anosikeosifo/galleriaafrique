package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.user.UserResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by osifo on 1/27/16.
 */
public interface LoginAPI {
    @FormUrlEncoded
    @POST("/sessions")
    void login(@Field(Constants.PARAM_USER_EMAIL) String user_email, @Field(Constants.PARAM_USER_PASSWORD) String user_password, Callback<UserResponse> callback);
}
