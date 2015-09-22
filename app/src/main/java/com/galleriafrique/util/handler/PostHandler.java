package com.galleriafrique.util.handler;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.galleriafrique.model.post.Post;
import com.galleriafrique.provider.DataContract;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inbuilt on 6/10/2015.
 */
public class PostHandler {
     static int mPostCount = 0;

    public static void savePostData(Context context, List<Post> data) throws RemoteException, OperationApplicationException {

        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        Uri uri = DataContract.addCallerIsSyncAdapterParameter(
                DataContract.Post.CONTENT_URI);
        //ContentProviderOperation.newDelete(uri).build();
        for(Post post : data){
            ContentProviderOperation.Builder builder = ContentProviderOperation
                    .newInsert(uri)
                    .withValue(DataContract.Post.POST_ID, post.getId())
                    .withValue(DataContract.Post.USERNAME, post.getUsername())
                    .withValue(DataContract.Post.DESCRIPTION, post.getDescription())
                    .withValue(DataContract.Post.IMAGE_URL, post.getImage())
                    .withValue(DataContract.Post.USER_AVATAR, post.getUserAvatar())
                    .withValue(DataContract.Post.CREATED_AT, post.getCreatedAt())
                    .withValue(DataContract.Post.COMMENT_COUNT, post.getCommentCount())
                    .withValue(DataContract.Post.LIKE_COUNT, post.getLikeCount());
            operations.add(builder.build());
            //increment the post count
            mPostCount++;
        }
        Log.d("POSTHANDLER", "THE OPERATION SIZE IS "+mPostCount);

        if (operations.size() > 0) {
            ContentResolver resolver = context.getContentResolver();

            resolver.applyBatch(DataContract.CONTENT_AUTHORITY, operations);
            Log.d("POSTHANDLER", "THE INSERT OPERATION WAS SUCCESSFUL");

//            //Tell us that we have done data bootstrap
//            PrefUtils.markDataBootstrapDone(context);
        }else{
            Log.d("POSTHANDLER", "THE OPERATION SIZE IS ZERO");
        }

    }


    public int getPostCount() {
        return mPostCount;
    }
}
