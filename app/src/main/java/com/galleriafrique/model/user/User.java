package com.galleriafrique.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */
public class User {
    private int id;
    public static String USER_DATA = "user_data";

    @SerializedName("fullname")
    private String name;

    private String email;

    private String mobile;

    private String auth_token;

    @SerializedName("profile_photo_url")
    private String profile_photo_url;

    @SerializedName("can_be_followed")
    private boolean canBeFollowed;

    private String locationCountry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return profile_photo_url;
    }

    public void setAvatar(String avatar) {
        this.profile_photo_url = profile_photo_url;
    }

    public boolean getFollowBackStatus() {
        return this.canBeFollowed;
    }

    public void setFollowBackStatus(boolean status) {
        this.canBeFollowed = status;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getLocationCountry() {
        return "NG";
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }
}
