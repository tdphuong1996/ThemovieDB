package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import butterknife.ButterKnife;

public class MovieFragment extends Fragment {
//    @BindView(R.id.actMain_nts)
    NavigationTabStrip NaviSt;
//    @BindView(R.id.actMain_viewPager)
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        NaviSt= view.findViewById(R.id.actMain_nts);
        viewPager= view.findViewById(R.id.actMain_viewPager);

        init();
        return view;
    }

    private void init() {

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new PopularFragment();
                    case 1:
                        return new NowFragment();
                    case 2:
                        return new TopRatedFragment();
                    default:
                        return null;
                }
            }
        });
        NaviSt.setViewPager(viewPager);
        NaviSt.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
