package com.galleriafrique.view.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.galleriafrique.R;

/**
 * Created by osifo on 8/3/15.
 */
public class CommentHolder {
    public ImageView userAvatar;
    public TextView commentText;
    public TextView timeStamp;
    public TextView userName;

    public CommentHolder(View view) {
        //userName = (TextView)view.findViewById(R.id.comment_user_name);
        userAvatar = (ImageView)view.findViewById(R.id.comment_user_avatar);
        commentText = (TextView)view.findViewById(R.id.comment_text);
        timeStamp = (TextView)view.findViewById(R.id.comment_time_stamp);
    }
}
