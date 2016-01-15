package com.galleriafrique.model.comment;

import com.galleriafrique.model.user.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */
public class Comment {
    private String text;

    private String id;

    @SerializedName("image_url")
    private String userAvatar;

    @SerializedName("create_at")
    private String createdAt;

    private String username;

    @SerializedName("user")
    private User user;

    public String getText() {
        return text;
    }

    public int getId() {
        return  Integer.valueOf(id);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
