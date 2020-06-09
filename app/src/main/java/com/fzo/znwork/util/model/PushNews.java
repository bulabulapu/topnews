package com.fzo.znwork.util.model;

public class PushNews {

    public final static String SHE_HUI = "shehui";
    public final static String GUO_NEI = "guonei";
    public final static String GUO_JI = "guoji";
    public final static String YU_LE = "yule";
    public final static String TI_YU = "tiyu";
    public final static String JUN_SHI = "junshi";
    public final static String KE_JI = "keji";
    public final static String CAI_JING = "caijing";
    public final static String SHI_SHANG = "shishang"; // 分类
    private String title;
    private String time; // 16:12
    private String category;
    private String author;
    private String url;
    private String pictureUrl; // 图片url

    public PushNews(String title, String time, String category, String author, String url, String pictureUrl) {
        this.author = author;
        this.category = category;
        this.pictureUrl = pictureUrl;
        this.time = time;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public static String getCategory(int i) { // 根据选择list返回相应的category
        switch (i) {
            case 0:
                return SHE_HUI;
            case 1:
                return GUO_NEI;
            case 2:
                return GUO_JI;
            case 3:
                return YU_LE;
            case 4:
                return TI_YU;
            case 5:
                return JUN_SHI;
            case 6:
                return KE_JI;
            case 7:
                return CAI_JING;
            case 8:
                return SHI_SHANG;
        }
        return SHE_HUI;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTime() {
        try {
            return time.split(" ")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
}
