package com.galleriafrique.model.user;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class UserResponse {
    private boolean success;
    private String message;
    private List<User> data;

    public boolean isSuccess() { return success; }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
