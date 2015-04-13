package com.zngh.platform.front.core.model.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import oracle.jbo.JboException;

import oracle.adf.share.logging.ADFLogger;

import oracle.sql.DATE;


/**
 * 时间处理帮助类。
 * 创建dateformat.properties或dateformat_[currentLocale].properties文件，指定需要的时间格式字符串。
 * 将此文件放在你的root classpath目录下。
 * 如果没有创建上述文件，系统将使用默认的时间格式。
 */
public class DateFormatUtils {
    private static final ADFLogger sLog =
        ADFLogger.createADFLogger(DateFormatUtils.class);

    // name of the dateformat(_locale).properties files bundle
    public static final String DATEFORMAT_BUNDLE = "dateformat";

    public static final String DATE_PATTERN_KEY = "datepattern";
    public static final String DATE_TIME_PATTERN_KEY = "datetimepattern";
    public static final String TIME_PATTERN_KEY = "timepattern";

    // The patterns below are default patterns and are only used when
    // the patterns are not present in the resource bundle.
    private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static String DEFAULT_TIME_PATTERN = "HH:mm:ss";


    // This hashmap caches ResourceBundles, keyed by locale.
    private static HashMap dateProperties = new HashMap();

    private static ResourceBundle getProperties(Locale locale) {
        ResourceBundle bundle = (ResourceBundle)dateProperties.get(locale);
        if (bundle == null) {
            sLog.info("Bundle for locale " + locale.getCountry() +
                      locale.hashCode() + " not yet in cache");
            bundle = ResourceBundle.getBundle(DATEFORMAT_BUNDLE, locale);
            sLog.info("Storing bundle in cache for locale: " +
                      locale.getCountry() + locale.hashCode());
            dateProperties.put(locale, bundle);
        } else {
            sLog.info("Using cached bundle");
        }
        return bundle;
    }

    public static String getDatePattern(Locale locale) {
        try {
            return getProperties(locale).getString(DATE_PATTERN_KEY);
        } catch (MissingResourceException ex) {
            // If it is not specified, return the (hardcoded) default
            sLog.info("Date pattern not present, using hardcoded default");
            return DEFAULT_DATE_PATTERN;
        }
    }

    public static String getDateTimePattern(Locale locale) {
        try {
            return getProperties(locale).getString(DATE_TIME_PATTERN_KEY);
        } catch (MissingResourceException ex) {
            // If it is not specified, return the (hardcoded) default
            sLog.info("Datetime pattern not present, using hardcoded default");
            return DEFAULT_DATE_TIME_PATTERN;
        }
    }

    public static String getTimePattern(Locale locale) {
        try {
            return getProperties(locale).getString(TIME_PATTERN_KEY);
        } catch (MissingResourceException ex) {
            // If it is not specified, return the (hardcoded) default
            sLog.info("Time pattern not present, using hardcoded default");
            return DEFAULT_TIME_PATTERN;
        }
    }

    /**
     * Create Date from inputString
     * @param pattern the pattern for conversion
     * @param locale the currentLocale for conversion
     * @param dateString the input date as string
     * @return java.util.Date
     */
    public static java.util.Date parseUtilDate(String dateString,
                                               Locale locale, String pattern) {
        if (dateString == null) {
            return null;
        }
        DateFormat df;
        df = new SimpleDateFormat(pattern, locale);
        java.util.Date parsedDate = null;
        try {
            parsedDate = df.parse(dateString);
        } catch (ParseException e) {
            sLog.info("Invalid dateString " + dateString);
        }
        return parsedDate;
    }

    public static oracle.jbo.domain.Date parseJboDate(String dateString,
                                                      Locale locale,
                                                      String pattern) {
        return convertUtilDateToJboDate(parseUtilDate(dateString, locale,
                                                      pattern));
    }


    public static String formatDateToString(java.util.Date date,
                                            Locale locale) {
        return formatDateToString(date, locale, getDatePattern(locale));
    }

    public static String formatDateToString(oracle.jbo.domain.Date date,
                                            Locale locale,
                                            String displayFormat) {
        return formatDateToString(convertJboToUtil(date), locale,
                                  displayFormat);
    }

    public static String formatDateToString(java.util.Date date, Locale locale,
                                            String displayFormat) {
        if (date == null) {
            return null;
        }
        DateFormat df;
        df = new SimpleDateFormat(displayFormat, locale);
        return df.format(date);
    }


    public static oracle.jbo.domain.Date mergeDateAndTime(oracle.jbo.domain.Date date,
                                                          String time) {
        return mergeDateAndTime(date, time,
                                getTimePattern(Locale.getDefault()));
    }

    public static oracle.jbo.domain.Date mergeDateAndTime(oracle.jbo.domain.Date date,
                                                          String time,
                                                          String timeFormat) throws JboException {
        String dateConversionFormat = getDatePattern(Locale.getDefault());
        SimpleDateFormat dateFormat =
            new SimpleDateFormat(dateConversionFormat);
        SimpleDateFormat datetimeFormat =
            new SimpleDateFormat(dateConversionFormat + " " + timeFormat);
        String hiredate = dateFormat.format(date.dateValue());
        try {
            java.util.Date utilDate =
                datetimeFormat.parse(hiredate + " " + time);
            java.sql.Timestamp sqlDate =
                new java.sql.Timestamp(utilDate.getTime());
            return new oracle.jbo.domain.Date(sqlDate);
        } catch (ParseException ep) {
            throw new JboException("Invalid time, must be " + timeFormat);
        }
    }

    public static oracle.jbo.domain.Date convertUtilDateToJboDate(java.util.Date value) {
        if (value == null) {
            return null;
        }
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(value.getTime());
        return new oracle.jbo.domain.Date(sqlDate);
    }

    public static java.util.Date convertJboToUtil(oracle.jbo.domain.Date odate) {
        java.util.Date juDate = null;
        if (odate != null) {
            juDate = new java.util.Date();
            juDate.setTime(odate.getValue().getTime());
        }
        return juDate;
    }

    public static oracle.jbo.domain.Date getJboCurrentDate() {
        return new oracle.jbo.domain.Date(oracle.jbo.domain.Date.getCurrentDate());
    }

    public static oracle.jbo.domain.Date getJboCurrentDateTime() {
        java.sql.Timestamp ts =
            new java.sql.Timestamp(System.currentTimeMillis());
        oracle.jbo.domain.Date currentDateTime =
            new oracle.jbo.domain.Date(ts);
        return currentDateTime;
    }

    public static void main(String[] args) {
        /* System.out.println("当前时间：" + getJboCurrentDate());
        System.out.println("当前时间戳：" + getJboCurrentDateTime());
        System.out.println("格式化：" + formatDateToString(new java.util.Date(), Locale.getDefault()));
        System.out.println("ddd: " + formatDateToString(getJboCurrentDateTime(), Locale.getDefault(), "yyyy-MM-dd HH:mm:ss")); */
        oracle.jbo.domain.Date jboDate = DateFormatUtils.getJboCurrentDate();
        System.out.println("当前时间:" + jboDate);
        //第一个参数是天，第二个参数是秒
        jboDate = (oracle.jbo.domain.Date)jboDate.addJulianDays(1, 0);
        System.out.println("加一天时间:" + jboDate);
        jboDate = (oracle.jbo.domain.Date)jboDate.addMonths(1);
        System.out.println("加一个月:" + jboDate);
    }
}
