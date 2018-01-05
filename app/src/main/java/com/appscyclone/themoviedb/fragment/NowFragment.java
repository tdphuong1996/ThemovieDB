package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.adapter.MovieAdapter;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowFragment extends Fragment {
    @BindView(R.id.fragNow_rvListMovieNow)
    RecyclerView rvListMovie;

    private MovieAdapter mMovieAdapter;
    private List<ItemMovieModel> mMovieList;
    private boolean isLoading=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init() {

        mMovieList=new ArrayList<>();
        mMovieAdapter =new MovieAdapter(mMovieList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvListMovie.setLayoutManager(layoutManager);
        rvListMovie.setAdapter(mMovieAdapter);
        if(isLoading){
            loadMovie(1);
        }else {
            mMovieAdapter.notifyDataSetChanged();
        }
    }

    private void loadMovie(int page){
        Map<String,String> mapMovie=new HashMap<>();
        mapMovie.put(ConstantUtils.PAGE,String.valueOf(page));
        mapMovie.put(ConstantUtils.LANGUAGE,ConstantUtils.EN_US);
        ApiInterface apiInterface= ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.getPopular("now_playing",mapMovie);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ItemMovieModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ItemMovieModel>>() {
                        }.getType());
                mMovieList.addAll(list);
                mMovieAdapter.notifyDataSetChanged();
                isLoading=false;

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
