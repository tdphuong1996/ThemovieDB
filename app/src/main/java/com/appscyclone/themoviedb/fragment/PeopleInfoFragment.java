package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.ImagePeopleModel;
import com.appscyclone.themoviedb.model.PeopleDetailModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TDP on 09/03/2018.
 */

public class PeopleInfoFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.fragPeopleDetail_tvBiography)
    TextView tvBiography;
    @BindView(R.id.fragPeopleDetail_tvBorn)
    TextView tvBorn;
    @BindView(R.id.fragPeopleDetail_tvBirthPlace)
    TextView tvBirthPlace;

    @BindView(R.id.fragPeopleDetail_llImage)
    LinearLayout llImage;


    public static PeopleInfoFragment newInstance(PeopleDetailModel model) {

        Bundle args = new Bundle();
        args.putSerializable(ConstantUtils.INFO_PEOPLE, model);
        PeopleInfoFragment fragment = new PeopleInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_people_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle bundle = getArguments();
        PeopleDetailModel peopleDetailModel = (PeopleDetailModel) bundle.getSerializable(ConstantUtils.INFO_PEOPLE);

        assert peopleDetailModel != null;
        tvBirthPlace.setText(String.format(getString(R.string.birthplace), peopleDetailModel.getPlaceOfBirth()));
        tvBorn.setText(String.format(getString(R.string.born), peopleDetailModel.getBirthday()));

        getImagePeople(peopleDetailModel.getId());

    }


    private void getImagePeople(int idPeople) {
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getImagePeople(idPeople);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                List<ImagePeopleModel> list = new Gson().fromJson(response.body().get("profiles").toString(),
                        new TypeToken<List<ImagePeopleModel>>() {
                        }.getType());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.color.colorPrimary)
                        .centerCrop()
                        .override(150, 150);

                int size = list.size();
                for (int i = 0; i < size; i++) {
                    View thumbContainer = inflater.inflate(R.layout.item_image_people, llImage, false);
                    ImageView thumbView = ButterKnife.findById(thumbContainer, R.id.itemImage_ivImage);
                    thumbView.requestLayout();

                    Glide.with(getContext())
                            .load(ConstantUtils.IMAGE_URL+list.get(i).getFilePath())
                            .apply(options)
                            .into(thumbView);
                    llImage.addView(thumbContainer);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
