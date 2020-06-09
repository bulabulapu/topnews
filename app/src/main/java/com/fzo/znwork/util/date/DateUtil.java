package com.fzo.znwork.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getNowDate() { // 返回当前的月和日,MM/dd格式
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(new Date());
    }
}
