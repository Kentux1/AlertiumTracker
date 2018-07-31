package com.kentux.alertiumtracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kentux.alertiumtracker.MainActivity;
import com.kentux.alertiumtracker.R;
import com.kentux.alertiumtracker.models.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context mContext;
    private ArrayList<News> mNews;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (position < getItemCount()) {
            News news = mNews.get(position);
            String newsMessage = news.getmNewsMessage();
            String newsThumbnail = news.getmNewsThumbnail();
            final String newsLink = news.getmNewsLink();

            if (!newsMessage.isEmpty()) {
                holder.newsMessage.setText(newsMessage);
            }

            if (!newsThumbnail.isEmpty()) {
                Picasso.get()
                        .load(newsThumbnail)
                        .into(holder.newsThumb);
            }

            holder.newsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(newsLink));
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (mNews == null) ? 0 : mNews.size();
    }

    public NewsAdapter() {
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_thumb)
        ImageView newsThumb;
        @BindView(R.id.news_message)
        TextView newsMessage;
        @BindView(R.id.news_card_view)
        CardView newsCardView;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setNews(ArrayList<News> news) {
        this.mNews = news;
        notifyDataSetChanged();
    }
}


