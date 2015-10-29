package com.galleriafrique.controller.fragment.posts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.view.adapters.CommentsListAdapter;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 9/15/15.
 */
public class PostDetails  extends BaseFragment{
    private ImageButton sharePost;
    private ImageButton addComment;
    private ImageButton postComment;
    private ImageButton favoritePost;
    private ImageView postImage;
    private ImageView postUserAvatar;
    private ListView commentsListView;

    private View postDetailsHeader;
    private View postComments;
    public CommentsListAdapter commentsListAdapter;
    private List<Comment> commentList;

    private Post post;

    private HomeActivity activity;

    public static PostDetails newInstance(Post post) {
        PostDetails fragment = new PostDetails();

        Bundle bundle = new Bundle();
        bundle.putString(Post.POST_DATA, CommonUtils.getGson().toJson(post).toString());
        fragment.setArguments(bundle);
        return  fragment;
    }

    public PostDetails() {

    }

    @Override
    public String getTitleText() {
        return "View post";
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        postComments =  inflater.inflate( R.layout.posts_comments_list, container, false);
        postDetailsHeader =  inflater.inflate(R.layout.post_detail_header, (ListView)postComments.findViewById(R.id.comment_list), false);
        return postComments;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (HomeActivity) getActivity();
        this.commentList = new ArrayList<Comment>();
        setUIContent(view);
    }




    private void setUIContent(View view) {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String postData = bundle.getString(Post.POST_DATA);
            if (postData != null) {
                post = CommonUtils.getGson().fromJson(postData, new TypeToken<Post>(){}.getType());
                if (post != null) {
                    loadPostComments(post.getComments());
                    initUI(view);
                    setClickListeners();
                }
            }
        }
    }

    private void initUI(View view) {
        commentsListView = (ListView)postComments.findViewById(R.id.comment_list);

        sharePost = (ImageButton)postDetailsHeader.findViewById(R.id.share_post);
        favoritePost = (ImageButton)postDetailsHeader.findViewById(R.id.favorite_post);

        ((TextView)postDetailsHeader.findViewById(R.id.post_username)).setText(post.getUser().getName());
        ((TextView)postDetailsHeader.findViewById(R.id.post_description)).setText(post.getDescription());
        ((TextView)postDetailsHeader.findViewById(R.id.post_location)).setText(post.getLocation());
        ((TextView)postDetailsHeader.findViewById(R.id.post_created_at)).setText(post.getCreatedTime());

        postUserAvatar = (ImageView)postDetailsHeader.findViewById(R.id.post_user_avatar);
        postImage = (ImageView)postDetailsHeader.findViewById(R.id.post_photo);

        if(post.getUserAvatar()!= null) {
            Glide.with(activity).load(post.getUser().getAvatar()).fitCenter().error(R.drawable.ic_avatar)
                    .placeholder(R.drawable.ic_avatar).into(postUserAvatar);
        }

        if(post.getImage() != null) {
            Glide.with(activity).load(post.getImage()).fitCenter().error(R.drawable.placeholder_photo)
                    .placeholder(R.drawable.placeholder_photo).into(postImage);
        }

        commentsListView.addHeaderView(postDetailsHeader);
        commentsListView.setAdapter(commentsListAdapter);
    }


    private void setClickListeners() {
        sharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.toast(activity, "sharepost clicked");
            }
        });

        favoritePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.toast(activity, "favoritePost clicked");
            }
        });

        postUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.toast(activity, "would load user profile view");
            }
        });

    }


    private void showPostComments(){
        if (commentsListAdapter == null) {
            commentsListAdapter = new CommentsListAdapter(this, commentList);
        } else {
            commentsListAdapter.notifyDataSetChanged();
        }
    }

    private void loadPostComments(List<Comment> data) {
        for(Comment comment : data) {
            if(!commentList.contains(comment)) {
                commentList.add(comment);
            }
        }

        showPostComments();
    }

}
