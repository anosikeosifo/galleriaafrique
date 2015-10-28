package com.galleriafrique.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * Created by Onyecar on 2/26/2015.
 */
public class DataContract
{
    //authority of data provider
    public static final String CONTENT_AUTHORITY = "com.galleriafrique.provider";

    //authority of base URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Paths
    public static final String PATH_POST = "post";
    public static final String PATH_WORKFLOW_HISTORY = "workflowHistory";



    private static final String CALLER_IS_SYNCADAPTER = "caller_is_sync_adapter";




    public static class Post implements PostColumns, BaseColumns {
        /** Content URI for  creditBatchDetails table */
        public static final Uri CONTENT_URI  =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POST).build();

        /** The mime type of a single item */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/vnd.com.galleriafrique.provider.post";

        /** The mime type of a single item */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/vnd.com.galleriafrique.provider.post";

        /** A projection of all tables in creditBatchDetails table */

        public static final String[] PROJECTION_ALL = {
                _ID, POST_ID, USERNAME, DESCRIPTION, IMAGE_URL, CREATED_AT, USER_AVATAR, COMMENT_COUNT, FAVORITE_COUNT
        };

        /** The default sort order for queries containing monthlySummary */
        public static final String SORT_ORDER_DEFAULT = _ID +" DESC";
    }



    public interface SyncColumns{
        String UPDATED = "updated";
    }

    interface PostColumns {
        String POST_ID = "postId";
        String USERNAME = "username";
        String DESCRIPTION = "description";
        String IMAGE_URL = "image_url";
        String CREATED_AT = "created_at";
        String USER_AVATAR = "user_avatar";
        String COMMENT_COUNT = "comment_count";
        String FAVORITE_COUNT = "favorite_count";
    }



    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(
                DataContract.CALLER_IS_SYNCADAPTER, "true").build();
    }

    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        return TextUtils.equals("true",
                uri.getQueryParameter(DataContract.CALLER_IS_SYNCADAPTER));
    }
}
