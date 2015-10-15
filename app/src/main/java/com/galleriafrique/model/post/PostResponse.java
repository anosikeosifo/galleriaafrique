package com.galleriafrique.model.post;

import com.galleriafrique.model.GeneralStatusResponse;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class PostResponse {
    private List<Post> data;
    private boolean success;
    private String message;

    public boolean isSuccess() {

        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return CommonUtils.getSafeString(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Post> getData() {
        return data;
    }

}
