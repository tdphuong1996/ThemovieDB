package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 13/01/2018.
 */

public class ExternallIDModel {

    @SerializedName("twitter_id")
    private String twitter;

    @SerializedName("facebook_id")
    private String facebook;

    @SerializedName("instagram_id")
    private String instagram;

    public String getTwitter() {
        return twitter!=null?twitter:"";
    }

    public String getFacebook() {
        return facebook!=null?facebook:"";
    }

    public String getInstagram() {
        return instagram!=null?instagram:"";
    }
}
