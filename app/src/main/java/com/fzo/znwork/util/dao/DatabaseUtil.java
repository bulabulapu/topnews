package com.fzo.znwork.util.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseUtil extends SQLiteOpenHelper { // 数据库的util

    /* 热点新闻表 */
    private static final String CREATE_TABLE_NEWS = "create table News("
            + "platform text,"
            + "ranking text,"
            + "title text,"
            + "follow text,"
            + "url text);";
    /*浏览历史记录表 */
    private static final String CREATE_TABLE_HISTORY = "create table History("
            + "ranking text,"
            + "platform text,"
            + "title text,"
            + "url text);";
    /*历史今日表 */        
    private static final String CREATE_TABLE_PAST_NEWS = "create table PastNews("
            + "year integer,"
            + "month integer,"
            + "day integer,"
            + "title text,"
            + "describe text);";

    DatabaseUtil(@Nullable Context context, @Nullable String dbName, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 创建时建表
        db.execSQL(CREATE_TABLE_NEWS);
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_PAST_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
