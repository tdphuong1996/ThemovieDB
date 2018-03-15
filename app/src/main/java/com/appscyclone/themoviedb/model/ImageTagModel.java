package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 14/03/2018.
 */

public class ImageTagModel {
    @SerializedName("file_path")
    @Expose
    private String filePath;

    public String getFilePath() {
        return filePath;
    }
}
