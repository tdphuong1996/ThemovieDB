package com.appscyclone.themoviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TDP on 15/03/2018.
 */

public class Movie1Adapter extends RecyclerView.Adapter<Movie1Adapter.ViewHolder>{

    private List<ItemMovieModel> movieList;
    private OnClickItemListener mCallBack;
    public Movie1Adapter(List<ItemMovieModel> movieList, Fragment fragment) {
        this.movieList = movieList;
        mCallBack= (OnClickItemListener) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie1,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemMovieModel model=movieList.get(position);
        holder.tvName.setText(model.getOriginalTitle());
        holder.tvYear.setText(model.getReleaseDate());
        Glide.with(holder.ivPoster.getContext())
                .load(ConstantUtils.IMAGE_URL+model.getPosterPath())
                .apply(new RequestOptions().placeholder(R.drawable.ic_movie_holder))
                .into(holder.ivPoster);

    }

    @Override
    public int getItemCount() {
        return movieList!=null?movieList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemMovie1_ivPoster)
        ImageView ivPoster;
        @BindView(R.id.itemMovie1_tvName)
        TextView tvName;
        @BindView(R.id.itemMovie1_tvYear)
        TextView tvYear;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.itemMovie1_ctContent)
        public void onClick(){
            mCallBack.onClickItem(getAdapterPosition());
        }
    }
}
