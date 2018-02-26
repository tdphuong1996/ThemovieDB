package com.appscyclone.themoviedb.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.activity.PlayVideoActivity;
import com.appscyclone.themoviedb.model.KeyVideoModel;
import com.appscyclone.themoviedb.model.MovieDetailModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.appscyclone.themoviedb.utils.ConvertNumber;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailFragment extends Fragment {
    @BindView(R.id.fragMovieDetail_tvBuget)
    TextView tvBuget;
    @BindView(R.id.fragMovieDetail_tvGenres)
    TextView tvGenres;
    @BindView(R.id.fragMovieDetail_tvName)
    TextView tvName;
    @BindView(R.id.fragMovieDetail_tvOverview)
    TextView tvOverview;
    @BindView(R.id.fragMovieDetail_tvRate)
    TextView tvRate;
    @BindView(R.id.fragMovieDetail_tvRevenue)
    TextView tvRevenue;
    @BindView(R.id.fragMovieDetail_tvRuntime)
    TextView tvRuntime;
    @BindView(R.id.fragDetail_imgPoster)
    ImageView imgPoster;
    @BindView(R.id.fragMovieDetail_tvMark) TextView tvMark;

    private int mMovieID;
    private String urlYoutube;
    private boolean mFavorite;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    @OnClick({R.id.fragMovie_ivPlay,R.id.viewCT_ivBack})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragMovie_ivPlay:
                Intent intent=new Intent(getActivity(),PlayVideoActivity.class);
                intent.putExtra("yt",urlYoutube);
                startActivity(intent);
                Log.i("Video", "Video Playing....");
                break;
            case R.id.viewCT_ivBack:
                getActivity().getSupportFragmentManager().popBackStack();
                ((MainActivity) getContext()).setHideBottomBar(true);
                break;
            case R.id.fragMovieDetail_tvMark:

        }
    }

    private void init() {
        Bundle bundle = getArguments();
        mMovieID = bundle.getInt("id");
        mSharedPreferences= getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        loadMovieDetail(mMovieID);
        String sessionID=mSharedPreferences.getString(ConstantUtils.SESSION_ID,"");
        String guestSessionID=mSharedPreferences.getString(ConstantUtils.GUEST_SESSION_ID,"");
        getAccountState(sessionID,guestSessionID);
        loadVideo(mMovieID);

    }

    private void loadMovieDetail(int idMovie) {
        Map<String,String> map =new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getMovieDetail(idMovie, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                MovieDetailModel model = new Gson().fromJson(response.body(), MovieDetailModel.class);
                tvBuget.setText(ConvertNumber.Convert(model.getBudget()));
                StringBuilder genres = new StringBuilder();
                for (int i = 0; i < model.getGenres().size(); i++) {
                    genres.append(model.getGenres().get(i).getName()).append(", ");
                }
                tvRate.setText(String.valueOf(model.getVoteAverage()));
                tvGenres.setText(genres);
                tvName.setText(model.getTitle());
                tvOverview.setText(model.getOverview());
                tvRevenue.setText(ConvertNumber.Convert(model.getRevenue()));
                tvRuntime.setText(String.valueOf(model.getRuntime()));
                Picasso.with(getContext()).load(ConstantUtils.IMAGE_URL + model.getBackDropPath()).into(imgPoster);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void loadVideo(final int idMovie) {
        Map<String,String> map =new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getVideo(idMovie, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<KeyVideoModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<KeyVideoModel>>() {
                        }.getType());
                for (int i = 0; i < list.size(); i++) {
                    urlYoutube = list.get(i).getKey();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void markFavorite(){
        Map<String,String> mapFavorite =new HashMap<>();
        mapFavorite.put(ConstantUtils.SESSION_ID,mSharedPreferences.getString(ConstantUtils.SESSION_ID,""));
        Map<String,String> mapBody=new HashMap<>();
        mapBody.put(ConstantUtils.MEDIA_TYPE,ConstantUtils.MOVIE);
        mapBody.put(ConstantUtils.MEDIA_ID,"ddd");
        //mapBody.put(ConstantUtils.FAVORITE,)
    }

    private void getAccountState(String sessionID,String guestSessionID){
        Map<String,String> mapAccountSate=new HashMap<>();
        mapAccountSate.put(ConstantUtils.SESSION_ID,sessionID);
        mapAccountSate.put(ConstantUtils.GUEST_SESSION_ID,guestSessionID);

        ApiInterface apiInterface=ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.getAccountSates(mMovieID,mapAccountSate);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                 mFavorite=response.body().getAsJsonObject().get(ConstantUtils.FAVORITE).getAsBoolean();
                 tvMark.setText(mFavorite?getString(R.string.remove_form_favorite):getString(R.string.mark_as_favorite));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}
