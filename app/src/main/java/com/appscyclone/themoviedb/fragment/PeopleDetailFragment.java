package com.appscyclone.themoviedb.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.adapter.PageAdapter;
import com.appscyclone.themoviedb.model.ImageTagModel;
import com.appscyclone.themoviedb.model.PeopleDetailModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class PeopleDetailFragment extends Fragment {

    @BindView(R.id.fragPeopleDetail_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fragPeopleDetail_viewPager)
    ViewPager viewPager;
    @BindView(R.id.fragPeopleDetail_ivAvatar)
    CircleImageView ivAvatar;
    @BindView(R.id.fragPeopleDetail_tvName)
    TextView tvName;
    @BindView(R.id.fragPeopleDetail_ivPoster)
    BannerSlider bannerSlider;

    private PeopleDetailModel mPeopleDetail;
//
//    public static PeopleDetailFragment newInstance(int id) {
//
//        Bundle args = new Bundle();
//        args.putInt(ConstantUtils.ID_PEOPLE,id);
//        PeopleDetailFragment fragment = new PeopleDetailFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
    public int mIDPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_detail, container, false);
        ButterKnife.bind(this, view);


        init();
        return view;
    }

    private void init() {
        Bundle bundle = getArguments();
        mIDPeople = bundle.getInt(ConstantUtils.ID_PEOPLE);
        loadPeopleDetail(mIDPeople);
        getTagImage(mIDPeople);
    }

    private void loadPeopleDetail(int idPeople) {
        Map<String,String> map =new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getPeopleDetail(idPeople, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                mPeopleDetail = new Gson().fromJson(response.body(), PeopleDetailModel.class);
                tvName.setText(mPeopleDetail.getName());
                Glide.with(getContext()).load(ConstantUtils.IMAGE_URL+mPeopleDetail.getProfilePath()).into(ivAvatar);
                PageAdapter mPageAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
                PeopleInfoFragment peopleInfoFragment=new PeopleInfoFragment();
                Bundle bundle1=new Bundle();
                bundle1.putSerializable(ConstantUtils.INFO_PEOPLE,mPeopleDetail);
                peopleInfoFragment.setArguments(bundle1);
                mPageAdapter.addFragment( peopleInfoFragment, "INFO");
                mPageAdapter.addFragment(new PeopleMovieFragment(), "MOVIES");
                viewPager.setAdapter(mPageAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getTagImage(int id){
        Map<String,String> map =new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.getTaggerImage(id,map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ImageTagModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ImageTagModel>>() {
                        }.getType());

                List<Banner> bannerList=new ArrayList<>();
                for(int i=0;i<8;i++){
                    bannerList.add(new RemoteBanner(ConstantUtils.IMAGE_URL+list.get(i).getFilePath()));
                }
                bannerSlider.setBanners(bannerList);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

//    private void loadExternalID(int idPeople) {
//        Map<String,String> map =new HashMap<>();
//        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
//        ApiInterface apiInterface = ApiUtils.getSOService();
//        Call<JsonObject> call = apiInterface.getExternalIDs(idPeople, map);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                ExternallIDModel model = new Gson().fromJson(response.body(), ExternallIDModel.class);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//    }

}
