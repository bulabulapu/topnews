package com.fzo.znwork.ui.main.rightfragment.newsfragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fzo.znwork.R;
import com.fzo.znwork.ui.detail.NewsDetailActivity;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.model.News;

import java.util.List;

//主界面新闻展示的recycler的adapter
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsOrder;
        TextView newsTitle;
        TextView newsFollow; // 新闻的次序,标题,热度或作者

        ViewHolder(View view) {
            super(view);
            newsOrder = view.findViewById(R.id.news_order);
            newsTitle = view.findViewById(R.id.news_title);
            newsFollow = view.findViewById(R.id.news_follow);
        }
    }

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> { // 为item添加监听,点击启动详情界面
            News news = newsList.get(holder.getAdapterPosition());
            Repository.addHistory(news);
            NewsDetailActivity.actionStart(parent.getContext(), news.getTitle(), news.getUrl());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newsOrder.setText(news.getOrder());
        holder.newsTitle.setText(news.getTitle());
        if (news.getFollow().isEmpty()) { // 调整热度的显示,区分热度、作者的位置
            holder.newsFollow.setVisibility(View.GONE);
        } else if (news.getUrl().indexOf("weibo") != -1) {
            holder.newsFollow.setText(news.getFollow() + "热度");
            holder.newsFollow.setGravity(Gravity.RIGHT);
        } else if (news.getUrl().indexOf("zhihu") != -1) {
            holder.newsFollow.setText(news.getFollow().replace(" ", ""));
            holder.newsFollow.setGravity(Gravity.RIGHT);
        } else {
            holder.newsFollow.setText(news.getFollow());
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
