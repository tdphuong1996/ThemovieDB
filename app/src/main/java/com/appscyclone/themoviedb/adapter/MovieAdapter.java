package com.appscyclone.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.ItemMovieModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<ItemMovieModel> mMovieList;

    public MovieAdapter(List<ItemMovieModel> mMovieList) {
        this.mMovieList = mMovieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        ItemMovieModel items=mMovieList.get(position);
        holder.tvTitle.setText(items.getTitle());
        holder.tvOverview.setText(items.getOverview());
        holder.tvRate.setText(String.valueOf(items.getVoteAverage()));
        StringBuilder genres=new StringBuilder();
//        for(int i=0;i<=items.getGenreIds().size();i++){
//            genres.append(items.getGenreIds().get(i)).append(", ");
//        }
//        holder.tvGenres.setText(genres);
    }

    @Override
    public int getItemCount() {
        return mMovieList!=null?mMovieList.size():0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemMovie_tvGenres) TextView tvGenres;
        @BindView(R.id.itemMovie_tvOverview) TextView tvOverview;
        @BindView(R.id.itemMovie_tvRate) TextView tvRate;
        @BindView(R.id.itemMovie_tvTitle) TextView tvTitle;
         ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
