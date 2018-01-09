package com.appscyclone.themoviedb.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
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
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

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
    @BindView(R.id.fragMovieDetail_tvMark)
    TextView tvMask;
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
    @BindView(R.id.fragDetail_tbDetail)
    Toolbar tbDetail;

    private int mMovieID;
    private String urlYoutube;

   private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(tbDetail);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.view_custom_action_bar, null);
        actionBar.setCustomView(view);
        TextView tvTitle = view.findViewById(R.id.viewCT_tvTitle);
        ImageView ivBack = view.findViewById(R.id.viewCT_ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).setHideBottomBar(false);
            }
        });
        tvTitle.setText("DETAIL");

        Bundle bundle = getArguments();
        mMovieID = bundle.getInt("id");
        loadMovieDetail(mMovieID);
        loadVideo(mMovieID);
    }




    private void loadMovieDetail(int idMovie) {
        Map map = new HashMap();
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
        Map map = new HashMap();
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

    @OnClick(R.id.fragMovie_ivPlay)
    public void onClick(View view) {
        if (view.getId() == R.id.fragMovie_ivPlay) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstantUtils.BASE_URL_YT + urlYoutube)));
            Log.i("Video", "Video Playing....");
        }
    }




}
