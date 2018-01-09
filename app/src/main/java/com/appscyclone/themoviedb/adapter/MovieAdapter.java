package com.appscyclone.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.ItemMovieModel;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<ItemMovieModel> mMovieList;
    private OnItemSelectedListener mListener;
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


        ItemMovieModel items = mMovieList.get(position);
        holder.tvTitle.setText(items.getTitle());
        holder.tvOverview.setText(items.getOverview());
        holder.tvRate.setText(valueOf(items.getVoteAverage()));

       StringBuilder genres =new StringBuilder();
       for(int i=0;i<items.getGenreIds().size();i++){
           genres.append(String.valueOf(items.getGenreIds().get(i)));
       }
        holder.tvGenres.setText(String.valueOf(genres));
        Picasso.with(holder.itemView.getContext())
                .load(ConstantUtils.IMAGE_URL + items.getPosterPath())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList != null ? mMovieList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemMovie_tvGenres)
        TextView tvGenres;
        @BindView(R.id.itemMovie_tvOverview)
        TextView tvOverview;
        @BindView(R.id.itemMovie_tvRate)
        TextView tvRate;
        @BindView(R.id.itemMovie_tvTitle)
        TextView tvTitle;
        @BindView(R.id.itemMovie_imgPoster)
        ImageView imgPoster;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view,getAdapterPosition());
                }
            });
        }

    }
    public interface OnItemSelectedListener {
        void onItemSelected(View v, int position);
    }
    public void setOnItemClickLister(OnItemSelectedListener mListener) {
        this.mListener = mListener;
    }
}
