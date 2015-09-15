package com.galleriafrique.model.comment;

/**
 * Created by osifo on 8/3/15.
 */
public class Comment {
    private String text;
    private String userImage;
    private String createdAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
