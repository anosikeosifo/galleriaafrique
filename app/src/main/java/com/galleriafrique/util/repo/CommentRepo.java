package com.galleriafrique.util.repo;

import android.content.Context;

import com.galleriafrique.Constants;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.CommentResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.api.CommentAPI;
import com.galleriafrique.util.api.PostAPI;
import com.galleriafrique.util.network.NetworkHelper;
import com.galleriafrique.util.tools.RepoUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by osifo on 8/3/15.
 */
public class CommentRepo {

    public CommentRepoListener commentRepoListener;
    private NetworkHelper networkHelper;
    public Context context;
    private int retryCount = 0;

    public CommentRepo(BaseFragment fragment) {
        this.context = fragment.getActivity();
        networkHelper = new NetworkHelper(this.context);
        commentRepoListener = (CommentRepoListener)fragment;
    }

    public interface CommentRepoListener {
        void updatePostComments(String postId, String commentText);

        void retryAddComments(String userID, String postID, String commentText);

        void showErrorMessage(String message);

        void requestFailed();
    }


    public void addComment(final String userID, final String postID, final String commentText) {

        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);
        if(restAdapter != null) {
            CommentAPI commentAPI = restAdapter.create(CommentAPI.class);
            if (commentAPI != null) {
                commentAPI.addComment(userID, postID, commentText, new Callback<CommentResponse>() {
                    @Override
                    public void success(CommentResponse commentResponse, Response response) {
                        if (commentRepoListener != null && commentResponse != null) {
                            if (commentResponse.isSuccess()) {
                                CommonUtils.log(commentResponse);
//                                commentRepoListener.updatePostComments("", "");
                            } else {
                                String message = commentResponse.getMessage();
                                if (message == null) {
                                    message = Constants.FAVORITE_POST_FAILED;
                                }
                                commentRepoListener.showErrorMessage(message);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (commentRepoListener != null) {
                            commentRepoListener.retryAddComments(userID, postID, commentText);
                        }
                    }
                });
            }
        } else {
            commentRepoListener.requestFailed();
        }

    }
}
