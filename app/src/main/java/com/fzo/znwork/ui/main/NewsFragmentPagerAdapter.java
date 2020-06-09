package com.fzo.znwork.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

//主界面和右边碎片的viewpager的adapter,管理fragment
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> newsFragmentList;

    public NewsFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> newsFragmentList) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.newsFragmentList = newsFragmentList;
    }

    @Override
    public int getCount() {
        return newsFragmentList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return newsFragmentList.get(position);
    }
}
