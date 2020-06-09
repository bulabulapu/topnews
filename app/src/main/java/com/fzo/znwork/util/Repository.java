package com.fzo.znwork.util;

import android.widget.Toast;

import com.fzo.znwork.NewsApplication;
import com.fzo.znwork.util.dao.NewsDao;
import com.fzo.znwork.util.dao.SharedPreferenceUtil;
import com.fzo.znwork.util.date.DateUtil;
import com.fzo.znwork.util.model.News;
import com.fzo.znwork.util.model.PastNews;
import com.fzo.znwork.util.model.PushNews;
import com.fzo.znwork.util.net.ServiceCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository { // 数据请求的接口,包括网络和本地

    public static boolean isSavedNews(String platform) { // 本地数据库是否有热点新闻
        List<News> newsList = new ArrayList<>();
        NewsDao.getLocalNews(platform, newsList);
        if (newsList.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void getLocalNews(String platform, List<News> newsList) { // 获取本地数据库的热点新闻
        NewsDao.getLocalNews(platform, newsList);
    }

    public static void refreshNews(String platform, List<News> list, Runnable run) { // 网络请求热点新闻
        ServiceCreator.newsService.getNews(1, News.findCount(platform), platform).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> newsList = response.body();
                if (newsList == null) { // 判断返回的json是否错误
                    Toast.makeText(NewsApplication.newsContext, "服务器异常", Toast.LENGTH_SHORT).show();
                } else {
                    NewsDao.saveNews(platform, newsList);
                }
                if (list != null) {
                    NewsDao.getLocalNews(platform, list);
                    run.run(); // 请求完成后停止刷新，并更改显示数据
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                t.printStackTrace();
                run.run();
                Toast.makeText(NewsApplication.newsContext, "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void refreshPastNews(List<PastNews> pastNewsList, Runnable runnable) { // 获取历史今日
        if (!DateUtil.getNowDate().equals(SharedPreferenceUtil.getSavedPastNewsDate())) { // 本地数据库存储的历史今日与日期不符，从服务器请求
            String[] date = DateUtil.getNowDate().split("/");
            ServiceCreator.newsService.getPastNews(date[0], date[1]).enqueue(new Callback<List<PastNews>>() {
                @Override
                public void onResponse(Call<List<PastNews>> call, Response<List<PastNews>> response) {
                    List<PastNews> newsList = response.body();
                    if (newsList == null) {
                        Toast.makeText(NewsApplication.newsContext, "服务器异常", Toast.LENGTH_SHORT).show();
                    } else {
                        NewsDao.savePastNews(newsList);
                        SharedPreferenceUtil.setPastNewsDate(date[0] + "/" + date[1]); // 更新本地存储的历史今日的日期
                        if (pastNewsList != null) {
                            NewsDao.getLocalPastNews(pastNewsList);
                            runnable.run(); // 停止刷新,更改显示
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<PastNews>> call, Throwable t) {
                    t.printStackTrace();
                    if (pastNewsList != null) {
                        NewsDao.getLocalPastNews(pastNewsList);
                        runnable.run();
                    }
                    Toast.makeText(NewsApplication.newsContext, "刷新失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else { // 直接从本地数据库取数据
            if (pastNewsList != null) {
                NewsDao.getLocalPastNews(pastNewsList);
                runnable.run();
            }
        }
    }

    public static void refreshPushNews(String category, List<PushNews> list, Runnable run) { // 请求推送新闻
        ServiceCreator.newsService.getPushNews(category).enqueue(new Callback<List<PushNews>>() {
            @Override
            public void onResponse(Call<List<PushNews>> call, Response<List<PushNews>> response) {
                List<PushNews> pushNewsList = response.body();
                if (pushNewsList == null) {
                    Toast.makeText(NewsApplication.newsContext, "服务器异常", Toast.LENGTH_SHORT).show();
                } else {
                    list.clear();
                    for (PushNews news : pushNewsList) {
                        list.add(news);
                    }
                    run.run();
                }
            }

            @Override
            public void onFailure(Call<List<PushNews>> call, Throwable t) {
                t.printStackTrace();
                run.run();
                Toast.makeText(NewsApplication.newsContext, "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addHistory(News news) { // 添加历史记录
        NewsDao.addHistory(news);
    }

    public static void getHistory(List<News> newsList) { // 获取历史记录
        NewsDao.getHistory(newsList);
    }

    public static void clearHistory() { // 清空历史记录
        NewsDao.clearHistory();
    }

    public static void addReadTime(long addTime) { // 将当前活动的浏览时长添加到数据库
        if (!DateUtil.getNowDate().equals(SharedPreferenceUtil.getDateOfReadTime())) { // 日期更新,将时长置0
            SharedPreferenceUtil.setReadTime(0);
            SharedPreferenceUtil.setDateOfReadTime(DateUtil.getNowDate()); // 更新日期
        }
        SharedPreferenceUtil.setReadTime(addTime + SharedPreferenceUtil.getReadTime());
    }

    public static String getReadTime() { // 获取浏览时长
        if (DateUtil.getNowDate().equals(SharedPreferenceUtil.getDateOfReadTime())) { // 检查数据库中的数据是否为当前日期的
            long time = SharedPreferenceUtil.getReadTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:m:s");
            String[] resultTimes = simpleDateFormat.format(new Date(time)).split(":");
            String result = "";
            resultTimes[0] = String.valueOf(Integer.parseInt(resultTimes[0]) - 8); // 时区问题
            if (!resultTimes[0].equals("0")) {
                result = resultTimes[0] + "小时";
            }
            if (!resultTimes[1].equals("0")) {
                result += resultTimes[1] + "分钟";
            }
            if (result.isEmpty()) { // 不足1分钟时
                result = "0分钟";
            }
            return result;
        }
        return "0分钟";
    }
}
