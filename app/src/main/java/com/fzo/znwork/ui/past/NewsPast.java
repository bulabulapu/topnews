package com.fzo.znwork.ui.past;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzo.znwork.R;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.date.DateUtil;
import com.fzo.znwork.util.model.PastNews;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

//历史今日
public class NewsPast extends AppCompatActivity {

    private ImageView buttonBack;
    private TextView pastNewsTitle;
    private SwipeRefreshLayout refreshLayout; // 刷新控件
    private RecyclerView pastRecyclerView;
    private NewsPastAdapter adapter;
    private List<PastNews> pastNewsList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NewsPast.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_past);
        buttonBack = findViewById(R.id.button_back);
        pastNewsTitle = findViewById(R.id.news_past_title);
        refreshLayout = findViewById(R.id.past_news_refresh_layout);
        pastRecyclerView = findViewById(R.id.news_past_recycler_view);
        pastNewsTitle.setText("历史今日•" + DateUtil.getNowDate());
        adapter = new NewsPastAdapter(pastNewsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        pastRecyclerView.setLayoutManager(linearLayoutManager);
        pastRecyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
        Repository.refreshPastNews(pastNewsList, () -> { // 加载数据
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));
        buttonBack.setOnClickListener(v -> finish());
        StatusBarCompat.setStatusBarColor(this, 500122); // 状态栏
        StatusBarCompat.changeToLightStatusBar(this);
    }
}
