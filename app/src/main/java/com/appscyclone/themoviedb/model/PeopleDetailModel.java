package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by TDP on 11/01/2018.
 */

public class PeopleDetailModel implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("birthday")
    private String birthday;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private int gender;
    @SerializedName("biography")
    private String biography;
    @SerializedName("place_of_birth")
    private String placeOfBirth;
    @SerializedName("profile_path")
    private String profilePath;

    public String getProfilePath() {
        return profilePath;
    }

    public String getBirthday() {
        return birthday != null ? birthday : "";
    }

    public String getName() {
        return name != null ? name : "";
    }

    public int getGender() {
        return gender;
    }

    public String getBiography() {
        return biography != null ? biography : "";
    }

    public String getPlaceOfBirth() {
        return placeOfBirth != null ? placeOfBirth : "";
    }

    public String convertGender(int gender) {
        return gender == 1 ? "Female" : "Male";
    }

    public int getId() {
        return id;
    }
}