package com.galleriafrique.controller.fragment.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.BaseActivity;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.controller.activity.base.UserProfileActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.FavoriteResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.view.adapters.PostsListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 12/3/15.
 */
public class Favorites extends BaseFragment implements PostRepo.PostRepoListener, PostsListAdapter.PostListAdapterListener {
    private UserProfileActivity activity;
    private RecyclerView favoritesListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private List<Post> favoritesList;
    private PostRepo postRepo;
    private PostsListAdapter postsListAdapter;
    private boolean isLoading = true;
    private User user;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public String getTitleText() {
        return "Post feed";
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_posts_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUser();
        this.postRepo = new PostRepo(this);
        this.activity = (UserProfileActivity)getActivity();
        this.favoritesList = new ArrayList<Post>();
        initUI(view);
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (favoritesList.size() < 1) {
            loadFavorites();
        }
    }

    public void initUI(View view) {
        favoritesListView = (RecyclerView) view.findViewById(R.id.user_posts_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        layoutManager = new LinearLayoutManager(this.getActivity());
        favoritesListView.setLayoutManager(layoutManager);
        favoritesListView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_Layout);
        postsListAdapter = new PostsListAdapter(this, favoritesList);
        favoritesListView.setAdapter(postsListAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFavorites();
            }
        });
    }

    public void loadFavorites() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        postRepo.fetchUserFavorites(String.valueOf(user.getId()));
    }

    @Override
    public void retryFetchUserFavorites(String userID) {
        postRepo.fetchUserFavorites(String.valueOf(user.getId()));
    }

    @Override
    public void retryFetchUserPosts(String userID) {

    }

    @Override
    public void retryGetAllPosts() { }

    @Override
    public void retryFetchUserFeed(String userID) { }

    @Override
    public void retryGetFavorites(String userID, String pageNumber) {

    }

    @Override
    public void retrySharePost(String userId, String sharerID, String postID, int position) {

    }

    @Override
    public void retryFavoritePost(String userID, String postID, int position) {
        //postRepo.favoritePost(userID, postID, position);
    }

    @Override
    public void updateFavorite(FavoriteResponse.Favorite favorite, int position) {
        postsListAdapter.updateFavorite(favorite, position);
    }

    @Override
    public void updateFavorites(List<Post> data) {
        updateFavoritesList(data);
    }

    @Override
    public void updateUserPosts(List<Post> posts) {

    }

    @Override
    public void updatePosts(List<Post> data) {  }

    private void updateFavoritesList(List<Post> data) {
        for(Post post : data) {
            if(!favoritesList.contains(post)) {
                favoritesList.add(post);
            }
        }

        showFavorites();

        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showFavorites() {
        if (postsListAdapter == null) {
            postsListAdapter = new PostsListAdapter(this, favoritesList);
        } else {
            postsListAdapter.notifyDataSetChanged();
        }
    }

    public void reloadFavorites() {
        if (favoritesList != null && postsListAdapter != null) {
            favoritesList.clear();
            postsListAdapter.notifyDataSetChanged();
        }

        postRepo.fetchUserFavorites(String.valueOf(user.getId()));
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setUser() {
        String userData = getActivity().getIntent().getExtras().getString(User.USER_DATA);
        if(!userData.isEmpty()) {
            user = CommonUtils.getGson().fromJson(userData, new TypeToken<User>(){}.getType());
        }
    }

    @Override
    public void favoritePost(Post post, int position) {
        postRepo.favoritePost(String.valueOf(user.getId()), String.valueOf(post.getId()), position);
    }

    @Override
    public void addPostComment() {

    }

    @Override
    public void showPostDetails(Post post) {
        Intent intent = new Intent(activity,HomeActivity.class);
        intent.putExtra("fragment", "PostDetails");
        intent.putExtra(Post.POST_DATA, CommonUtils.getGson().toJson(post).toString());
        startActivity(intent);
    }

    @Override
    public void showUserProfile(User user) {Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(User.USER_DATA, CommonUtils.getGson().toJson(user).toString());
        activity.startActivity(intent);
    }


    @Override
    public void sharePost(BaseActivity activity, Post post) {

        CommonUtils.sharePost(activity, post);
    }

    @Override
    public void showErrorMessage(String message) {
        CommonUtils.toast(activity, message);
    }

    @Override
    public void requestFailed() {

    }

    @Override
    public void retryCreatePost(String description, String image, String user_id) {

    }

    @Override
    public void createPostSuccessful(Post post) {

    }

    @Override
    public String getTagText() {
        return super.getTagText();
    }

    @Override
    public void repost(String userID, String postID) {

    }

    @Override
    public void repostSuccessful(Post post) {

    }

    @Override
    public void retryRepost(String userID, String postID) {

    }
}
