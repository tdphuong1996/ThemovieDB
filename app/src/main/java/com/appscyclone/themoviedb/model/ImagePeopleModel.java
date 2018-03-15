package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 09/03/2018.
 */

public class ImagePeopleModel {
    @SerializedName("file_path")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }
}
