package com.fzo.znwork.util.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.fzo.znwork.NewsApplication;

public class SharedPreferenceUtil {
    private static SharedPreferences preferences = NewsApplication.newsContext
            .getSharedPreferences("news", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = preferences.edit(); // 获取editor

    public static int getFirstHistoryOrder() { // 返回当最新历史记录所在的位置
        return preferences.getInt("history_order", 0);
    }

    public static void setFirstHistoryOrder(int order) {
        editor.putInt("history_order", order);
        editor.apply();
    }

    public static String getSavedPastNewsDate() { // 本地数据库中存储的历史今日的日期
        return preferences.getString("past_date", "00/00");
    }

    public static void setPastNewsDate(String date) {
        editor.putString("past_date", date);
        editor.apply();
    }

    public static String getDateOfReadTime() { // 返回存储的阅读时长的日期,阅读时长24小时重置
        return preferences.getString("read_time_date", "00/00");
    }

    public static void setDateOfReadTime(String date) { // 更改日期
        editor.putString("read_time_date", date);
        editor.apply();
    }

    public static long getReadTime() { // 本地存储的阅读时长
        return preferences.getLong("read_time", 0);
    }

    public static void setReadTime(long time) {
        editor.putLong("read_time", time);
        editor.apply();
    }
}
