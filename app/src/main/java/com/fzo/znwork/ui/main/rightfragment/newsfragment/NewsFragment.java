package com.fzo.znwork.ui.main.rightfragment.newsfragment;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fzo.znwork.NewsApplication;
import com.fzo.znwork.R;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.model.News;

import java.util.ArrayList;
import java.util.List;

//主界面新闻展示的fragment
public class NewsFragment extends Fragment {

    private String platform; // 当前fragment的平台
    private SwipeRefreshLayout refreshLayout; // 下拉刷新布局
    private RecyclerView newsRecyclerView; // 列表布局
    private NewsAdapter adapter; // 列表的adapter
    private List<News> newsList = new ArrayList<>(); // 数据的list
    private MediaPlayer mediaPlayer = new MediaPlayer(); // 媒体播放

    public NewsFragment(String platform) {
        super();
        this.platform = platform;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        newsRecyclerView = view.findViewById(R.id.news_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> { // 刷新数据
                    Repository.refreshNews(platform, newsList, () -> {
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                        mediaPlayer.start(); // 播放音效
                    });
                }
        );
        refreshLayout.setRefreshing(true);
        if (Repository.isSavedNews(platform)) { // 检查本地数据库是否有当前平台数据
            Repository.getLocalNews(platform, newsList);
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        } else { // 网络请求
            Repository.refreshNews(platform, newsList, () -> {
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            });
        }
        initMediaPlayer();
        return view;
    }

    private void initMediaPlayer() { // 初始化媒体播放组件
        try {
            AssetFileDescriptor fd = NewsApplication.newsContext.getAssets().openFd("tip.mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
