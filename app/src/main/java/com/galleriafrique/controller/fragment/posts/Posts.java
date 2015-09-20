package com.galleriafrique.controller.fragment.posts;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.Home;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.view.adapters.PostsListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Posts extends BaseFragment implements  PostRepo.PostRepoListener, PostsListAdapter.PostListAdapterListener{
    private Home activity;
    private RecyclerView postsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private List<Post> postList;
    private PostRepo postRepo;
    private PostsListAdapter postsListAdapter;
    private boolean isLoading = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setToolbarTitle(getTagText());
    }

    @Override
    public String getTagText() {
        return getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.postRepo = new PostRepo(this);
        this.activity = (Home)getActivity();
        this.postList = new ArrayList<Post>();
        initUI(view);
        setListeners();
        loadPosts();
    }

    public void initUI(View view) {
        postsListView = (RecyclerView) view.findViewById(R.id.post_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        layoutManager = new LinearLayoutManager(this.getActivity());
        postsListView.setLayoutManager(layoutManager);
        postsListView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_Layout);
        postsListAdapter = new PostsListAdapter(this, postList);
        postsListView.setAdapter(postsListAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadPosts();
            }
        });
    }

    public void loadPosts() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        postRepo.getAllPosts(); //test arguments
    }

    @Override
    public void retryGetAllPosts() {

    }

    @Override
    public void retryGetUserFeed(String userID, String pageNumber) {

    }

    @Override
    public void retryGetFavorites(String userID, String pageNumber) {

    }

    @Override
    public void retryLikePost(String userID, String postID, int position) {

    }

    @Override
    public void retrySharePost(String userId, String sharerID, String postID, int position) {

    }

    @Override
    public void retryFavoritePost(String userID, String postID, int position) {

    }

    @Override
    public void updateLike(LikeResponse.Like like, String postID, int position) {

    }

    @Override
    public void updatePosts(List<Post> data) {
       updatePostList(data);
    }

    private void updatePostList(List<Post> data) {
        for(Post post : data) {
            if(!postList.contains(post)) {
                postList.add(post);
            }
        }

        showPosts();

        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showPosts() {
        if (postsListAdapter == null) {
            postsListAdapter = new PostsListAdapter(this, postList);
        } else {
            postsListAdapter.notifyDataSetChanged();
        }
    }

    public void reloadPosts() {

        if (postList != null && postsListAdapter != null) {
            //postList.clear();
            postsListAdapter.notifyDataSetChanged();
        }
        //this should be changed to getUserFeed, so it loads just user specific stuff
        postRepo.getAllPosts();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addPostComment() {

    }

    @Override
    public void showPostDetails(Post post) {

    }

    @Override
    public void showUserProfile() {

    }

    @Override
    public void likePost() {

    }

    @Override
    public void sharePost() {

    }

    @Override
    public void showErrorMessage(String message) {
        CommonUtils.log(message);
    }

    @Override
    public void requestFailed() {

    }

}
