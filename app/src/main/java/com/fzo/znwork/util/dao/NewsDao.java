package com.fzo.znwork.util.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fzo.znwork.NewsApplication;
import com.fzo.znwork.util.model.News;
import com.fzo.znwork.util.model.PastNews;

import java.util.ArrayList;
import java.util.List;

public class NewsDao { // 本地数据的操作接口
    private static DatabaseUtil databaseUtil = new DatabaseUtil(NewsApplication.newsContext, "News.db", null, 1);
    private static SQLiteDatabase newsDatabase = databaseUtil.getReadableDatabase(); // 数据库操作的hander
    private final static int MAX_HISTORY_NUM = 50; // 历史记录最多50条

    public static void getLocalNews(String platform, List<News> newsList) { // 获取本地数据库存储的热点新闻
        newsList.clear();
        Cursor cursor = newsDatabase.query("News", null, "platform=?", new String[]{platform}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News(
                        platform,
                        cursor.getString(cursor.getColumnIndex("ranking")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("follow")),
                        cursor.getString(cursor.getColumnIndex("url"))
                );
                newsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public static void saveNews(String platform, List<News> newsList) { // 向数据库更新热点新闻
        newsDatabase.delete("News", "platform = ?", new String[]{platform});
        ContentValues values = new ContentValues();
        for (News news : newsList) {
            values.put("platform", platform);
            values.put("ranking", news.getOrder());
            values.put("title", news.getTitle());
            values.put("follow", news.getFollow());
            values.put("url", news.getUrl());
            newsDatabase.insert("News", null, values);
            values.clear();
        }
    }

    public static void addHistory(News news) { // 向本地数据库添加历史记录,同时更改最新历史记录的位置
        int order = SharedPreferenceUtil.getFirstHistoryOrder();
        int count = 0;
        Cursor cursor = newsDatabase.rawQuery("select count(*) from History", null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0); // 获取数据库中的记录条数
        }
        cursor.close();
        if (count == MAX_HISTORY_NUM) { // 已经存满MAX_HISTORY_NUM条，就更新掉最早的一条
            if (order == MAX_HISTORY_NUM) {
                order = 0;
            } else {
                order++;
            }
            newsDatabase.execSQL("update History set platform=?,title=?,url=? where ranking=?",
                    new String[]{news.getPlatform(), news.getTitle(), news.getUrl(), Integer.toString(order)});
            SharedPreferenceUtil.setFirstHistoryOrder(order);
        } else { // 直接添加到数据库
            order++;
            newsDatabase.execSQL("insert into History values(?,?,?,?)",
                    new String[]{Integer.toString(order), news.getPlatform(), news.getTitle(), news.getUrl()});
            SharedPreferenceUtil.setFirstHistoryOrder(order);
        }
    }

    public static void getHistory(List<News> newsList) { // 获取历史记录
        newsList.clear();
        int order = SharedPreferenceUtil.getFirstHistoryOrder();
        List<News> tempNewsList = new ArrayList<>();
        Cursor cursor = newsDatabase.rawQuery("select * from History", null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News(
                        cursor.getString(cursor.getColumnIndex("platform")),
                        cursor.getString(cursor.getColumnIndex("ranking")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        "null",
                        cursor.getString(cursor.getColumnIndex("url"))
                );
                tempNewsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (int i = order; i > 0; i--) { // 根据最新历史记录的位置对从数据库中读取出来的数据进行重新排序
            newsList.add(tempNewsList.get(i - 1));
        }
        if (tempNewsList.size() == MAX_HISTORY_NUM) {
            for (int i = MAX_HISTORY_NUM; i > order; i--) {
                newsList.add(tempNewsList.get(i - 1));
            }
        }
    }

    public static void clearHistory() { // 清空历史新闻数据库，同时最新历史记录置0
        SharedPreferenceUtil.setFirstHistoryOrder(0);
        newsDatabase.delete("History", null, null);
    }

    public static void savePastNews(List<PastNews> pastNewsList) { // 存储历史今日的新闻
        newsDatabase.delete("PastNews", null, null);
        ContentValues values = new ContentValues();
        for (PastNews news : pastNewsList) {
            values.put("year", news.getYear());
            values.put("month", news.getMonth());
            values.put("day", news.getDay());
            values.put("title", news.getTitle());
            values.put("describe", news.getDes());
            newsDatabase.insert("PastNews", null, values);
            values.clear();
        }
    }

    public static void getLocalPastNews(List<PastNews> pastNewsList) { // 获取本地存储的历史今日
        pastNewsList.clear();
        Cursor cursor = newsDatabase.rawQuery("select * from PastNews", null);
        if (cursor.moveToFirst()) {
            do {
                PastNews news = new PastNews(
                        cursor.getInt(cursor.getColumnIndex("year")),
                        cursor.getInt(cursor.getColumnIndex("month")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("describe"))
                );
                pastNewsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
