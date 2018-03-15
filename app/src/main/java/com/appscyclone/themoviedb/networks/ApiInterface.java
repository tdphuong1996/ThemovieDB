package com.appscyclone.themoviedb.networks;

import com.appscyclone.themoviedb.model.MarkFavoriteModel;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


    @GET("person/{person_id}/external_ids")
    Call<JsonObject> getExternalIDs(@Path("person_id") int id,@QueryMap Map<String, String> options);

    @GET("authentication/token/new"+ConstantUtils.API_KEY)
    Call<JsonObject> getToken();

    @GET("authentication/token/validate_with_login"+ConstantUtils.API_KEY)
    Call<JsonObject> checkLogin(@QueryMap Map<String,String> options);

    @GET("authentication/session/new"+ConstantUtils.API_KEY)
    Call<JsonObject> getSession(@QueryMap Map<String,String> options);

    @GET("account"+ConstantUtils.API_KEY)
    Call<JsonObject> getAccount(@QueryMap Map<String,String> options);

    @POST("account/{account_id}/favorite"+ConstantUtils.API_KEY)
    Call<JsonObject> addFavorite(@Path("account_id") int id, @QueryMap Map<String,String> options, @Body MarkFavoriteModel model);

    @GET("movie/{movie_id}/account_states"+ConstantUtils.API_KEY)
    Call<JsonObject> getAccountSates(@Path("movie_id") int id,@QueryMap Map<String,String> options);

    @GET("authentication/guest_session/new"+ConstantUtils.API_KEY)
    Call<JsonObject> getGuestSessionId();

    @GET("account/{account_id}/favorite/movies"+ConstantUtils.API_KEY)
    Call<JsonObject> getFavorite(@Path("account_id") int id,@QueryMap Map<String,String> options );

    @GET("movie/{movie_id}/reviews"+ConstantUtils.API_KEY)
    Call<JsonObject> getReviews(@Path("movie_id") int idMovie);

    @GET("search/movie"+ConstantUtils.API_KEY)
    Call<JsonObject> getSearchMovie(@QueryMap Map<String,String> options);

    @GET("person/{person_id}/tagged_images"+ConstantUtils.API_KEY)
     Call<JsonObject> getTaggerImage(@Path("person_id") int id,@QueryMap Map<String, String> options);

    @GET("person/{person_id}/images"+ConstantUtils.API_KEY)
    Call<JsonObject> getImagePeople(@Path("person_id") int id);

    @GET("person/{person_id}/movie_credits"+ConstantUtils.API_KEY)
    Call<JsonObject> getPeopleMovieCredits(@Path("person_id") int id,@QueryMap Map<String, String> options);

    @GET("person/{person_id}" + ConstantUtils.API_KEY)
    Call<JsonObject> getPeopleDetail(@Path("person_id") int id,@QueryMap Map<String, String> options);

    @GET("/person/{person_id}/tagged_images"+ConstantUtils.API_KEY)
    Call<JsonObject> gettagImage(@Path("person_id") int id);


}
