package com.fzo.znwork.ui.past;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fzo.znwork.R;
import com.fzo.znwork.util.model.PastNews;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class NewsPastAdapter extends RecyclerView.Adapter<NewsPastAdapter.ViewHolder> {

    private List<PastNews> pastNewsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearText;
        TextView titleText;

        ViewHolder(View view) {
            super(view);
            yearText = view.findViewById(R.id.past_year);
            titleText = view.findViewById(R.id.past_news_title);
        }
    }

    public NewsPastAdapter(List<PastNews> pastNewsList) {
        this.pastNewsList = pastNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_past_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.baidu.com/s?wd=" + URLEncoder.encode(holder.titleText.getText().toString(), "UTF-8")));
                parent.getContext().startActivity(intent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PastNews pastNews = pastNewsList.get(position);
        holder.yearText.setText(Integer.toString(pastNews.getYear())); // 传入int类型会报错
        holder.titleText.setText(pastNews.getTitle());
    }

    @Override
    public int getItemCount() {
        return pastNewsList.size();
    }
}
