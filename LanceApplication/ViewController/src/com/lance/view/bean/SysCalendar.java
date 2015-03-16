package com.lance.view.bean;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class SysCalendar {
    public SysCalendar() {
        super();
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 4);
        Date date = c.getTime();
        System.out.println(sdf.format(date));
        System.out.println(SysCalendar.getWeekOfDate(date));
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
