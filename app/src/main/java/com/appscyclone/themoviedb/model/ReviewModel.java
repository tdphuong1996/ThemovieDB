package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 01/03/2018.
 */

public class ReviewModel {

    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
     public String getTextAvatar(){
        String [] split =author.split("\\s");
        int size =split.length;
        String first = null,last=null;
        for(int i=0;i<size;i++){
             first=split[0].substring(0,1).toUpperCase();
             last=split[size-1].substring(0,1).toUpperCase();
        }
        return first+last;
     }

}


