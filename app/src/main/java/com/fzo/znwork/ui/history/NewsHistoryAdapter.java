package com.fzo.znwork.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fzo.znwork.R;
import com.fzo.znwork.ui.detail.NewsDetailActivity;
import com.fzo.znwork.util.model.News;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// 历史记录的recycler的adapter
public class NewsHistoryAdapter extends RecyclerView.Adapter<NewsHistoryAdapter.ViewHolder> {

    private List<News> newsList; // 显示的数据

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView; // 每个item的图标
        TextView textView; // 新闻的title

        ViewHolder(View view) {
            super(view);
            circleImageView = view.findViewById(R.id.circle_platform_logo);
            textView = view.findViewById(R.id.news_title);
        }
    }

    public NewsHistoryAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_history_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> { // 点击item后跳转到新闻详情
            News news = newsList.get(holder.getAdapterPosition());
            NewsDetailActivity.actionStart(parent.getContext(), news.getTitle(), news.getUrl());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        int imageId = 0; // 根据当前新闻的platform设置图标
        String platform = news.getPlatform();
        if (platform.equals(News.KU_AN)) {
            imageId = R.drawable.kuan_192x192;
        } else if (platform.equals(News.PENG_PAI)) {
            imageId = R.drawable.pengpai_160x160;
        } else if (platform.equals(News.SHAO_SHU_PAI)) {
            imageId = R.drawable.shaoshupai_160x160;
        } else if (platform.equals(News.WEI_BO)) {
            imageId = R.drawable.weibo_160x160;
        } else {
            imageId = R.drawable.zhihu_160x160;
        }
        holder.circleImageView.setImageResource(imageId);
        holder.textView.setText(news.getTitle()); // 新闻title
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}
