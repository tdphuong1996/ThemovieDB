package com.appscyclone.themoviedb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.MoviesAdapter;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.LoadingDialog;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.appscyclone.themoviedb.utils.KeyboardUtils;
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

public class SearchFragment extends Fragment implements TextView.OnEditorActionListener,OnClickItemListener,View.OnClickListener{
    @BindView(R.id.fragSearch_etSearch)
    EditText etSearch;
    @BindView(R.id.fragSearch_rvSearchList)
    RecyclerView rvSearchList;
    @BindView(R.id.fragSearch_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragSearch_llSearchContent)
    LinearLayout llSearchContent;
    private LoadingDialog mLoadingDialog;
    private MoviesAdapter mMoviesAdapter;
    private List<ItemMovieModel> mMovieList;
    private int mPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        KeyboardUtils.setupUI(llSearchContent,getActivity());
        init();
        return view;
    }

    private void init() {
        mMovieList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(mMovieList, this);
        rvSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchList.setAdapter(mMoviesAdapter);
        etSearch.setOnEditorActionListener(this);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(this);
        mLoadingDialog=new LoadingDialog(getContext());

    }

    private void getSearchMovie(int mPage) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        map.put(ConstantUtils.PAGE, String.valueOf(mPage));
        map.put(ConstantUtils.QUERY, etSearch.getText().toString());
        map.put(ConstantUtils.ADULT, "true");
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getSearchMovie(map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ItemMovieModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ItemMovieModel>>() {
                        }.getType());
                mMovieList.addAll(list);
                mMoviesAdapter.notifyDataSetChanged();
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        KeyboardUtils.hideSoftKeyboard(getActivity());
        getSearchMovie(1);
        mLoadingDialog.show();
        mMovieList.clear();
        mMoviesAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public void onClickItem(int position) {
        Fragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", mMovieList.get(position).getId());
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("abc").add(R.id.actMain_layout_Frag, fragment).commit();

    }

    @Override
    public void onClick(View view) {
        getActivity().getSupportFragmentManager().popBackStack();
        ((MainActivity) getContext()).setHideBottomBar(true);
    }
}
