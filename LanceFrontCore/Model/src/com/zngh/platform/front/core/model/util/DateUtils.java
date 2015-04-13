package com.zngh.platform.front.core.model.util;

import java.util.Calendar;
import java.util.Locale;
import oracle.jbo.domain.Date;

public class DateUtils {
    public DateUtils() {
        super();
    }

    public static int getYear(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int getLastDateOfMonth(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 两个日期之前相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getIntevalDays(java.util.Date startDate,
                                      java.util.Date endDate) {
        java.util.Calendar startCalendar = java.util.Calendar.getInstance();
        java.util.Calendar endCalendar = java.util.Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        long diff =
            endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        return (diff / (1000 * 60 * 60 * 24));
    }
    
    /**
     * 两个日期之前相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getIntevalDays(oracle.jbo.domain.Date startDate,
                                      oracle.jbo.domain.Date endDate) {
        java.util.Date utilStartDate = DateFormatUtils.convertJboToUtil(startDate);
        java.util.Date utilEndDate = DateFormatUtils.convertJboToUtil(endDate);
        return getIntevalDays(utilStartDate, utilEndDate);
    }
    
    /**
     * 指定的两个日期是否在指定的月数之内
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param months 约束
     * @return true or false
     */
    public static boolean isDateInRangeMonth(java.util.Date startDate, java.util.Date endDate, int months) {
        Calendar tempCalendar = java.util.Calendar.getInstance();
        tempCalendar.setTime(startDate);
        tempCalendar.add(Calendar.MONTH, months);
        long tempDiffDays = getIntevalDays(startDate, tempCalendar.getTime());
        long diffDays = Math.abs(getIntevalDays(startDate, endDate));
        if(diffDays <= tempDiffDays) {
            return true;
        }
        return false;
    }
    
    /**
     * 指定的两个日期是否在指定的月数之内
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param months 约束
     * @return true or false
     */
    public static boolean isDateInRangeMonth(oracle.jbo.domain.Date startDate, oracle.jbo.domain.Date endDate, int months) {
        java.util.Date utilStartDate = DateFormatUtils.convertJboToUtil(startDate);
        java.util.Date utilEndDate = DateFormatUtils.convertJboToUtil(endDate);
        return isDateInRangeMonth(utilStartDate, utilEndDate, months);
    }
    
    
    
    public static void main(String[] args) {
        System.out.println("month = " + getMonth(new java.util.Date()));
        System.out.println("year = " + getYear(new java.util.Date()));
        System.out.println("day of month = " +
                           getDayOfMonth(new java.util.Date()));
        java.util.Date startDate = 
            DateFormatUtils.parseUtilDate("2012-04-11", Locale.getDefault(), "yyyy-MM-dd");
        java.util.Date endDate = 
            DateFormatUtils.parseUtilDate("2012-02-01", Locale.getDefault(), "yyyy-MM-dd");
        
        oracle.jbo.domain.Date jbostartDate = 
            DateFormatUtils.parseJboDate("2012-03-11", Locale.getDefault(), "yyyy-MM-dd");
        oracle.jbo.domain.Date jboendDate = 
            DateFormatUtils.parseJboDate("2012-02-01", Locale.getDefault(), "yyyy-MM-dd");
        
        System.out.println("getIntevalDays = " + getIntevalDays(startDate, endDate));
        
        System.out.println("getLastDateOfMonth = " + getLastDateOfMonth(new java.util.Date()));
        System.out.println("isDateInRangeMonth = " + isDateInRangeMonth(jbostartDate, jboendDate, 2));
    }
}
