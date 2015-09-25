package com.galleriafrique.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.util.tools.CircleTransform;
import com.galleriafrique.view.holders.CommentHolder;
import com.galleriafrique.view.holders.PostHolder;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class CommentsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Comment> commentList;
    private CommentHolder commentHolder;

    public CommentsListAdapter(BaseFragment fragment, List<Comment> commentList) {
        this.context = fragment.getActivity();
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.commentList = commentList;

    }


    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return commentList == null ? 0 : commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Comment comment = this.commentList.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.post_comments_list_item, parent, false);
            commentHolder = new CommentHolder(view);
            setContent(commentHolder, comment);
        } else {
            commentHolder = (CommentHolder)view.getTag();
        }

        return view;
    }

    private void setContent(CommentHolder commentHolder, Comment comment) {
        commentHolder.commentText.setText(comment.getText());
        commentHolder.timeStamp.setText(comment.getCreatedAt());
        Glide.with(context).load(comment.getUserImage()).centerCrop().placeholder(R.drawable.ic_avatar).transform(new CircleTransform(context)).into(commentHolder.userAvatar);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
