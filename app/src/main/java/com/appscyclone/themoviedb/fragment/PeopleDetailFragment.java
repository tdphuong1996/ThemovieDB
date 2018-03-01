package com.appscyclone.themoviedb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.model.PeopleDetailModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.ReadMoreDialog;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDetailFragment extends Fragment {

    @BindView(R.id.fragDetailPeople_ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.fragDetailPeople_ivFB)
    ImageView ivFacebook;
    @BindView(R.id.fragDetailPeople_ivTW)
    ImageView ivTwitter;
    @BindView(R.id.fragDetailPeople_ivIG)
    ImageView ivInstagram;
    @BindView(R.id.fragDetailPeople_tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.fragDetailPeople_tvGender)
    TextView tvGender;
    @BindView(R.id.fragDetailPeople_tvKnownCredits)
    TextView tvKnownCredits;
    @BindView(R.id.fragDetailPeople_tvKnownFor)
    TextView tvKnownFor;
    @BindView(R.id.fragDetailPeople_tvBiography)
    TextView tvBiography;
    @BindView(R.id.fragDetailPeople_tvPlace)
    TextView tvPlace;
    @BindView(R.id.fragDetailPeople_tvName)
    TextView tvName;
    private String mContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick({R.id.fragDetailPeople_tvReadMore,R.id.viewCT_ivBack})
    public void onClick(View view){
      switch (view.getId()){
          case R.id.viewCT_ivBack:
              getActivity().getSupportFragmentManager().popBackStack();
              ((MainActivity) getContext()).setHideBottomBar(true);
              break;
          case R.id.fragDetailPeople_tvReadMore:
              new ReadMoreDialog(view.getContext(),tvBiography.getText().toString()).show();
      }
    }

    private void init() {
        Bundle bundle = getArguments();
        loadPeopleDetail(bundle.getInt(ConstantUtils.ID_PEOPLE));
    }

    private void loadPeopleDetail(int idPeople) {
        Map<String,String> map =new HashMap<>();
        map.put(ConstantUtils.LANGUAGE, ConstantUtils.EN_US);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getPeopleDetail(idPeople, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                PeopleDetailModel model = new Gson().fromJson(response.body(), PeopleDetailModel.class);
                tvBirthday.setText(model.getBirthday());
                mContent = model.getBiography();
                tvGender.setText(model.convertGender(model.getGender()));
                tvBiography.setText(mContent);
                tvPlace.setText(model.getPlaceOfBirth());
                tvName.setText(model.getName());
                Glide.with(getContext()).load(ConstantUtils.IMAGE_URL + model.getProfilePath()).into(ivAvatar);
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
