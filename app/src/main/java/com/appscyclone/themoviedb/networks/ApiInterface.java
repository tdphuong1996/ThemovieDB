package com.appscyclone.themoviedb.networks;

import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by TDP on 26/12/2017.
 */

public interface ApiInterface {
    @GET("movie/{abc}/" + ConstantUtils.API_KEY)
    Call<JsonObject> getPopular(@Path("abc") String abc, @QueryMap Map<String, String> options);

    @GET("movie/{movie_id}" + ConstantUtils.API_KEY)
    Call<JsonObject> getMovieDetail(@Path("movie_id") int id, @QueryMap Map<String, String> options);

    @GET("movie/{movie_id}/videos" + ConstantUtils.API_KEY)
    Call<JsonObject> getVideo(@Path("movie_id") int id, @QueryMap Map<String, String> options);

    @GET("person/popular" + ConstantUtils.API_KEY)
    Call<JsonObject> getPeople(@QueryMap Map<String, String> options);

    @GET("person/{person_id}" + ConstantUtils.API_KEY)
    Call<JsonObject> getPeopleDetail(@Path("person_id") int id,@QueryMap Map<String, String> options);

    @GET("person/{person_id}/external_ids")
    Call<JsonObject> getExternalIDs(@Path("person_id") int id,@QueryMap Map<String, String> options);


}
