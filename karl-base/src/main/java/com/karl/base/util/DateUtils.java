/**
 * 元年软件
 *
 * @author xiaohuzi
 * @date 2018年4月17日 下午3:02:44
 * @version V1.0
 */
package com.karl.base.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public final class DateUtils {

    /**
     * 年月日 yyyy-MM-dd
     */
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * 精确到秒 yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 精确到秒 yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECONDS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_MSEC = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 精确到毫秒 yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_M = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 精确到秒T yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String FORMAT_YEAR_MONTH_DAY_T_DAY_HOUR_MINUTE_SECONDS = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 精确到秒T yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final String FORMAT_YEAR_MONTH_DAY_T_HOUR_MINUTE_MSEC = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * 精确到秒T yyyy-MM-dd'T'HH:mm
     */
    public static final String FORMAT_YEAR_MONTH_DAY_T_DAY_HOUR_MINUTE = "yyyy-MM-dd'T'HH:mm";

    public static final String FORMAT_YEAR_MONTH = "yyyyMM";

    public static final String FORMAT_YEAR_MONTH_NONA = "yyyy-MM";


    /**
     * 日期格式数组
     * <br>yyyy-MM-dd HH:mm
     * <br>yyyy-MM-dd HH:mm:ss
     * <br>yyyy-MM-dd HH:mm:ss.SSS
     * <br>yyyy-MM-ddThh:mm:ss
     * <br>yyyy-MM-ddThh:mm:ss.SSS
     * <br>yyyy-MM-ddTHH:mm
     * <br>yyyy-MM-dd
     */
    public static final String[] DATE_FORMATS = {FORMAT_YEAR_MONTH_DAY_T_HOUR_MINUTE_MSEC,
            FORMAT_YEAR_MONTH_DAY_T_DAY_HOUR_MINUTE_SECONDS, FORMAT_YEAR_MONTH_DAY, FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE,
            FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECONDS, FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_MSEC,
            FORMAT_YEAR_MONTH_DAY_T_DAY_HOUR_MINUTE, FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_M, FORMAT_YEAR_MONTH_NONA};

    /**
     * 星期数组
     */
    private static final String[] WEEKDAYSNAME = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 获得Calendar实例
     *
     * @return
     */
    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    /**
     * 私有构造函数，防止实例化
     */
    private DateUtils() {
    }

    /**
     * 判断一个字符串是否可以格式化成日期
     *
     * @param dateStr 日期字符串，格式为yyyy-MM-dd
     * @return
     */
    public static boolean isValidDate(String dateStr) {
        return isValidDate(dateStr, FORMAT_YEAR_MONTH_DAY);
    }

    /**
     * 判断一个字符串是否可以格式化成日期
     *
     * @param dateStr 日期字符串
     * @param pattern 校验格式，如 yyyy-MM-dd/yyyy-MM-dd HH:mm:ss/yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try {
            format.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 依据默认format格式【yyyy-MM-dd】把日期字符串转化为日期对象
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr) {
        try {
            // 依据format格式把日期字符串转化为日期对象
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
            dateFormat.setLenient(true);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 日期字符串转化为日期对象
     *
     * @param dateStr       日期字符串
     * @param parsePatterns 日期格式
     * @return
     * @throws ParseException
     */
    public static LocalDate stringToLocalDate(final String dateStr, final String... parsePatterns)
            throws ParseException {
        for (String pattern : parsePatterns) {
            try {
                // 依据format格式把日期字符串转化为日期对象
//				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                        // month-year
                        .appendPattern(pattern)
                        // default value for day
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                        // create formatter
                        .toFormatter();
                return LocalDate.parse(dateStr, fmt);
            } catch (Exception e) {
            }
        }
        throw new ParseException("Unable to parse the date: " + dateStr, -1);
    }

    /**
     * 依据format格式把日期字符串转化为日期对象
     *
     * @param dateStr
     * @param format  格式（如：yyyy-MM-dd）
     * @return
     */
    public static Date stringToDate(String dateStr, String format) {
        try {
            if (StringUtils.isEmpty(dateStr)) {
                return null;
            }
            // 依据format格式把日期字符串转化为日期对象
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(true);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 依据format格式把日期字符串转化为日期对象
     *
     * @param dateStr
     * @param parsePatterns
     * @return
     * @throws ParseException
     */
    public static LocalDateTime stringToLocalDateTime(final String dateStr, final String... parsePatterns)
            throws ParseException {
        for (String pattern : parsePatterns) {
            try {
                // 依据format格式把日期字符串转化为日期对象
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(dateStr, dateFormat);
            } catch (Exception e) {
            }
        }
        return stringToLocalDate(dateStr, parsePatterns).atStartOfDay();
    }

    /**
     * 添加指定天数获得新的日期
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @param days    添加天数
     * @return
     */
    public static Date dateAdd(String dateStr, String format, int days) {
        Date date = stringToDate(dateStr, format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * Date 按照默认格式【yyyy-MM-dd】 格式化成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sFormat = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
        return sFormat.format(date);
    }

    /**
     * Date 按照format格式化成字符串
     *
     * @param date   date
     * @param format 日期格式，如：yyyy-MM-dd
     * @return
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sFormat = new SimpleDateFormat(format);
        return sFormat.format(date);
    }

    /**
     * 毫秒数 按照默认格式化方法格式化成字符串
     *
     * @param milliseconds 毫秒数
     * @return
     */
    public static String longToDateString(long milliseconds) {
        Date date = new Date(milliseconds);
        return dateToString(date);
    }

    /**
     * 毫秒数按照传入的格式化方法格式化成字符串
     *
     * @param milliseconds 毫秒数
     * @param format       日期格式，如：yyyy-MM-dd
     * @return
     */
    public static String longToDateString(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        return dateToString(date, format);
    }

    /**
     * 获得年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获得月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得是今年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获得今天是周几
     *
     * @param date
     * @return
     */
    public static String geDayOfWeek(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return WEEKDAYSNAME[index];
    }

    /**
     * 加减年份，获得新的日期
     *
     * @param date
     * @return
     */
    public static Date getBeforeYear(Date date, int year) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 加减月份，获得新的日期
     *
     * @param date
     * @return
     */
    public static Date getBeforeMonth(Date date, int month) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 日期减周，获得新的日期
     *
     * @param date
     * @return
     */
    public static Date getWork(Date date, int w) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, w);
        return calendar.getTime();
    }

    /**
     * 获取本周第一天，星期一为第一天
     *
     * @return
     * @author huqiwei
     */
    public static Date getWeekFirstDaySunday() {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取本周最后一天，星期日为最后一天
     *
     * @return
     * @author huqiwei
     */
    public static Date getWeekLastDaySunday() {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 转换日期字符串
     *
     * @param date      日期
     * @param formatter 格式
     * @return
     */
    public static String format(LocalDate date, String formatter) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formatter);
        return dateFormat.format(date);
    }

    /**
     * 转换日期字符串
     *
     * @param date      日期
     * @param formatter 格式
     * @return
     */
    public static String format(LocalDateTime date, String formatter) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formatter);
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * LocalDataTime 转换 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDataTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * LocalData 转换 Date
     *
     * @param localDate
     * @return
     */
    public static Date localDataToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 方法说明：获取当前月
     * <p>
     * Author：        Lenovo
     * Create Date：   2019年9月2日 上午9:43:16
     * History:  2019年9月2日 上午9:43:16   Lenovo   Created.
     *
     * @param date
     * @return
     */
    public static String formatYearMonth(LocalDateTime date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH);
        //SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YEAR_MONTH);
        return dateFormat.format(date);
    }

    /**
     * 计算两个时间点的天数差（dt1-dt2）
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即要计算的天数差
     */
    public static int dateDiff(LocalDateTime dt1, LocalDateTime dt2) {
        // 获取第一个时间点的时间戳对应的秒数
        long t1 = dt1.toEpochSecond(ZoneOffset.ofHours(0));
        // 获取第一个时间点在是1970年1月1日后的第几天
        long day1 = t1 / (60 * 60 * 24);
        // 获取第二个时间点的时间戳对应的秒数
        long t2 = dt2.toEpochSecond(ZoneOffset.ofHours(0));
        // 获取第二个时间点在是1970年1月1日后的第几天
        long day2 = t2 / (60 * 60 * 24);
        //返 回两个时间点的天数差
        return (int) (day1 - day2);
    }
}
