package com.galleriafrique.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.util.tools.CircleTransform;
import com.galleriafrique.view.holders.PostHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Post> postList;
    public PostListAdapterListener postListAdapterListener;

    public PostsListAdapter(BaseFragment fragment, List<Post> postList) {
        this.context = fragment.getActivity();
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postListAdapterListener = (PostListAdapterListener)fragment;
        this.postList = postList;
    }

    @Override
    public void onBindViewHolder(PostHolder postHolder, int position) {
        final Post post = postList.get(position);
        setContent(postHolder, post);
        setListeners(postHolder, post);
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        PostHolder postHolder = new PostHolder(view);
        return postHolder;
    }

    private void setContent(PostHolder postHolder, Post post) {
        postHolder.user.setText(post.getUsername());
        postHolder.description.setText(post.getDescription());
        postHolder.createdAt.setText(post.createdAt);

        Glide.with(context).load(post.getImage()).fitCenter().error(R.drawable.placeholder_photo)
                .placeholder(R.drawable.placeholder_photo).crossFade().into(postHolder.photo);

        Glide.with(context).load(post.getUserAvatar()).centerCrop().placeholder(R.drawable.ic_avatar).transform(new CircleTransform(context)).into(postHolder.userAvatar);
    }

    private void setListeners(PostHolder holder, final Post post) {
        holder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                postListAdapterListener.showPostDetails(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public interface PostListAdapterListener  {
        void showPostDetails(Post post);
        void showUserProfile();
        void likePost();
        void sharePost();
        void addPostComment();
    }
}
