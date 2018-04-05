package com.appscyclone.themoviedb.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.ReviewsAdapter;
import com.appscyclone.themoviedb.model.KeyVideoModel;
import com.appscyclone.themoviedb.model.MarkFavoriteModel;
import com.appscyclone.themoviedb.model.MessageModel;
import com.appscyclone.themoviedb.model.MovieDetailModel;
import com.appscyclone.themoviedb.model.ReviewModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.LoadingDialog;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailFragment extends Fragment implements RatingBar.OnRatingBarChangeListener{

    @BindView(R.id.fragMovieDetails_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.fragMovieDetails_tvName)
    TextView tvName;
    @BindView(R.id.fragMovieDetails_tvYear)
    TextView tvYear;
    @BindView(R.id.fragMovieDetails_tvRating)
    TextView tvRating;
    @BindView(R.id.fragMovieDetail_tvDescription)
    TextView tvDescription;
    @BindView(R.id.trailers)
    LinearLayout llTrailer;
    @BindView(R.id.trailers_container)
    HorizontalScrollView hlTrailerContent;
    @BindView(R.id.reviews_label)
    TextView tvReview;
    @BindView(R.id.trailers_label)
    TextView llReviewsContainer;
    @BindView(R.id.toolbar)
    @Nullable
    Toolbar toolbar;
    @BindView(R.id.fragMovieDetails_ivPoster)
    ImageView ivPoster;
    @BindView(R.id.fragMovieDetails_rvListReviews)
    RecyclerView rvListReviews;
    @BindView(R.id.fragMovieDetail_fabFavorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.fragMovieDetail_rtRate)
    RatingBar rtRate;

    private int mMovieID;
    private String urlYoutube, mSessionID, mGuestSessionID;
    private boolean mFavorite;
    private SharedPreferences mSharedPreferences;
    private LoadingDialog mLoadingDialog;

    private ReviewsAdapter mReviewsAdapter;
    private List<ReviewModel> mReviewList;

    public static MovieDetailFragment newInstance(int idMovie) {

        Bundle args = new Bundle();
        args.putInt("id",idMovie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick({R.id.fragMovieDetail_fabFavorite})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fragMovieDetail_fabFavorite:
                    markFavorite();
                    break;
                default:
                    break;
            }
    }

    private void init() {
        setToolbar();
        Bundle bundle = getArguments();
        mMovieID = bundle.getInt("id");
        mSharedPreferences = getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        loadMovieDetail(mMovieID);
        mSessionID = mSharedPreferences.getString(ConstantUtils.SESSION_ID, "");
        mGuestSessionID = mSharedPreferences.getString(ConstantUtils.GUEST_SESSION_ID, "");
        getAccountState(mSessionID, mGuestSessionID);
        loadVideo(mMovieID);
        mLoadingDialog = new LoadingDialog(getContext());
        mReviewList = new ArrayList<>();
        mReviewsAdapter = new ReviewsAdapter(mReviewList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvListReviews.setLayoutManager(layoutManager);
        rvListReviews.setAdapter(mReviewsAdapter);
        loadReview();
        mLoadingDialog.show();

        rtRate.setOnRatingBarChangeListener(this);

    }

    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle(getString(R.string.movie_details));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                    ((MainActivity) getContext()).setHideBottomBar(true);
            }
        });
    }

    private void loadMovieDetail(int idMovie) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getMovieDetail(idMovie, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                MovieDetailModel model = new Gson().fromJson(response.body(), MovieDetailModel.class);

                tvRating.setText(String.format(getString(R.string.rating), String.valueOf(model.getVoteAverage())));
                tvYear.setText(String.format(getString(R.string.release_date), model.getReleaseDate()));
                tvName.setText(model.getTitle());
                tvDescription.setText(model.getOverview());
                Glide.with(getContext()).load(ConstantUtils.IMAGE_URL + model.getBackDropPath()).into(ivPoster);
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void loadVideo(final int idMovie) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getVideo(idMovie, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<KeyVideoModel> videoModels = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<KeyVideoModel>>() {
                        }.getType());
                //bug
                LayoutInflater inflater = getActivity().getLayoutInflater();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.color.colorPrimary)
                        .centerCrop()
                        .override(150, 150);

                int size = videoModels.size();
                for (int i = 0; i < size; i++) {
                    View thumbContainer = inflater.inflate(R.layout.view_video, llTrailer, false);
                    ImageView thumbView = ButterKnife.findById(thumbContainer, R.id.video_thumb);
                    thumbView.setTag(R.id.glide_tag, videoModels.get(i).getUrl());
                    thumbView.requestLayout();
                    thumbView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onThumbnailClick(view);
                        }
                    });
                    Glide.with(getContext())
                            .load(videoModels.get(i).getThumbnailUrl())
                            .apply(options)
                            .into(thumbView);
                    llTrailer.addView(thumbContainer);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void loadReview() {
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getReviews(mMovieID);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ReviewModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ReviewModel>>() {
                        }.getType());
                mReviewList.addAll(list);
                mReviewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void markFavorite() {
        final Map<String, String> mapFavorite = new HashMap<>();
        mapFavorite.put(ConstantUtils.SESSION_ID, mSharedPreferences.getString(ConstantUtils.SESSION_ID, ""));
        MarkFavoriteModel markFavoriteModel = new MarkFavoriteModel(ConstantUtils.MOVIE, mMovieID, !mFavorite);

        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.addFavorite(mMovieID, mapFavorite, markFavoriteModel);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String statusMessage = response.body().getAsJsonObject().get("status_message").getAsString();
                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                mFavorite = !mFavorite;
                getAccountState(mSessionID, mGuestSessionID);
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getAccountState(String sessionID, String guestSessionID) {
        Map<String, String> mapAccountSate = new HashMap<>();
        mapAccountSate.put(ConstantUtils.SESSION_ID, sessionID);
        mapAccountSate.put(ConstantUtils.GUEST_SESSION_ID, guestSessionID);

        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getAccountSates(mMovieID, mapAccountSate);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                mFavorite = response.body().getAsJsonObject().get(ConstantUtils.FAVORITE).getAsBoolean();
                fabFavorite.setImageDrawable(mFavorite ?
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_white_24dp) :
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white_24dp));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void onThumbnailClick(View view)
    {
        String videoUrl = (String) view.getTag(R.id.glide_tag);
        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(playVideoIntent);
    }

    private void setRating(float value){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID,Context.MODE_PRIVATE);
        String guest_session_id=sharedPreferences.getString(ConstantUtils.GUEST_SESSION_ID,"");
        String session_id=sharedPreferences.getString(ConstantUtils.SESSION_ID,"");
        Map<String,String> map=new HashMap<>();
        map.put(ConstantUtils.SESSION_ID,session_id);
        map.put(ConstantUtils.GUEST_SESSION_ID,guest_session_id);
        Map<String,Float> map1=new HashMap<>();
        map1.put("value",value);

        ApiInterface apiInterface=ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.setRating(mMovieID,map1,map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                MessageModel messageModel=new Gson().fromJson(response.body(),MessageModel.class);
                Toast.makeText(getContext(), messageModel.getSttMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        setRating(v*2);
    }
}
