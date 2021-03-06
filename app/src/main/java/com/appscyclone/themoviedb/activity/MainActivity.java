package com.appscyclone.themoviedb.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.base.ActivityBase;
import com.appscyclone.themoviedb.fragment.FavoriteFragment;
import com.appscyclone.themoviedb.fragment.MovieFragment;
import com.appscyclone.themoviedb.fragment.PeopleFragment;
import com.appscyclone.themoviedb.fragment.UsersFragment;
import com.appscyclone.themoviedb.model.AccountModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends ActivityBase implements  BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.actMain_bottomNavi)
    BottomNavigationView bottomNavi;

    private String mSessionID,mGuestSessionId;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mSharedPreferences = getSharedPreferences(ConstantUtils.ACCOUNT_ID, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        mSessionID = intent.getStringExtra(ConstantUtils.SESSION);
        mGuestSessionId=intent.getStringExtra(ConstantUtils.GUEST_SESSION_ID);
        if(mSessionID!=null){
            SharedPreferences.Editor editor=mSharedPreferences.edit();
            editor.putString(ConstantUtils.SESSION_ID,mSessionID);
            editor.apply();
        }
        replaceFragment(new MovieFragment(), R.id.actMain_layout_Frag);
        removeShiftMode(bottomNavi);
        bottomNavi.setOnNavigationItemSelectedListener(this);


    }

    private void getAccount() {
        Map<String, String> mapAccount = new HashMap<>();
        mapAccount.put("session_id", mSessionID);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getAccount(mapAccount);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                AccountModel accountModel = new Gson().fromJson(response.body(), AccountModel.class);
                editor.putInt(ConstantUtils.ACCOUNT_ID, accountModel.getId());
                editor.putString(ConstantUtils.USER_NAME, accountModel.getUsername());
                editor.putString(ConstantUtils.GUEST_SESSION_ID,mGuestSessionId);
                editor.apply();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
    }

    public void setHideBottomBar(boolean b) {
        bottomNavi.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setHideBottomBar(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String accountID= mSharedPreferences.getString(ConstantUtils.SESSION_ID,"0");
       if(accountID.equals("0")){
          startActivity(new Intent(MainActivity.this,LoginActivity.class));
       }else {
           getAccount();
       }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_movie:
                fragment = new MovieFragment();
                break;
            case R.id.menu_people:
                fragment = new PeopleFragment();
                break;
            case R.id.menu_favorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.menu_user:
                fragment=new UsersFragment();
                break;
        }
        if (fragment != null) {
            replaceFragment(fragment, R.id.actMain_layout_Frag);
        }
        return true;
    }
}
