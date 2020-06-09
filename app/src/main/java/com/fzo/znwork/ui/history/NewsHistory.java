package com.fzo.znwork.ui.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.fzo.znwork.R;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.model.News;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

public class NewsHistory extends AppCompatActivity {

    private ImageView buttonBack;
    private RecyclerView historyRecyclerView;
    private NewsHistoryAdapter adapter;
    private List<News> newsList = new ArrayList<>(); // 显示的历史记录的数据
    private ImageView buttonClear;

    // 外部启动的接口
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NewsHistory.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_history);
        buttonBack = findViewById(R.id.button_back);
        historyRecyclerView = findViewById(R.id.news_history_recycler_view);
        buttonClear = findViewById(R.id.button_clear);
        adapter = new NewsHistoryAdapter(newsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(linearLayoutManager);
        historyRecyclerView.setAdapter(adapter);
        Repository.getHistory(newsList);
        adapter.notifyDataSetChanged();
        buttonBack.setOnClickListener(v -> finish());
        buttonClear.setOnClickListener(v -> {
            Repository.clearHistory();
            newsList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "已清空", Toast.LENGTH_SHORT).show();
        });
        StatusBarCompat.setStatusBarColor(this, 500122); // 设置状态栏
        StatusBarCompat.changeToLightStatusBar(this);
    }
}
