package com.fzo.znwork.ui.main.leftfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fzo.znwork.R;
import com.fzo.znwork.ui.detail.NewsDetailActivity;
import com.fzo.znwork.util.model.PushNews;

import java.util.List;

// 主界面viewpager的adapter
public class NewsPushAdapter extends RecyclerView.Adapter<NewsPushAdapter.ViewHolder> {

    private Context context;
    private List<PushNews> pushNewsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;
        TextView authorText;
        TextView timeText;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.push_news_image);
            titleText = view.findViewById(R.id.push_news_title);
            authorText = view.findViewById(R.id.push_news_author);
            timeText = view.findViewById(R.id.push_news_time);
        }
    }

    public NewsPushAdapter(List<PushNews> pushNewsList) {
        this.pushNewsList = pushNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_push_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            PushNews pushNews = pushNewsList.get(holder.getAdapterPosition());
            NewsDetailActivity.actionStart(context, pushNews.getTitle(), pushNews.getUrl());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PushNews pushNews = pushNewsList.get(position);
        Glide.with(context).load(pushNews.getPictureUrl()).into(holder.imageView);
        holder.titleText.setText(pushNews.getTitle());
        holder.authorText.setText(pushNews.getAuthor());
        holder.timeText.setText(pushNews.getTime());
    }

    @Override
    public int getItemCount() {
        return pushNewsList.size();
    }
}
