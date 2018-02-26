package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 21/02/2018.
 */

public class AccountModel {
    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private  String username;

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
}
