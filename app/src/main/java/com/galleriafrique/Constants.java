package com.galleriafrique;

import java.text.SimpleDateFormat;

/**
 * Created by osifo on 8/3/15.
 */
public class Constants {
    public static final String ENDPOINT                     = "http://tilteapp.herokuapp.com/api";
    public static final String PAGE_NUMBER                  = "page";
    public static final String TAG                          = "Galleriafrique";
    public static final String START_DATE                   = "startDate";
    public static final String END_DATE                     = "endDate";
    public static final String PARAM_POST_IMAGE             = "post[image]";
    public static final String PARAM_POST_DESCRIPTION       = "post[description]";
    public static final String PARAM_POST_USER_ID           = "post[user_id]";
    public static final String PARAM_USER_ID                = "user_id";
    public static final String PARAM_POST_ID                = "post_id";

    public static final String GET_POSTS_FAILED             = "Posts could not be loaded, Try again.";
    public static final String CREATE_POSTS_FAILED          = "Posts could not be created, Try again.";
    public static final SimpleDateFormat DATE_FORMAT        = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ZZZZ");

    public static final String USER_DATA                    = "user";
    public static final String SHARE_WITH                   = "Share this post with";
    public static final String FAVORITE_POST_FAILED         = "Post could not be shared. Please try again";

}
