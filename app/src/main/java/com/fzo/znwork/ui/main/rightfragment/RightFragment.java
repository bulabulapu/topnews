package com.fzo.znwork.ui.main.rightfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fzo.znwork.R;
import com.fzo.znwork.ui.history.NewsHistory;
import com.fzo.znwork.ui.main.NewsFragmentPagerAdapter;
import com.fzo.znwork.ui.main.rightfragment.newsfragment.NewsFragment;
import com.fzo.znwork.ui.past.NewsPast;
import com.fzo.znwork.util.Repository;
import com.fzo.znwork.util.model.News;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import qiu.niorgai.StatusBarCompat;

//主新闻界面
public class RightFragment extends Fragment {

    private CircleImageView circleImageView;
    private RecyclerView platformTitleRecyclerView;
    LinearLayoutManager linearLayoutManager;
    private PlatformTitleAdapter platformTitleAdapter;
    private RecyclerView.ViewHolder viewHolder; // 主界面上方的控件
    private List<Fragment> newsFragmentList = new ArrayList<>();
    public static ViewPager newsViewPager;
    private NewsFragmentPagerAdapter newsFragmentPagerAdapter; // 主界面显示新闻的控件
    private int nowPlatform = 0; // 当前平台标识
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton timeButton;
    private FloatingActionButton historyButton;
    private FloatingActionButton pastButton; // 浮动按钮及菜单

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page_fragment_right, container, false);
        circleImageView = view.findViewById(R.id.circle_platform_logo);
        platformTitleRecyclerView = view.findViewById(R.id.platform_title_recycler_view);
        platformTitleAdapter = new PlatformTitleAdapter();
        newsViewPager = view.findViewById(R.id.view_pager);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        platformTitleRecyclerView.setLayoutManager(linearLayoutManager);
        platformTitleRecyclerView.setAdapter(platformTitleAdapter);
        floatingActionMenu = view.findViewById(R.id.fab_menu);
        timeButton = view.findViewById(R.id.fab_time);
        historyButton = view.findViewById(R.id.fab_history);
        pastButton = view.findViewById(R.id.fab_past);
        newsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // 添加界面切换的监听器
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int offset = position - nowPlatform;
                if (offset != 0) {
                    platformTitleRecyclerView.smoothScrollBy(offset * 450, 0); // 滑动顶部的title
                    switch (position) { // 更改图标
                        case 0:
                            circleImageView.setImageResource(R.drawable.zhihu_160x160);
                            break;
                        case 1:
                            circleImageView.setImageResource(R.drawable.kuan_192x192);
                            break;
                        case 2:
                            circleImageView.setImageResource(R.drawable.pengpai_160x160);
                            break;
                        case 3:
                            circleImageView.setImageResource(R.drawable.shaoshupai_160x160);
                            break;
                        case 4:
                            circleImageView.setImageResource(R.drawable.weibo_160x160);
                            break;
                        default:
                            break;
                    }
                    setPlatformTitleSize(nowPlatform, 20); // 更改上一个界面的title字体
                }
                setPlatformTitleSize(position, 30); // 当前界面
                nowPlatform = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        newsFragmentList.add(new NewsFragment(News.ZHI_HU));
        newsFragmentList.add(new NewsFragment(News.KU_AN));
        newsFragmentList.add(new NewsFragment(News.PENG_PAI));
        newsFragmentList.add(new NewsFragment(News.SHAO_SHU_PAI));
        newsFragmentList.add(new NewsFragment(News.WEI_BO));
        newsFragmentPagerAdapter = new NewsFragmentPagerAdapter(getActivity().getSupportFragmentManager(), newsFragmentList);
        newsViewPager.setAdapter(newsFragmentPagerAdapter);
        newsViewPager.setCurrentItem(0); // 初始默认在第0个fragment
        floatingActionMenu.setClosedOnTouchOutside(true); // 设置点击空白浮动按钮菜单自动关闭
        timeButton.setOnClickListener(v -> { // 为浮动按钮添加监听
            floatingActionMenu.close(true);
            Snackbar.make(v
                    , "今日浏览了" + Repository.getReadTime()
                    , Snackbar.LENGTH_LONG).show();
        });
        historyButton.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            NewsHistory.actionStart(getContext());
        });
        pastButton.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            NewsPast.actionStart(getContext());
        });
        StatusBarCompat.setStatusBarColor(getActivity(), 500122); // 状态栏
        StatusBarCompat.changeToLightStatusBar(getActivity());
        return view;
    }

    private void setPlatformTitleSize(final int position, float size) { // 更改顶部title的字体
        viewHolder = platformTitleRecyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null && viewHolder instanceof PlatformTitleAdapter.ViewHolder) {
            ((PlatformTitleAdapter.ViewHolder) viewHolder).platformTitle.setTextSize(size);
        } else { //下一个title还没有滑出来,获取不到viewHolder,延时获取
            new Thread(() -> {
                try {
                    Thread.currentThread().sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                viewHolder = platformTitleRecyclerView.findViewHolderForAdapterPosition(position);
                getActivity().runOnUiThread(() -> {
                    if (viewHolder != null && viewHolder instanceof PlatformTitleAdapter.ViewHolder) {
                        ((PlatformTitleAdapter.ViewHolder) viewHolder).platformTitle.setTextSize(size);
                    }
                });
            }).start();
        }
    }
}
