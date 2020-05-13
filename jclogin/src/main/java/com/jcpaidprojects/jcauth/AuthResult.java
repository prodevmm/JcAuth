package com.jcpaidprojects.jcauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResult {
    @SerializedName("loginAuthorized")
    @Expose
    private Boolean loginAuthorized;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

    public Boolean getLoginAuthorized() {
        return loginAuthorized;
    }

    public void setLoginAuthorized(Boolean loginAuthorized) {
        this.loginAuthorized = loginAuthorized;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
