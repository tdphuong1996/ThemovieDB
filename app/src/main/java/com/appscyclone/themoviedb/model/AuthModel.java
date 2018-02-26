package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 18/01/2018.
 */

public class AuthModel {
    @SerializedName("request_token")
    private String requestToken;

    @SerializedName("success")
    private boolean success;
    @SerializedName("status_message")
    private String statusMessage;

    @SerializedName("session_id")
    private String sessionID;

    public String getStatusMessage() {
        return statusMessage!=null?statusMessage:"";
    }


    public String getRequestToken() {
        return requestToken!=null?requestToken:"";
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSessionID() {
        return sessionID;
    }
}
