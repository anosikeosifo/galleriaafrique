package com.galleriafrique.controller.fragment.posts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.FavoriteResponse;
import com.galleriafrique.model.post.LikeResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.helpers.ProgressDialogHelper;
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.util.tools.Strings;

import java.util.List;

/**
 * Created by osifo on 9/23/15.
 */

public class AddPost extends BaseFragment implements  PostRepo.PostRepoListener {

    private HomeActivity activity;

    private PostRepo postRepo;

    private ImageView postImageView;

    private EditText postDescriptionTxt;

    private ImageButton sendButton;

    private String postDescription;

    private String postImage;

    private User currentUser;;

    private ProgressDialogHelper progressDialogHelper;


    public static AddPost newInstanceFromGallery(String imageUrl) {
        AddPost fragment = new AddPost();
        Bundle bundle = new Bundle();
        bundle.putString("galleryImage", imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddPost(){

    }

    @Override
    public String getTitleText() {
        return "Add post";
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.add_post, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (HomeActivity) getActivity();
        postRepo = new PostRepo(this);
        initUI(view);
    }

    private void initUI(View view){
        postImageView = (ImageView)view.findViewById(R.id.img_for_upload);
        postDescriptionTxt = (EditText)view.findViewById(R.id.post_description);
        sendButton = (ImageButton)view.findViewById(R.id.share_post);
        //currentUser = AccountManager.getUser();
        setContent();
        setListeners();
    }

    private void setContent() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            postImage = bundle.getString("galleryImage");
            if (postImage != null) {
                Glide.with(this).load(postImage).fitCenter().error(R.drawable.placeholder_photo).into(postImageView);
            }
        }
    }

    private void setListeners() {
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (formValidated()) {
                    postRepo.createPost(postDescription, postImage, CommonUtils.getSafeString(12));
                    activity.getSupportFragmentManager().popBackStack();
                    activity.getFragmentSwitcher().showPostFeed();
                }
            }
        });
    }

    private boolean formValidated() {
        if(Strings.isTextEmpty(postDescriptionTxt)) {
            CommonUtils.toast(activity, "Please provide a description");
        } else {
            postDescription = Strings.getStringFromView(postDescriptionTxt);
            return true;
        }

        return false;
    }

    @Override
    public void retryCreatePost(String description, String image, String user_id) {
        postRepo.createPost(description, image, user_id);
    }

    @Override
    public void createPostSuccessful(Post post) {
        //TODO: Change this to a snackbar.
        CommonUtils.toast(activity, "Post created successfully.");
    }

    @Override
    public void retryGetAllPosts() {

    }

    @Override
    public void retryFetchUserFeed(String userID) {

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
    public void updateFavorite(FavoriteResponse.Favorite like, int position) {

    }


    @Override
    public void updatePosts(List<Post> posts) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void requestFailed() {

    }
}
