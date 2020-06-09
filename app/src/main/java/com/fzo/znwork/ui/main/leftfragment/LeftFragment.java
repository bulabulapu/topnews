package com.fzo.znwork.ui.main.leftfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fzo.znwork.R;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.model.PushNews;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

    private String category = PushNews.SHE_HUI;
    private TextView textView;
    private ImageView imageView; // 头部选择界面
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsPushAdapter adapter;
    private List<PushNews> pushNewsList = new ArrayList<>(); // 新闻列表
    private CardView cardView;
    private ListView listView;
    private String[] choose = {"社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private ArrayAdapter<String> chooseAdapter; // 选择列表

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page_fragment_left, container, false);
        textView = view.findViewById(R.id.push_category);
        imageView = view.findViewById(R.id.push_category_choose);
        refreshLayout = view.findViewById(R.id.push_news_swipe_refresh);
        recyclerView = view.findViewById(R.id.push_news_recycler_view);
        cardView = view.findViewById(R.id.category_choose_card);
        listView = view.findViewById(R.id.category_choose_list_view);
        adapter = new NewsPushAdapter(pushNewsList);
        chooseAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, choose);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> Repository.refreshPushNews(category, pushNewsList, () -> { // 刷新
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }));
        refreshLayout.setRefreshing(true);
        textView.setText(choose[0]);
        Repository.refreshPushNews(category, pushNewsList, () -> { // 初始时进行加载
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });
        imageView.setOnClickListener(v -> { // 打开列表
            cardView.setVisibility(View.VISIBLE);
        });
        listView.setAdapter(chooseAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> { // 选择列表监听
            cardView.setVisibility(View.GONE);
            textView.setText(choose[position]);
            category = PushNews.getCategory(position);
            refreshLayout.setRefreshing(true);
            recyclerView.smoothScrollToPosition(0);
            Repository.refreshPushNews(category, pushNewsList, () -> {
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            });
        });
        view.findViewById(R.id.touch_gone_area).setOnTouchListener((v, event) -> { // 点击其他区域关闭选项列表
            if (cardView.getVisibility() == View.VISIBLE) {
                cardView.setVisibility(View.GONE);
                return true;
            }
            return false;
        });
        return view;
    }
}
