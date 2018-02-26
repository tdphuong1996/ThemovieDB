package com.appscyclone.themoviedb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.PeopleAdapter;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.PeopleModel;
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


public class PeopleFragment extends Fragment implements OnClickItemListener {
    @BindView(R.id.fragPeople_rvPeopleList)
    RecyclerView rvPeopleList;
    private List<PeopleModel> mPeopleList;
    private PeopleAdapter mPeopleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {

        mPeopleList = new ArrayList<>();
        rvPeopleList.setHasFixedSize(true);
        rvPeopleList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mPeopleAdapter = new PeopleAdapter(mPeopleList,this);
        rvPeopleList.setAdapter(mPeopleAdapter);
        loadPeople();


    }

    private void loadPeople() {
        Map<String, String> mapPeople = new HashMap<>();
        mapPeople.put(ConstantUtils.PAGE, String.valueOf(1));
        mapPeople.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getPeople(mapPeople);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<PeopleModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<PeopleModel>>() {
                        }.getType());
                mPeopleList.addAll(list);
                mPeopleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickItem(int position) {
        PeopleDetailFragment fragment=new PeopleDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.ID_PEOPLE,mPeopleList.get(position).getId());
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("abc").add(R.id.actMain_layout_Frag, fragment).commit();
        if(getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setHideBottomBar(false);
    }
}
