package com.fzo.znwork.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.fzo.znwork.R;
import com.fzo.znwork.ui.main.leftfragment.LeftFragment;
import com.fzo.znwork.ui.main.rightfragment.RightFragment;

import java.util.ArrayList;
import java.util.List;

//主界面
public class NewsActivity extends AppCompatActivity {

    private List<Fragment> mainPageFragmentList = new ArrayList<>();
    private ViewPager mainPageViewPager;
    private NewsFragmentPagerAdapter mainPageFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mainPageViewPager = findViewById(R.id.main_page_view_pager);
        mainPageFragmentList.add(new LeftFragment());
        mainPageFragmentList.add(new RightFragment()); // 左右fragment
        mainPageFragmentPagerAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager(), mainPageFragmentList);
        mainPageViewPager.setAdapter(mainPageFragmentPagerAdapter);
        mainPageViewPager.setCurrentItem(1); // 启动时界面
    }
}
