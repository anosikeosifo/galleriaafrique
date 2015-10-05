package com.galleriafrique.controller.fragment.posts;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.FragmentSwitcher;
import com.galleriafrique.controller.activity.base.Home;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.post.PostResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.view.adapters.PostsListAdapter;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import org.w3c.dom.Text;

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
    private TextView dotSeparator;
    private FloatingActionButton addStaffFAB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Posts() {

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

        addStaffFAB = (FloatingActionButton)view.findViewById(R.id.new_post);
        addStaffFAB.attachToRecyclerView(postsListView);

        addStaffFAB.attachToRecyclerView(postsListView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                addStaffFAB.show();
            }

            @Override
            public void onScrollUp() {
                addStaffFAB.hide();
            }
        });
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadPosts();
            }
        });

        addStaffFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.getFragmentSwitcher().showImageGallery();
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
        postRepo.getAllPosts();
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
            Log.d("POST", post.getUser().toString());
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
        activity.getFragmentSwitcher().showPostDetails(post);
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

    @Override
    public void retryCreatePost(String description, String image, String user_id) {

    }

    @Override
    public void createPostSuccessful(Post post) {

    }
}
