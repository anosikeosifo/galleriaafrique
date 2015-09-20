package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.post.PostResponse;


import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;

/**
 * Created by osifo on 8/3/15.
 */
public interface PostAPI {

    @GET("/posts")
    //void getAllPosts(@Field(Constants.START_DATE)  String startDate, @Field(Constants.END_DATE) String endDate, Callback<PostResponse> callback);
    void getAllPosts(Callback<PostResponse> callback);

//    void createPosts();

}
