package com.galleriafrique.model.post;

import com.galleriafrique.Constants;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.model.user.User;
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
    private int commentCount;
    private int likeCount;

    @SerializedName("user")
    public User user;

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
        return location;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedTime() {

        String time = "now";

        try {

            final Date createdDate = Constants.DATE_FORMAT.parse(getCreatedAt());
            final Date currentDate = new Date();

            // in milliseconds
            long diff = currentDate.getTime() - createdDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            long diffWeeks = diff / (7 * 24 * 60 * 60 * 1000);

            if (diffWeeks > 0) {
                return diffWeeks + "w";
            } else if (diffDays > 0) {
                return diffDays + "d";
            } else if (diffHours > 0) {
                return diffHours + "h";
            } else if (diffMinutes > 0) {
                return diffMinutes + "m";
            } else if (diffSeconds > 0) {
                return diffSeconds + "s";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }
}

