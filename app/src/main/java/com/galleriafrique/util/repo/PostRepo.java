package com.galleriafrique.util.repo;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.galleriafrique.Constants;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.util.api.PostAPI;
import com.galleriafrique.util.handler.PostHandler;
import com.galleriafrique.util.network.NetworkHelper;
import com.galleriafrique.util.tools.RepoUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by osifo on 8/3/15.
 */
public class PostRepo {

    private NetworkHelper networkHelper;
    public PostRepoListener postRepoListener;
    public Context context;

    public PostRepo(Context context) {
        networkHelper = new NetworkHelper(context);
        this.context = context;
    }

    public void setPostRepoListener(PostRepoListener postRepoListener) {
        this.postRepoListener = postRepoListener;
    }

    public interface  PostRepoListener {
        void retryGetAllPosts(String pageNumber, String startDate, String endDate);

        void retryGetUserFeed(String userID, String pageNumber);

        void retryGetFavorites(String userID, String pageNumber);

        void retryLikePost(String userID, String postID, int position);

        void retrySharePost(String userId,  String sharerID, String postID, int position);

        void retryFavoritePost(String userID, String postID, int position);

        void updateLike(LikeResponse.Like like, String postID, int position);

        void updatePosts(PostResponse postResponse);

        void showErrorMessage(String message);

        void requestFailed();
    }

    public void getAllPosts(final String pageNumber, final String startDate, final String endDate) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.getAllPosts(pageNumber,startDate, endDate,  new Callback<PostResponse>() {
                @Override
                public void success(PostResponse postResponse, Response response) {
                    if (postRepoListener != null && postResponse != null) {

                        Log.d("POST_LIST", "post response: " + String.valueOf(postResponse.isSuccess()));
                        if (postResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(postResponse.getData()));

                            //save the data into the database
                            try {
                                PostHandler.savePostData(context, postResponse.getData());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (OperationApplicationException e) {
                                e.printStackTrace();
                            }

                            postRepoListener.updatePosts(postResponse.getData());
                        } else {
                            String message = "failed";
                            if (message == null) {
                                message = Constants.GET_POSTS_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            postRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if(postRepoListener != null) {
                        postRepoListener.retryGetAllPosts(pageNumber,startDate, endDate);
                    }
                }
            });
        } else {
            postRepoListener.requestFailed();
        }

    }

    public void getAllPostsByLocation(String pageNumber) {

    }

    public void getUserFeed(String userID, String pageNumber) {

    }

    public void likePost(String userID, String postID, int position) {

    }

    public void sharePost(String userId,  String sharerID, String postID, int position) {

    }

    public void favoritePost(String userID, String postID, int position) {

    }

    public void createPost() {

    }

}
