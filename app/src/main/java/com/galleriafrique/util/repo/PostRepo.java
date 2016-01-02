package com.galleriafrique.util.repo;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.galleriafrique.Constants;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.CommentResponse;
import com.galleriafrique.model.post.FavoriteResponse;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.api.PostAPI;
import com.galleriafrique.util.handler.PostHandler;
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

        void retryFetchUserFeed(String userID);

        void retryFetchUserFavorites(String userID);

        void retryFetchUserPosts(String userID);

        void retryGetFavorites(String userID, String pageNumber);

        void retrySharePost(String userId,  String sharerID, String postID, int position);

        void retryFavoritePost(String userID, String postID, int position);

        void updateFavorite(FavoriteResponse.Favorite favorite, int position);

        void updatePosts(List<Post> posts);

        void updateFavorites(List<Post> posts);

        void updateUserPosts(List<Post> posts);

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
                    retryCount = 0;
                    while (retryCount < 4) {
                        if (postRepoListener != null) {
//                            postRepoListener.retryCreatePost(description, image, userId);
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
                        if (postRepoListener != null) {
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

    public void fetchUserFeed(final String userID) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.fetchFeed(userID, new Callback<PostResponse>() {
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
                    retryCount = 0;
                    while (retryCount < 4) {
                        if (postRepoListener != null) {
                            postRepoListener.retryFetchUserFeed(userID);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            postRepoListener.requestFailed();
        }

    }

    public void fetchUserFavorites(final String userID) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.fetchFavorites(userID, new Callback<PostResponse>() {
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

                            postRepoListener.updateFavorites(postResponse.getData());
                        } else {
                            String message = postResponse.getMessage();
                            if (message == null) {
                                message = Constants.GET_USER_FAVORITES_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            postRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    retryCount = 0;
                    while (retryCount < 4) {
                        if (postRepoListener != null) {
                            postRepoListener.retryFetchUserFavorites(userID);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            postRepoListener.requestFailed();
        }

    }

    public void fetchUserPosts(final String userID) {
        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);

        PostAPI postAPI =  restAdapter.create(PostAPI.class);

        if(postAPI != null) {
            postAPI.fetchUserPosts(userID, new Callback<PostResponse>() {
                @Override
                public void success(PostResponse postResponse, Response response) {
                    if (postRepoListener != null && postResponse != null) {

                        Log.d("POST_LIST", "post response: " + String.valueOf(postResponse.isSuccess()));
                        if (postResponse.isSuccess()) {
                            Log.d("POST_LIST", String.valueOf(postResponse.getData()));
                            postRepoListener.updateUserPosts(postResponse.getData());
                        } else {
                            String message = postResponse.getMessage();
                            if (message == null) {
                                message = Constants.GET_USER_POSTS_FAILED;
                            }
                            Log.d("POST_LIST", "post response: " + message);
                            postRepoListener.showErrorMessage(message);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    retryCount = 0;
                    while (retryCount < 4) {
                        if (postRepoListener != null) {
                            postRepoListener.retryFetchUserPosts(userID);
                            retryCount++;
                        }
                    }
                }
            });
        } else {
            postRepoListener.requestFailed();
        }

    }

    public void favoritePost(final String userID, final String postID, final int position) {

        RestAdapter restAdapter = RepoUtils.getAPIRestAdapter(context, Constants.ENDPOINT, networkHelper);
        if(restAdapter != null) {
            PostAPI postAPI = restAdapter.create(PostAPI.class);
            if (postAPI != null) {
                postAPI.favoritePost(userID, postID, new Callback<FavoriteResponse>() {
                    @Override
                    public void success(FavoriteResponse favoriteResponse, Response response) {
                        if (postRepoListener != null && favoriteResponse != null) {
                            if (favoriteResponse.isSuccess()) {
                                postRepoListener.updateFavorite(favoriteResponse.getFavorite(), position);
                            } else {
                                String message = favoriteResponse.getMessage();
                                if (message == null) {
                                    message = Constants.FAVORITE_POST_FAILED;
                                }
                                postRepoListener.showErrorMessage(message);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (postRepoListener != null) {
                            postRepoListener.retryFavoritePost(userID, postID, position);
                        }
                    }
                });
            }
        } else {
            postRepoListener.requestFailed();
        }

    }



    public void sharePost(String userId,  String sharerID, String postID, int position) {

    }

}
