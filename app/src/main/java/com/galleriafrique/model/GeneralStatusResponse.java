package com.galleriafrique.model;

import com.galleriafrique.util.CommonUtils;

/**
 * Created by osifo on 8/21/15.
 */
public class GeneralStatusResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
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
}
