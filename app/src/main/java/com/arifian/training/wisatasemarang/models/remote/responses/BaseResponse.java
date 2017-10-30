package com.arifian.training.wisatasemarang.models.remote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faqih on 30/10/17.
 */

public class BaseResponse {
    @SerializedName("message")
    @Expose
    protected String message;
    @SerializedName("success")
    @Expose
    protected Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
