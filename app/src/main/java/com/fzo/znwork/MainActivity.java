package com.fzo.znwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.fzo.znwork.ui.main.NewsActivity;

public class MainActivity extends AppCompatActivity {

    private boolean flag = true; // 用于防止在sleep时退出程序后，依然进行了跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main); // 启动时的性能优化,采用设置theme的方法
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
        goOn();
    }

    private void goOn() { // 开屏界面sleep500ms后启动NewsActivity
        new Thread(() -> {
            Intent intent = new Intent(this, NewsActivity.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (flag) { // 检查当前activity是否被关闭
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag = false;
    }
}
