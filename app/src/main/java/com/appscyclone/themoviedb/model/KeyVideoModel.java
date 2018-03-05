package com.appscyclone.themoviedb.model;

import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 08/01/2018.
 */

public class KeyVideoModel {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public  String getThumbnailUrl(){
        return String.format(ConstantUtils.YOUTUBE_THUMBNAIL_URL,key);
    }

    public String getUrl(){
        return  String.format(ConstantUtils.YOUTUBE_VIDEO_URL,key);
    }
}
