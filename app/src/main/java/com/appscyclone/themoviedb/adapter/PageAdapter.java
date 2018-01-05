package com.appscyclone.themoviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by TDP on 05/01/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    private ArrayList<String> arrayTitle=new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList!=null?fragmentArrayList.size():0;
    }
    public void addFragment(Fragment fragment,String title){
        fragmentArrayList.add(fragment);
        arrayTitle.add(title);
    }
    @Override
    public  CharSequence getPageTitle(int position){
        return arrayTitle.get(position);
    }
}
