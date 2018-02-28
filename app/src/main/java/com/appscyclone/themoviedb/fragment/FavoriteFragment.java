package com.appscyclone.themoviedb.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.MovieAdapter;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.ProcessDialog;
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


public class FavoriteFragment extends Fragment implements OnClickItemListener {
    @BindView(R.id.fragFavorite_rvFavoriteList)
    RecyclerView rvFavoriteList;

    private MovieAdapter mMovieAdapter;
    private List<ItemMovieModel> mMovieList;

    private ProcessDialog mProcessDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {
        mMovieList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(mMovieList,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFavoriteList.setLayoutManager(layoutManager);
        rvFavoriteList.setAdapter(mMovieAdapter);
        mProcessDialog=new ProcessDialog(getContext(),getString(R.string.loading));
        mProcessDialog.show();
        mProcessDialog.setCanceledOnTouchOutside(true);
        getFavorite(1);

    }

    private void getFavorite(int page){
        SharedPreferences preferences=getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        Map<String, String> mapMovie = new HashMap<>();
        mapMovie.put(ConstantUtils.PAGE, String.valueOf(page));
        mapMovie.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        mapMovie.put(ConstantUtils.SESSION_ID,preferences.getString(ConstantUtils.SESSION_ID,""));
        mapMovie.put(ConstantUtils.SORT_BY,ConstantUtils.CREATED_AT);

        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getFavorite(preferences.getInt(ConstantUtils.ACCOUNT_ID,0),mapMovie);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ItemMovieModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ItemMovieModel>>() {
                        }.getType());
                mMovieList.addAll(list);
                mMovieAdapter.notifyDataSetChanged();
                mProcessDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickItem(int position) {
        Fragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", mMovieList.get(position).getId());
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("abc").add(R.id.actMain_layout_Frag, fragment).commit();
        if(getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setHideBottomBar(false);
    }
}
