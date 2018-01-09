package com.appscyclone.themoviedb.model;

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
}
