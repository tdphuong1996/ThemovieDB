package com.appscyclone.themoviedb.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.base.ActivityBase;
import com.appscyclone.themoviedb.fragment.FavoriteFragment;
import com.appscyclone.themoviedb.fragment.MovieFragment;
import com.appscyclone.themoviedb.fragment.PeopleFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ActivityBase  {
    @BindView(R.id.actMain_bottomNavi)
    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        replaceFragment(new MovieFragment(), R.id.actMain_layout_Frag);
        removeShiftMode(bottomNavi);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
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
                    case R.id.menu_more:
                        Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (fragment != null) {
                    replaceFragment(fragment, R.id.actMain_layout_Frag);
                }
                return true;
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
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {
        }
    }
    public void setHideBottomBar(boolean b){
        if(b){
            bottomNavi.setVisibility(View.GONE);
        }else {
            bottomNavi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setHideBottomBar(false);
    }
}
