package com.galleriafrique.controller.fragment.posts;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.view.adapters.PostsListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Posts extends BaseFragment implements  PostRepo.PostRepoListener, PostsListAdapter.PostListAdapterListener{
    private Home activity;
    private RecyclerView postsListView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private List<Post> postList;
    private PostRepo postRepo;
    private PostsListAdapter postsListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
    public String getTagText() {
        return "Posts";
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

        postRepo = new PostRepo(this);
        activity = (Home)getActivity();
        postList = new ArrayList<Post>();
        initUI(view);
        loadPosts();
    }

    public void initUI(View view) {
        postsListView = (RecyclerView) view.findViewById(R.id.post_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        layoutManager = new LinearLayoutManager(this.getActivity());
        postsListView.setLayoutManager(layoutManager);
        postsListView.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadPosts() {
        postRepo.getAllPosts("", new Date().toString(), new Date().toString()); //test arguments
    }

    @Override
    public void retryGetAllPosts(String pageNumber, String startDate, String endDate) {

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
    public void updatePosts(PostResponse postResponse) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void requestFailed() {

    }

}
