package com.galleriafrique.model.comment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */
public class Comment {
    private String text;

    @SerializedName("image_url")
    private String userImage;

    @SerializedName("create_at")
    private String createdAt;

    private String username;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }
}
