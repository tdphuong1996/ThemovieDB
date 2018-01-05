package com.appscyclone.themoviedb.networks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by TDP on 26/12/2017.
 */

public class RetrofitClient {
    private static Retrofit mRetrofit = null;


    public static Retrofit getClient(String baseUrl) {

        OkHttpClient client;

        OkHttpClient.Builder okhttpcliient = new OkHttpClient.Builder();
        okhttpcliient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        client = okhttpcliient.build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}
