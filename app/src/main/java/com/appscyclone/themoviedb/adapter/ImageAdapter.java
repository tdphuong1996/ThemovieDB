package com.appscyclone.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.ImagePeopleModel;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TDP on 09/03/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImagePeopleModel> mListImage;

    public ImageAdapter(List<ImagePeopleModel> mListImage) {
        this.mListImage = mListImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_people,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.ivImage.getContext())
                .load(ConstantUtils.IMAGE_URL+mListImage.get(position).getFilePath())
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mListImage!=null?mListImage.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemImage_ivImage)
        ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
