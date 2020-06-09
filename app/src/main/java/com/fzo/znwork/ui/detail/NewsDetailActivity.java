package com.fzo.znwork.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.fzo.znwork.R;
import com.fzo.znwork.util.Repository;

import java.util.Date;

import qiu.niorgai.StatusBarCompat;

public class NewsDetailActivity extends AppCompatActivity { // 新闻详情界面

    private ImageView buttonBack; // 返回按钮
    private AutoRollTextView titleText; // 滚动标题
    private ImageView buttonShare; // 分享按钮
    private String newsTitle;
    private String newsUrl; // 当前新闻的标题和url
    private WebView webView;
    private long startTime = 0; // 开始浏览的时间戳

    //滚动文字控件
    public static class AutoRollTextView extends androidx.appcompat.widget.AppCompatTextView {

        public AutoRollTextView(Context context) {
            super(context);
        }

        public AutoRollTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public AutoRollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean isFocused() {
            return true;
        }
    }

    //外部启动当前活动
    public static void actionStart(Context context, String newsTitle, String newsUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("newsTitle", newsTitle);
        intent.putExtra("newsUrl", newsUrl);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        newsTitle = getIntent().getStringExtra("newsTitle");
        newsUrl = getIntent().getStringExtra("newsUrl");
        buttonBack = findViewById(R.id.button_back);
        titleText = findViewById(R.id.title_text);
        buttonShare = findViewById(R.id.button_share);
        webView = findViewById(R.id.web_view);
        buttonBack.setOnClickListener(v -> finish());
        titleText.setText(newsTitle);
        buttonShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, newsTitle + "\n" + newsUrl);
            startActivity(Intent.createChooser(intent, "分享"));
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                try { // 用于解决知乎的请求头问题
                    String s = request.getUrl().getScheme();
                    if (request.getUrl().getScheme().equals("zhihu")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, request.getUrl()));
                        return false;
                    }
                } catch (Exception e) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.loadUrl(newsUrl);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE); // 设置顶部状态栏颜色
        StatusBarCompat.changeToLightStatusBar(this); // 设置顶部状态栏的style以显示状态栏的文字
    }

    @Override
    protected void onPause() { // 结束浏览,将时间差添加到本地并将开始时间置0
        super.onPause();
        Repository.addReadTime(new Date().getTime() - startTime);
        startTime = 0;
    }

    @Override
    protected void onResume() { // 开始浏览
        super.onResume();
        startTime = new Date().getTime();
    }
}
