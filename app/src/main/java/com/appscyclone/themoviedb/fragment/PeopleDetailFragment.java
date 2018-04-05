package com.appscyclone.themoviedb.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.PageAdapter;
import com.appscyclone.themoviedb.model.ExternallIDModel;
import com.appscyclone.themoviedb.model.ImageTagModel;
import com.appscyclone.themoviedb.model.PeopleDetailModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class PeopleDetailFragment extends Fragment {

    @BindView(R.id.fragPeopleDetails_ctLayout)
    CollapsingToolbarLayout collapsingToolbar;
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
    @BindView(R.id.viewAvatar_llContent)
    LinearLayout llContent;

    @BindView(R.id.fragPeopleDetail_flbinst)
    FloatingActionButton flbInstagram;
    @BindView(R.id.fragPeopleDetail_flbFb)
    FloatingActionButton flbFb;
    @BindView(R.id.fragPeopleDetail_flbtwitter)
    FloatingActionButton flbTwitter;

    private PeopleDetailModel mPeopleDetail;
    private ExternallIDModel mExternallIDModel;

    public int mIDPeople;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick({R.id.fragPeopleDetails_ivBack, R.id.fragPeopleDetail_flbFb, R.id.fragPeopleDetail_flbtwitter, R.id.fragPeopleDetail_flbinst})
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (view.getId()) {
            case R.id.fragPeopleDetail_flbFb:
                intent.setData(Uri.parse("https://fb.com/" + mExternallIDModel.getFacebook()));
                break;
            case R.id.fragPeopleDetail_flbinst:
                intent.setData(Uri.parse("https://www.instagram.com/" + mExternallIDModel.getInstagram()));
                break;
            case R.id.fragPeopleDetail_flbtwitter:
                intent.setData(Uri.parse("https://twitter.com/" + mExternallIDModel.getTwitter()));
                break;
            case R.id.fragPeopleDetails_ivBack:
                getActivity().getSupportFragmentManager().popBackStack();
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).setHideBottomBar(true);
                break;
        }
        startActivity(intent);
    }

    private void init() {
        setToolbar();
        Bundle bundle = getArguments();
        assert bundle != null;
        mIDPeople = bundle.getInt(ConstantUtils.ID_PEOPLE);
        loadPeopleDetail(mIDPeople);
        loadExternalID(mIDPeople);
        getTagImage(mIDPeople);


    }

    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.red));
        collapsingToolbar.setTitle(getString(R.string.people_detail));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
    }

    private void loadPeopleDetail(int idPeople) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getPeopleDetail(idPeople, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                mPeopleDetail = new Gson().fromJson(response.body(), PeopleDetailModel.class);
                tvName.setText(mPeopleDetail.getName());
                Glide.with(getContext()).load(ConstantUtils.IMAGE_URL + mPeopleDetail.getProfilePath()).into(ivAvatar);
                PageAdapter mPageAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
                mPageAdapter.addFragment(PeopleInfoFragment.newInstance(mPeopleDetail), "INFO");
                mPageAdapter.addFragment(PeopleMovieFragment.newInstance(mIDPeople), "MOVIES");
                viewPager.setAdapter(mPageAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getTagImage(int id) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getTaggerImage(id, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ImageTagModel> list = new Gson().fromJson(response.body().get("results").toString(),
                        new TypeToken<List<ImageTagModel>>() {
                        }.getType());
                if (list != null) {
                    List<Banner> bannerList = new ArrayList<>();
                    int size = list.size() < 8 ? list.size() : 8;
                    for (int i = 0; i < size; i++) {
                        bannerList.add(new RemoteBanner(ConstantUtils.IMAGE_URL + list.get(i).getFilePath()));
                    }
                    bannerSlider.setBanners(bannerList);
                } else {
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void loadExternalID(int idPeople) {
        Map<String, String> map = new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getExternalIDs(idPeople, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                mExternallIDModel = new Gson().fromJson(response.body(), ExternallIDModel.class);
                flbFb.setVisibility(Objects.equals(mExternallIDModel.getFacebook(), "") ? View.GONE : View.VISIBLE);
                flbInstagram.setVisibility(Objects.equals(mExternallIDModel.getInstagram(), "") ? View.GONE : View.VISIBLE);
                flbTwitter.setVisibility(Objects.equals(mExternallIDModel.getTwitter(), "") ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
