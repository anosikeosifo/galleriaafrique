package com.galleriafrique.model.post;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */
public class Post {
    //Post(id: integer, image_url: string, description: string, user_id: integer, created_at: datetime, updated_at: datetime, removed: boolean, image: string)

    public int id;
    public String username;
    public String description;

    @SerializedName("image_url")
    public String image;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String userAvatar;
    public int commentCount;
    public int likeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}

