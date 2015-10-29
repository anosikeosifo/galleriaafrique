package com.galleriafrique.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.post.FavoriteResponse;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.tools.CircleTransform;
import com.galleriafrique.util.tools.Strings;
import com.galleriafrique.view.holders.PostHolder;
import com.bumptech.glide.Glide;
import com.galleriafrique.provider.AndroidDatabaseManager;

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
        setTags(postHolder, position);
        setListeners(postHolder, post);
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        PostHolder postHolder = new PostHolder(view);
        return postHolder;
    }

    private void setContent(PostHolder postHolder, Post post) {
        postHolder.user.setText(post.getUser().getName());
        postHolder.description.setText(post.getDescription());
        postHolder.createdAt.setText(post.getCreatedTime());

        Glide.with(context).load(post.getImage()).fitCenter().error(R.drawable.placeholder_photo)
                .placeholder(R.drawable.placeholder_photo).crossFade().into(postHolder.photo);

        Glide.with(context).load(post.getUser().getAvatar()).centerCrop().placeholder(R.drawable.ic_avatar).transform(new CircleTransform(context)).into(postHolder.userAvatar);

        setContentForActionUI(postHolder, post);
    }

    public void setContentForActionUI(PostHolder holder, Post post) {
        //favorite action ui
        if(post.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_true);
            holder.favoriteCount.setText(Integer.toString(post.getFavoriteCount()));
            holder.favoriteButton.setEnabled(false);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
        }

        if (post.getFavoriteCount() > 0) {
            holder.favoriteCount.setText(String.valueOf(post.getFavoriteCount()));
        }
    }

    private void setListeners(final PostHolder holder, final Post post) {
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapterListener.showPostDetails(post);
            }
        });

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapterListener.showPostDetails(post);
            }
        });

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapterListener.favoritePost(post, (int) view.getTag());
                updateFavoriteUIBeforeAPICall(holder);
            }
        });
    }

    private void setTags(PostHolder postHolder, int position) {
        postHolder.favoriteButton.setTag(position);
    }

    public void updateFavoriteUIBeforeAPICall(PostHolder holder) {
        updateFavoriteCount(holder);
        holder.favoriteButton.setEnabled(false);
        holder.favoriteButton.setImageResource(R.drawable.ic_favorite_true);
    }

    public void updateFavoriteUIAfterAPICall(PostHolder holder, Post post) {
        if(post.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_true);
            holder.favoriteCount.setText(post.getFavoriteCount());
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
        }

    }

    private void updateFavoriteCount(PostHolder holder) {
        if (Strings.isTextEmpty(holder.favoriteCount)) {
            holder.favoriteCount.setText("1");
        } else {
            holder.favoriteCount.setText(Integer.toString(Integer.parseInt(holder.favoriteCount.getText().toString()) + 1));
        }
    }

    public void updateFavorite(FavoriteResponse.Favorite favorite, int position) {
        Post post = postList.get(position);
        post.setFavoriteCount(favorite.getCount());
        post.setIsFavorite(favorite.isFavorite());
        notifyDataSetChanged();
        //updateFavoriteUIAfterAPICall(post)
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
        void favoritePost(Post post, int position);
        void sharePost(HomeActivity activity, Post post);
        void addPostComment();
    }
}
