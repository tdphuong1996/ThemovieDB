package com.appscyclone.themoviedb.networks;

public class ApiUtils {
     private static final String BASE_URL="https://api.themoviedb.org/3/";
    public static ApiInterface getSOService(){
        return  RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
