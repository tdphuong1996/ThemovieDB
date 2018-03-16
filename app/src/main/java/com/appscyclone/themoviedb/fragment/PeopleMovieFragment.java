package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.adapter.Movie1Adapter;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.LoadingDialog;
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

/**
 * Created by TDP on 09/03/2018.
 */

public class PeopleMovieFragment extends Fragment implements OnClickItemListener{
    @BindView(R.id.viewPeopleMovie_rvListMovie)
    RecyclerView rvListMovie;
    @BindView(R.id.viewPeopleMovie_tvCredits)
    TextView tvCredits;
    private LoadingDialog mLoadingDialog;
    private Movie1Adapter mMovie1Adapter;
    private List<ItemMovieModel> mMovieList;

    public static PeopleMovieFragment newInstance(int idPeople) {

        Bundle args = new Bundle();
        args.putInt(ConstantUtils.ID_PEOPLE,idPeople);
        PeopleMovieFragment fragment = new PeopleMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_people_movie, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        mMovieList=new ArrayList<>();
        mMovie1Adapter=new Movie1Adapter(mMovieList,this);
        rvListMovie.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvListMovie.setAdapter(mMovie1Adapter);
        Bundle bundle=getArguments();
        loadMovieCredit(bundle.getInt(ConstantUtils.ID_PEOPLE));

        mLoadingDialog=new LoadingDialog(getContext());
        mLoadingDialog.show();
        mLoadingDialog.setCanceledOnTouchOutside(true);
    }

    private void loadMovieCredit(int idPeople) {
        Map<String, String> mapMovie = new HashMap<>();
        mapMovie.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface= ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.getPeopleMovieCredits(idPeople,mapMovie);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ItemMovieModel> list = new Gson().fromJson(response.body().get("cast").toString(),
                        new TypeToken<List<ItemMovieModel>>() {
                        }.getType());
                mMovieList.addAll(list);

                mMovie1Adapter.notifyDataSetChanged();
                tvCredits.setText(String.format(getString(R.string.acting),String.valueOf(list.size())));
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mLoadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onClickItem(int position) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(getClass().getSimpleName())
                .add(R.id.fragPeopleDetail_cdlContent, MovieDetailFragment.newInstance(mMovieList.get(position).getId()))
                .commit();
    }
}
