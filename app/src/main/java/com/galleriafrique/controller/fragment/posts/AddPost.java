package com.galleriafrique.controller.fragment.posts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.Home;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.view.adapters.CommentsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 9/23/15.
 */

public class AddPost extends BaseFragment {

    private Home activity;
    private ImageView postImage;
    private EditText postDescription;

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
        activity = (Home) getActivity();

        initUI(view);
    }

    private void initUI(View view){
        postImage = (ImageView)view.findViewById(R.id.img_for_upload);
        postDescription = (EditText)view.findViewById(R.id.post_description);

        setContent();
    }

    private void setContent() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String imageUrl = bundle.getString("galleryImage");
            if (imageUrl != null) {
                Glide.with(this).load(imageUrl).fitCenter().error(R.drawable.placeholder_photo).into(postImage);
            }
        }
    }
}
