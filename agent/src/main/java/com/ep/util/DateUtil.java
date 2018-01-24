package com.ep.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat formatDatetime =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat simpleFormatDatetime = new SimpleDateFormat("yyyy-MM-dd");

    public static synchronized String currentDateTimeStr() {
        return formatDatetime.format(new Date());
    }

    public static synchronized String format2Str(long datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime);
        return formatDatetime.format(cal.getTime());
    }

    public static synchronized String simpleFormat2Str(long datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime);
        return simpleFormatDatetime.format(cal.getTime());
    }

    public static long getNextDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    public static long getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getBeforeDayTime(int beforeDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -beforeDays);
        return calendar.getTimeInMillis();
    }

    public static long getLastDayTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis();
    }
}
