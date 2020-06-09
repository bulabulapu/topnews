package com.fzo.znwork.util.model;

public class News {
    public final static String KU_AN = "kuan";
    public final static String PENG_PAI = "pengpai";
    public final static String WEI_BO = "weibo";
    public final static String SHAO_SHU_PAI = "shaoshupai";
    public final static String ZHI_HU = "zhihu"; // 平台
    private String platform;// 平台
    private String order;// 热点新闻的次序
    private String title;// 新闻标题
    private String follow;// 新闻的热度或者作者
    private String url;// 新闻url

    public News(String platform, String order, String title, String follow, String url) {
        this.platform = platform;
        this.order = order;
        this.title = title;
        this.follow = follow;
        this.url = url;
    }

    public String getPlatform() {
        return platform;
    }

    public String getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getFollow() {
        return follow;
    }

    public String getUrl() {
        return url;
    }

    public static int findCount(String platform) {
        if (platform.equals(KU_AN)) {
            return 20;
        } else if (platform.equals(PENG_PAI)) {
            return 10;
        } else if (platform.equals(WEI_BO)) {
            return 50;
        } else if (platform.equals(SHAO_SHU_PAI)) {
            return 20;
        } else if (platform.equals(ZHI_HU)) {
            return 50;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "News [order=" + order + ", title=" + title + ", follow=" + follow + ", url=" + url + "]";
    }
}
