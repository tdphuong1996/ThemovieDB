package com.appscyclone.themoviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appscyclone.themoviedb.R;

import butterknife.ButterKnife;

/**
 * Created by TDP on 09/03/2018.
 */

public class PeopleMovieFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_people_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
