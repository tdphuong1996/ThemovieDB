package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 20/03/2018.
 */

public class MessageModel {
    @SerializedName("status_message")
    private String sttMessage;

    public String getSttMessage() {
        return sttMessage!=null?sttMessage:"";
    }
}
