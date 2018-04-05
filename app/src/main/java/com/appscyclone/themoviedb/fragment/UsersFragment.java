package com.appscyclone.themoviedb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    @BindView(R.id.fragUser_tvAvatar)
    TextView tvAvatar;
    @BindView(R.id.fragUser_tvUsers)
    TextView tvUsers;

    private SharedPreferences mPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

    public String getTextAvatar(String name){
        String [] split =name.split("\\s");
        int size =split.length;
        String first = null,last=null;
        for(int i=0;i<size;i++){
            first=split[0].substring(0,1).toUpperCase();
            last=split[size-1].substring(0,1).toUpperCase();
        }
        return first+last;
    }

    private void init() {
        mPreferences=getContext().getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        tvUsers.setText(mPreferences.getString(ConstantUtils.USER_NAME,""));
        tvAvatar.setText(getTextAvatar(tvUsers.getText().toString()));
    }



}
