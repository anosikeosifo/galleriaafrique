package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.user.UserResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by osifo on 8/3/15.
 */
public interface UserAPI {

    @FormUrlEncoded
    @POST("/users/followers")
    // @Field(Constants.PARAM_PAGE_NUMBER) String pageNumber, @Field(Constants.PARAM_LIMIT) String limit, // to add these later
    void getFollowers(@Field(Constants.PARAM_USER_ID) String user_id, Callback<UserResponse> callback);


    @FormUrlEncoded
    @POST("/users/following")
        // @Field(Constants.PARAM_PAGE_NUMBER) String pageNumber, @Field(Constants.PARAM_LIMIT) String limit, // to add these later
    void getFollowing(@Field(Constants.PARAM_USER_ID) String user_id, Callback<UserResponse> callback);

}
