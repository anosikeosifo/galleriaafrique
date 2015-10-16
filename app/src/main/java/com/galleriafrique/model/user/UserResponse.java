package com.galleriafrique.model.user;

import com.galleriafrique.model.GeneralStatusResponse;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class UserResponse extends GeneralStatusResponse {
    private List<User> data;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
