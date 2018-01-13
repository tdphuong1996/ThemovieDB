package com.appscyclone.themoviedb.base;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


@SuppressLint("Registered")
public class ActivityBase extends AppCompatActivity {
//    public void addFragment(Fragment fragment,int layout){
//        getSupportFragmentManager().beginTransaction().add(layout,fragment).commit();
//    }
    public void replaceFragment(Fragment fragment,int layout){
        getSupportFragmentManager().beginTransaction().replace(layout,fragment).commit();
    }

}
