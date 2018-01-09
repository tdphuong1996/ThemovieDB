package com.appscyclone.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.PeopleModel;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TDP on 09/01/2018.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private List<PeopleModel> mPeopleList;

    public PeopleAdapter(List<PeopleModel> mPeopleList) {
        this.mPeopleList = mPeopleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PeopleModel model = mPeopleList.get(position);
        holder.tvName.setText(model.getName());
        Picasso.with(holder.itemView.getContext()).load(ConstantUtils.IMAGE_URL + model.getProfilePath()).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return mPeopleList != null ? mPeopleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemPeople_ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.itemPeople_tvName)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
