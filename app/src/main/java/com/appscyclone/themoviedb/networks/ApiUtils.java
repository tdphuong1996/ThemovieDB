package com.appscyclone.themoviedb.networks;

/**
 * Created by TDP on 26/12/2017.
 */

public class ApiUtils {
    public static final String BASE_URL="https://api.themoviedb.org/3/";
    public static ApiInterface getSOService(){
        return  RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
