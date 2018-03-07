package com.appscyclone.themoviedb.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<ItemMovieModel> mMovieList;
    private OnClickItemListener mCallBack;

    public MoviesAdapter(List<ItemMovieModel> mMovieList, Fragment fragment) {
        this.mMovieList = mMovieList;
        mCallBack = (OnClickItemListener) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemMovieModel itemMovieModel = mMovieList.get(position);
        holder.tvDate.setText(itemMovieModel.getReleaseDate());
        GradientDrawable bgShape = (GradientDrawable)holder.tvRating.getBackground();

        if(itemMovieModel.getVoteAverage()>=7){
            bgShape.setColor(Color.argb(255,18,91,63));
        }else {
            bgShape.setColor(Color.argb(255,210,213,49));

        }
        holder.tvRating.setText(String.valueOf(itemMovieModel.getVoteAverage()+"☆" ));
        holder.tvName.setText(itemMovieModel.getTitle());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH);

        Glide.with(holder.tvName.getContext())
                .asBitmap()
                .load(ConstantUtils.IMAGE_URL + itemMovieModel.getBackdropPath())
                .apply(options)
                .into(new BitmapImageViewTarget(holder.ivPoster) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);
                        Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
                    }
                });


    }

    private void setBackgroundColor(Palette palette, MoviesAdapter.ViewHolder holder) {
        holder.viewTitleBG.setBackgroundColor(palette.getVibrantColor(holder.tvName.getContext()
                .getResources().getColor(R.color.black_translucent_60)));
    }

    @Override
    public int getItemCount() {
        return mMovieList != null ? mMovieList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemMovies_ivPoster)
        ImageView ivPoster;
        @BindView(R.id.itemMovies_tvName)
        TextView tvName;
        @BindView(R.id.itemMovies_tvDate)
        TextView tvDate;
        @BindView(R.id.itemMovies_tvRating)
        TextView tvRating;
        @BindView(R.id.itemMovies_title_background)
        View viewTitleBG;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.itemMovies_flContent)
        public void onClick() {
            mCallBack.onClickItem(getAdapterPosition());
        }
    }
}
