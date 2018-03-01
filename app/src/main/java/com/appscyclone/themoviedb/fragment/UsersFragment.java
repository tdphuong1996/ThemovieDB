package com.appscyclone.themoviedb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.LoginActivity;
import com.appscyclone.themoviedb.utils.ConstantUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsersFragment extends Fragment {

//    @BindView(R.id.fragUser_ivAvatar)
//    ImageView ivAvatar;
    @BindView(R.id.fragUser_tvUsers)
    TextView tvUsers;

    private SharedPreferences mPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    @OnClick(R.id.fragUsers_tvLogout)
    public void onClick(){
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    private void init() {
        mPreferences=getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        tvUsers.setText(mPreferences.getString(ConstantUtils.USER_NAME,""));
    }

}
