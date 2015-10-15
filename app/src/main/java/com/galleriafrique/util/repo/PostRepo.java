package com.galleriafrique.util.repo;

import android.content.Context;
import android.util.Log;

import com.galleriafrique.Constants;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.api.PostAPI;
import com.galleriafrique.util.network.NetworkHelper;
import com.galleriafrique.util.tools.RepoUtils;

import java.util.List;

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
    private int retryCount = 0;

    public PostRepo(BaseFragment fragment) {
        this.context = fragment.getActivity();
        networkHelper = new NetworkHelper(this.context);
        postRepoListener = (PostRepoListener) fragment;
    }

    public interface  PostRepoListener {
        void retryGetAllPosts();

        void retryCreatePost(String description, String image, String user_id);

        void retryGetUserFeed(String userID, String pageNumber);

        void retryGetFavorites(String userID, String pageNumber);

        void retryLikePost(String userID, String postID, int position);

        void retrySharePost(String userId,  String sharerID, String postID, int position);

        void retryFavoritePost(String userID, String postID, int position);

        void updateLike(LikeResponse.Like like, String postID, int position);

        void updatePosts(List<Post> posts);

        void createPostSuccessful(Post post);

        void showErrorMessage(String message);

        void requestFailed();
    }

    public void createPost(final String description, final String image, final String userId) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.createPost(CommonUtils.getTypedString(description), CommonUtils.getTypedFile(image), CommonUtils.getTypedString(userId), new Callback<PostResponse>() {
                @Override
                public void success(PostResponse postResponse, Response response) {
                    if (postRepoListener != null && postResponse != null) {
                        Log.d("CREATE_POST", "post response: " + String.valueOf(postResponse.isSuccess()));
                        if (postResponse.isSuccess()) {
                            Log.d("CREATE_POST", String.valueOf(postResponse.getData()));
                            postRepoListener.createPostSuccessful(new Post());
                        } else {
                            String message = postResponse.getMessage();
                            if (message == null) {
                                message = Constants.CREATE_POSTS_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            postRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    while (retryCount < 4) {
                        if (postRepoListener != null) {
                            postRepoListener.retryCreatePost(description, image, userId);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            postRepoListener.requestFailed();
        }

    }

    public void getAllPosts() {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.getAllPosts(new Callback<PostResponse>() {
                @Override
                public void success(PostResponse postResponse, Response response) {
                    if (postRepoListener != null && postResponse != null) {
                        Log.d("POST_LIST", "post response: " + String.valueOf(postResponse.isSuccess()));
                        if (postResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(postResponse.getData()));
                            postRepoListener.updatePosts(postResponse.getData());
                        } else {
                            String message = postResponse.getMessage();
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
                    while (retryCount < 4) {
                        if(postRepoListener != null) {
                            postRepoListener.retryGetAllPosts();
                            retryCount++;
                        }
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

}
