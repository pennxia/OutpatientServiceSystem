package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.materialdesign.bean.HealthNews;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

public class HealthNewsRecycleViewAdapter extends RecyclerView.Adapter<HealthNewsRecycleViewAdapter.ViewHolder> {

    List<HealthNews> displatHealthNewsList;
    Context mContext;
    AppCompatActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView newsTitleTextView;
        TextView newsTypeTextView;
        TextView publishTimeTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            this.newsTitleTextView = itemView.findViewById(R.id.recycleview_item_fragment_hospitalhome_newstitle_textview);
            this.newsTypeTextView = itemView.findViewById(R.id.recycleview_item_fragment_hospitalhome_newstype_textview);
            this.publishTimeTextView = itemView.findViewById(R.id.recycleview_item_fragment_hospitalhome_publishtime_textview);
            this.imageView = itemView.findViewById(R.id.recycleview_item_fragment_hospitalhome_imageview);
        }
    }

    public HealthNewsRecycleViewAdapter(AppCompatActivity activity, List<HealthNews> displayHealthNewsList) {
        this.displatHealthNewsList = displayHealthNewsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activityandfragment_healthnews, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //enterHealthNewsDetailsActivity(position);
                Utility.showToastShort(activity,displatHealthNewsList.get(position).getUrl());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthNews healthNews = displatHealthNewsList.get(position);
        Glide.with(mContext).load(Utility.getHealthNewsIconAddress(healthNews.getHealthNewsIconName())).into(holder.imageView);
        holder.newsTitleTextView.setText(healthNews.getHealthNewsTitle());
        holder.newsTypeTextView.setText(healthNews.getHealthNewsType());
        holder.publishTimeTextView.setText(Utility.handleHealthNewsPublishTime(healthNews.getHealthNewsPublishTime()));
    }

    @Override
    public int getItemCount() {
        return displatHealthNewsList.size();
    }

}
