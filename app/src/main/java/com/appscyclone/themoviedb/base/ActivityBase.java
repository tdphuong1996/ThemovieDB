package com.appscyclone.themoviedb.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by TDP on 05/01/2018.
 */

public class ActivityBase extends AppCompatActivity {
    public void addFragment(Fragment fragment,int layout){
        getSupportFragmentManager().beginTransaction().add(layout,fragment).commit();
    }
    public void replaceFragment(Fragment fragment,int layout){
        getSupportFragmentManager().beginTransaction().replace(layout,fragment).commit();
    }

}
