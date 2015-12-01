package com.galleriafrique.model.comment;

import com.galleriafrique.model.user.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */
public class Comment {
    private String text;

    @SerializedName("image_url")
    private String userAvatar;

    @SerializedName("create_at")
    private String createdAt;

    private String username;

    private User user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }
}
