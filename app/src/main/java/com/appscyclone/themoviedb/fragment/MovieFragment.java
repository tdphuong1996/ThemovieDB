package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.adapter.PageAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment {
    @BindView(R.id.actMain_nts)
    NavigationTabStrip NaviSt;
    @BindView(R.id.actMain_viewPager)
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        PageAdapter pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
        pageAdapter.addFragment(new PopularFragment());
        pageAdapter.addFragment(new NowFragment());
        pageAdapter.addFragment(new TopRatedFragment());
        viewPager.setAdapter(pageAdapter);
        NaviSt.setViewPager(viewPager);
        NaviSt.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                viewPager.setCurrentItem(index);
            }
            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });

    }


}
