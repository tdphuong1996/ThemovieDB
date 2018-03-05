package com.appscyclone.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.interfaces.OnClickItemListener;
import com.appscyclone.themoviedb.model.ReviewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<ReviewModel> mReviewsList;
    private OnClickItemListener mCallBack;

    public ReviewsAdapter(List<ReviewModel> mReviewsList) {
        this.mReviewsList = mReviewsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewModel model = mReviewsList.get(position);
        holder.tvAuthor.setText(model.getAuthor());
        holder.tvContent.setText(model.getContent());
        holder.tvAvatar.setText(model.getTextAvatar());
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewReview_tvContent)
        TextView tvContent;
        @BindView(R.id.viewReview_tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.viewReview_tvAvatar)
        TextView tvAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.viewReview_rlContent)

        public void onClick() {
            if (tvContent.getMaxLines() == 5) {
                tvContent.setMaxLines(500);
            } else {
                tvContent.setMaxLines(5);
            }
        }


    }
}
