package com.fzo.znwork.util.model;

public class PastNews { // 历史今日新闻
    private int year;
    private int month;
    private int day;
    private String title;
    private String des; // 详情describe

    public PastNews(int year, int month, int day, String title, String des) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.title = title;
        this.des = des;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getDes() {
        return des;
    }
}
