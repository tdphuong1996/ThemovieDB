package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 09/01/2018.
 */

public class PeopleModel {
    @SerializedName("id")
    private int id;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }
}
