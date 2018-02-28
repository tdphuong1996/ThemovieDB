package com.appscyclone.themoviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appscyclone.themoviedb.fragment.NowFragment;
import com.appscyclone.themoviedb.fragment.PopularFragment;
import com.appscyclone.themoviedb.fragment.TopRatedFragment;

public class PageAdapter extends FragmentStatePagerAdapter {
    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new PopularFragment();
                break;
            case 1:
                frag = new NowFragment();
                break;
            case 2:
                frag = new TopRatedFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "POPULAR";
                break;
            case 1:
                title = "NOW";
                break;
            case 2:
                title = "TOP RATED";
                break;
        }
        return title;
    }
}
