package com.galleriafrique.model.post;

import com.galleriafrique.Constants;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.tools.Strings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class Post {

    public static String POST_DATA = "post_data";
    private int id;
    private String username;
    private String description;
    private String location;

    @SerializedName("image_url")
    public String image;

    @SerializedName("created_at")
    public String createdAt;

    public String userAvatar;

    @SerializedName("comments_count")
    private int commentCount;

    @SerializedName("favorites_count")
    private int favoriteCount;

    @SerializedName("is_favorite")
    private boolean isFavorite;

    @SerializedName("user")
    public User user;

    @SerializedName("can_repost")
    private boolean canRepost;

    @SerializedName("repost_id")
    private String repostID;

    @SerializedName("origin_post")
    private Post origin_post;

    public List<Comment> comments;

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

    public String getLocation() {
        return "Lagos, Nigeria.";
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean canRepost() {
        return canRepost;
    }

    public void setCanRepost(boolean canRepost) {
        this.canRepost = canRepost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isRepost() {
        return this.getOriginPost() != null;
    }

    public Post getOriginPost() {
        return origin_post;
    }

    public void setOriginPost(Post origin_post) {
        this.origin_post = origin_post;
    }
}

