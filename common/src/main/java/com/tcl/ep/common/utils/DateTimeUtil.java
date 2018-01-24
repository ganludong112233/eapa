package com.tcl.ep.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static String FORMATTER_1 = "yyyy-MM-dd HH:mm:ss";
    public static String FORMATTER_2 = "yyyy-MM-dd";
    public static final long INVALID_DATE = 0L;

    private final static Logger LOG = LoggerFactory.getLogger(DateTimeUtil.class);

    private static final DateTimeFormatter dateFormatter =
        DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final DateTimeFormatter dateFormatterWithZone =
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter utcDateFormatter =
        DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC);

    public static final DateTimeFormatter ES_FORMATTER =
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);


    private static final DateTimeFormatter utcDateFormatter2 =
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);


    public static String toEsTimeString(long mills) {
        return ES_FORMATTER.print(mills);
    }

    public static long getCurrentMillsOfUTC() {
        DateTime dateTime = new DateTime(new Date());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getMillis();
    }

    public static int getCurrentYear() {
        DateTime dateTime = new DateTime(new Date());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getYear();
    }

    public static int getCurrentMonth() {
        DateTime dateTime = new DateTime(new Date());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getMonthOfYear();
    }

    public static int getCurrentDay() {
        DateTime dateTime = new DateTime(new Date());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getDayOfMonth();
    }

    public static Calendar getCurrentTimeOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取正负天数的UTC凌晨时间
     *
     * @param day：天数
     * @return :UTC时间
     */
    public static long getCalenderMillsofUTC(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        DateTime dateTime = new DateTime(calendar.getTime());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getMillis();
    }

    /**
     * 返回周一日期
     *
     * @return yyyy-MM-dd
     */
    public static String getWeekDay(int week) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATTER_2);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -week * 7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 返回某天日期
     *
     * @param day 天数
     * @return yyyy-MM-dd
     */
    public static String getDay(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATTER_2);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前时间正负天时间戳
     *
     * @param day 天数
     * @return 毫秒
     */
    public static long getCalenderDayMillsofUTC(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);
        DateTime dateTime = new DateTime(calendar.getTime());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getMillis();
    }



    /**
     * 获取正负小时的UTC时间戳
     *
     * @param hour ：小时
     * @return :UTC 毫秒
     */
    public static long getCalenderHourMillsofUTC(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        DateTime dateTime = new DateTime(calendar.getTime());
        DateTime utcDateTime = dateTime.withZone(DateTimeZone.UTC);
        return utcDateTime.getMillis();
    }

    public static DateTime fromDateString(String date) {
        return dateFormatter.parseDateTime(date);
    }

    /**
     * 字符串时间天数加1
     *
     * @param date
     * @return
     */
    public static String plusDateString(String date) {
        DateTime dateTime = fromDateString(date);
        dateTime = dateTime.plusDays(1);
        return fomartDateToStr("yyyy-MM-dd", dateTime.toDate());
    }

    /**
     * 时间转换为字符串
     *
     * @param format ：格式方式
     * @param date   ：时间
     * @return
     */
    public static String fomartDateToStr(String format, Date date) {
        String dateString = null;
        try {
            if (null != date) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                dateString = formatter.format(date);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return dateString;
    }

    /**
     * 时间转换为字符串
     *
     * @param format ：格式方式
     * @param mills  ：时间
     * @return
     */
    public static String fomartDateToStrUtc(String format, long mills) {
        return DateTimeFormat.forPattern(format).withZone(DateTimeZone.UTC).print(mills);
    }

    /**
     * 获取UTC时间
     *
     * @param mills
     * @return
     */
    public static String toUtcTimeString(long mills) {
        return utcDateFormatter.print(mills);
    }

    /**
     * 获取UTC Date(yyyy-MM-dd)
     *
     * @param mills
     * @return
     */
    public static Date toUtcDateMill(long mills) {
        return fromDateString(toUtcTimeString(mills)).toDate();
    }

    /**
     * 获取UTC时间
     *
     * @param mills
     * @return(yyyy-MM-dd HH:mm:ss)
     */
    public static String toUtcTimeBySec(long mills) {
        return utcDateFormatter2.print(mills);
    }

    /**
     * 转换成时间戳（UTC）
     *
     * @param dateTime（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static long getMillsFromUTC(String dateTime) {
        return ES_FORMATTER.parseDateTime(dateTime).getMillis();
    }

    /**
     * 转换成时间戳(带时区时间)
     *
     * @param dateTime（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static long getMillsWithZone(String dateTime) {
        return dateFormatterWithZone.parseDateTime(dateTime).getMillis();
    }
    public static long checkDate(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return INVALID_DATE;
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return INVALID_DATE;
    }
    /**
     * 格式化时间
     * 
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 格式化时间
     * 
     * @param date 日期
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (date == null) {
            date = new Date();
        }

        return sdf.format(date);
    }


}
