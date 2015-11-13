package com.galleriafrique.util.api;

import com.galleriafrique.Constants;
import com.galleriafrique.model.comment.CommentResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by osifo on 8/3/15.
 */
public interface CommentAPI {

    @FormUrlEncoded
    @POST("/comments")
    void addComment(@Field(Constants.PARAM_COMMENT_USER_ID) String user_id, @Field(Constants.PARAM_COMMENT_POST_ID) String post_id, @Field(Constants.PARAM_COMMENT_TEXT) String comment_text, Callback<CommentResponse> callback);
}
