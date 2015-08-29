package com.galleriafrique.view.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galleriafrique.R;

/**
 * Created by osifo on 8/3/15.
 */
public class PostHolder extends RecyclerView.ViewHolder {
    public TextView user;
    public  TextView title;
    public TextView description;
    public TextView created_at;
    public ImageView photo;
    public ImageView user_avatar;
    public CardView card;

    public PostHolder(View cardView) {

        super(cardView);

        this.user = (TextView) cardView.findViewById(R.id.post_username);

        this.title = (TextView) cardView.findViewById(R.id.post_title);

        this.description = (TextView) cardView.findViewById(R.id.post_title);

        this.created_at = (TextView) cardView.findViewById(R.id.post_created_at);

        this.photo = (ImageView) cardView.findViewById(R.id.post_photo);

        this.user_avatar = (ImageView) cardView.findViewById(R.id.post_user_avatar);

        this.card = (CardView)cardView.findViewById(R.id.list_item);
    }
}
