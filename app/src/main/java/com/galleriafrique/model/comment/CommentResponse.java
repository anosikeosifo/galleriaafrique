package com.galleriafrique.model.comment;

import com.galleriafrique.model.GeneralStatusResponse;

import java.util.List;

/**
 * Created by osifo on 8/3/15.
 */
public class CommentResponse extends GeneralStatusResponse {
    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }
}
