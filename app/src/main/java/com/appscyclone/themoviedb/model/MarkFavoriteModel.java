package com.appscyclone.themoviedb.model;


public class MarkFavoriteModel {
    private String media_type;
    private int media_id;
    private boolean favorite;

    public MarkFavoriteModel(String media_type, int media_id, boolean favorite) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.favorite = favorite;
    }
}
