package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.activity.MainActivity;
import com.appscyclone.themoviedb.adapter.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieFragment extends Fragment   {
    @BindView(R.id.fragMovie_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fragMovie_viewPager)
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick(R.id.fragMovie_ivSearch)
    public void onClick(){
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("abcd").add(R.id.actMain_layout_Frag, new SearchFragment()).commit();
        if(getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setHideBottomBar(false);



    }
    private void init() {
        PageAdapter pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
        pageAdapter.addFragment(new PopularFragment(),"POPULAR");
        pageAdapter.addFragment(new NowFragment(),"NOW");
        pageAdapter.addFragment(new TopRatedFragment(),"TOP RATED");
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
