package com.zngh.platform.front.core.model.util;

import com.zngh.platform.front.core.model.cache.CacheUtil;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Timestamp;

/**
 * 用于获取当前时间，大部分方法未验证
 */
public class TimeUtil {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(TimeUtil.class);
    
    private static final String DEFAULT_LOCAL_STRING = "yyyy-MM-dd HH:mm:ss";

    /**
     * @see 得到当前时刻的时间字符串
     * @return String para的标准时间格式的串,例如：返回'2003-08-09 16:00:00'
     * 已验证
     */
    public static String getLocalString() {
        java.util.Date currentDate = new java.util.Date();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(DEFAULT_LOCAL_STRING);
        String date = dateFormat.format(currentDate);
        return date;
    }

    public static String getLocalString(java.util.Date currentDate) {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(DEFAULT_LOCAL_STRING);
        String date = dateFormat.format(currentDate);
        return date;
    }

    //的到默认的时间格式为(DEFAULT_LOCAL_STRING)的当前时间
    public static String getCurrentDateTime() {
        return getCurrentDateTime(DEFAULT_LOCAL_STRING);
    }

    /*
       根据输入的格式(String _dtFormat)得到当前时间格式
    */
    public static String getCurrentDateTime(String _dtFormat) {
        String currentdatetime = "";
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
            currentdatetime = dtFormat.format(date);
        } catch (Exception e) {
            LOGGER.log(LOGGER.ERROR,"时间格式不正确");
            e.printStackTrace();
        }
        return currentdatetime;
    }

    public static Timestamp getTimestamp(String str) {
        Timestamp ret = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_LOCAL_STRING);

            Date date = dateFormat.parse(str);
            long datelong = date.getTime();
            ret = new Timestamp(datelong);

        } catch (Exception e) {
        }
        return ret;
    }

    public static Timestamp getTimestamp(String str, String _dtFormat) {
        Timestamp ret = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(_dtFormat);

            Date date = dateFormat.parse(str);
            long datelong = date.getTime();
            ret = new Timestamp(datelong);

        } catch (Exception e) {
        }
        return ret;
    }

    public static Timestamp getTimestamp() {
        return getTimestamp(0);
    }

    public static String getTimestampString(Timestamp sta) {
        String ret = "";
        try {
            String str = sta.toString();
            ret = str.substring(0, str.lastIndexOf('.'));
        } catch (Exception e) {
            ret = "";
        }
        return ret;
    }

    /** length 推荐直接用 StaticVariable中的 YearToMonth 和 YearToDay 做参数.
     * YearToMonth: 2007-01
     * YearToDay: 2007-01-30
     * @param sta
     * @param length
     * @return
     */
    public static String getTimestampFormat(Timestamp sta, int length) {
        String ret = "";
        try {
            String str = sta.toString();
            ret = str.substring(0, length);
        } catch (Exception e) {
            ret = "";
        }
        return ret;
    }

    public static Timestamp getTimestamp(int disday) {
        Calendar c;
        c = Calendar.getInstance();
        long realtime = c.getTimeInMillis();
        realtime += 86400000 * disday;
        return new Timestamp(realtime);
    }

    /**
     * @see 得到时间字符串
     * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
     * @param disday int 和当前距离的天数
     * @return String para的标准时间格式的串,例如：返回'2003-8-10 00:00:00'
     */

    public static String getTimeString(int disday) {
        String ls_display = "";
        Calendar c;
        c = Calendar.getInstance();
        long realtime = c.getTimeInMillis();
        realtime += 86400000 * disday;
        c.setTimeInMillis(realtime);
        String _yystr = "", _mmstr = "", _ddstr = "";
        int _yy = c.get(Calendar.YEAR);
        _yystr = _yy + "";
        int _mm = c.get(Calendar.MONTH) + 1;
        _mmstr = _mm + "";
        if (_mm < 10) {
            _mmstr = "0" + _mm;
        }
        int _dd = c.get(Calendar.DATE);
        _ddstr = _dd + "";
        if (_dd < 10) {
            _ddstr = "0" + _dd;
        }
        ls_display = "'" + _yy + "-" + _mm + "-" + _dd + " 00:00:00'";
        return ls_display;
    }

    public static oracle.jbo.domain.Date getCurrentOracleDate() {
        Calendar d = Calendar.getInstance();
        return convertUtilDateToJboDate(d.getTime());
    }

    public static oracle.jbo.domain.Date convertUtilDateToJboDate(java.util.Date value) {
        if (value == null) {
            return null;
        }
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(value.getTime());
        return new oracle.jbo.domain.Date(sqlDate);
    }


    public static void main(String[] args) {

        System.out.println(getCurrentOracleDate());
    }

    //    public static Date getCurrentDateTime(String strDate, String _dtFormat) {
    //        Date tDate = null;
    //
    //        SimpleDateFormat smpDateFormat = new SimpleDateFormat(_dtFormat);
    //        ParsePosition pos = new ParsePosition(0);
    //        tDate = smpDateFormat.parse(strDate, pos); //标准格式的date类型时间
    //
    //        return tDate;
    //    }


}
