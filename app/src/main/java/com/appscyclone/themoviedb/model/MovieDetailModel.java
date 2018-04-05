package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TDP on 06/01/2018.
 */

public class MovieDetailModel {
    @SerializedName("budget")
    private int budget;
    @SerializedName("genres")
    private List<Genre> genres = null;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overview;
    @SerializedName("revenue")
    private int revenue;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("title")
    private String title;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName( "release_date")
    private String releaseDate;

    public String getBackDropPath() {
        return backDropPath;
    }




    public List<Genre> getGenres() {
        return genres;
    }




    public String getOverview() {
        return overview;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public static class Genre {

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
