package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.post.FavoriteResponse;
import com.galleriafrique.model.post.PostResponse;


import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by osifo on 8/3/15.
 */
public interface PostAPI {

    @GET("/posts")
    //void getAllPosts(@Field(Constants.START_DATE)  String startDate, @Field(Constants.END_DATE) String endDate, Callback<PostResponse> callback);
    void getAllPosts(Callback<PostResponse> callback);

    @FormUrlEncoded
    @POST("/users/feed")
    // @Field(Constants.PARAM_PAGE_NUMBER) String pageNumber, @Field(Constants.PARAM_LIMIT) String limit, // to add these later
    void fetchFeed(@Field(Constants.PARAM_USER_ID) String user_id, Callback<PostResponse> callback);

    @Multipart
    @POST("/posts")
    void createPost(@Part(Constants.PARAM_POST_DESCRIPTION) TypedString description, @Part(Constants.PARAM_POST_IMAGE) TypedFile image,@Part(Constants.PARAM_POST_USER_ID) TypedString user_id, Callback<PostResponse> callback);

    @FormUrlEncoded
    @POST("/posts/favorite")
    void favoritePost(@Field(Constants.PARAM_USER_ID) String user_id, @Field(Constants.PARAM_POST_ID) String post_id, Callback<FavoriteResponse> callback);
}
