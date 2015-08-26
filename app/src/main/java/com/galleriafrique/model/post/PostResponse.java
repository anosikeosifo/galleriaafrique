package com.galleriafrique.model.post;

import com.galleriafrique.model.GeneralStatusResponse;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class PostResponse extends GeneralStatusResponse {
    private List<Post> data;

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }
}
