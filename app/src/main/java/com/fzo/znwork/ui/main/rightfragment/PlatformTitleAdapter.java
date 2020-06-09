package com.fzo.znwork.ui.main.rightfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fzo.znwork.R;
import com.fzo.znwork.ui.main.NewsActivity;

import java.util.ArrayList;
import java.util.List;

//主界面上方的平台recycler的adapter
class PlatformTitleAdapter extends RecyclerView.Adapter<PlatformTitleAdapter.ViewHolder> {

    private List<String> platformList = new ArrayList<String>() { // 平台名称列表
        {
            add("知乎");
            add("酷安");
            add("澎湃");
            add("少数派");
            add("微博");
            add(""); // 为空优化显示
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView platformTitle;

        ViewHolder(View view) {
            super(view);
            platformTitle = view.findViewById(R.id.platform_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platform_title_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> { // 点击事件,主界面跳转到相应的fragment
            if (RightFragment.newsViewPager != null) {
                RightFragment.newsViewPager.setCurrentItem(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) { // 设置第一个平台标题的字体
            holder.platformTitle.setTextSize(30);
        }
        holder.platformTitle.setText(platformList.get(position));
    }

    @Override
    public int getItemCount() {
        return platformList.size();
    }
}
