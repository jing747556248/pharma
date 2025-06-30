package com.sanofi.pharma.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 */
@Slf4j
public class DateUtil {

    /**
     * 一天转化为小时。单位：小时
     */
    public static final int DAY_2_HOUR = 24;
    public static final int WEEK_2_DAY = 7;
    public static final long SECOND_LONG = 1000L;
    public static final long FIVE_SECOND_LONG = 5 * SECOND_LONG;
    public static final long MINUTE_LONG = 60 * SECOND_LONG;
    public static final long FIVE_MINUTE_LONG = 5 * 60 * SECOND_LONG;
    public static final long HOUR_LONG = 60 * MINUTE_LONG;
    public static final long DAY_LONG = DAY_2_HOUR * HOUR_LONG;
    public static final long MONTH_LONG = 30 * DAY_LONG;
    public static final long YEAR_LONG = 365 * DAY_LONG;
    public static final long WEEK_ONE_TIME_LONG = 6 * 24 * 60 * 60 * 1000;
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE2 = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND3 = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND4 = "yyyy-MM-dd:HH:mm:ss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_DECOND5 = "yyyyMMddhhmmss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_DECOND6 = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_DECOND7 = "yyyyMMddHHmm";
    public static final String DATE_FORMAT_MONTH_DAY = "MM月dd日";
    public static final String DATE_FORMAT_MONTH_DAY1 = "MM-dd";
    public static final String DATE_FORMAT_MONTH_DAY2 = "MM/dd";
    public static final String DATE_FORMAT_HOUR_MINUTE = "HH:mm";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY = "yyyyMMdd";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY3 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY4 = "yyyy.MM.dd";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_STRING = "yy年MM月dd日";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_STRING2 = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_STRING3 = "yyyy年MM月dd日HH时mm分";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_SECOND = "yyyy-MM-dd HH:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_SECOND_MILLI = "yyyy/MM/dd HH:mm:ss SSS";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_MS = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyyMMdd";

    private static String[] WEEKS_CN = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static String[] WEEKS_US = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
    private static String[] WEEKS_TW = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private static LocalTime SEVEN_HOUR_AFTER = LocalTime.of(7, 0, 0);

    public static final String DIFF_TYPE_MINUTE = "minute";

    /**
     * 日期格式化  Date 转 String
     * 默认返回 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern).format(date);
        }
    }

    /**
     * String 转 Date
     *
     */
    public static Date stringToDate(String date, String pattern) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(pattern)) {
            return null;
        } else {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
            return fmt.parseLocalDateTime(date).toDate();
        }
    }

    public static LocalDate dateToLocalDate(Date date){
        if (date == null)
            return null;
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    /**
     * @param date    时间。若为空，则返回空串
     * @param pattern 时间格式化
     * @return 格式化后的时间字符串.
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return date1 != null && date2 != null && diffDay(date1, date2) == 0;
    }

    public static Integer diffDay(Date date1, Date date2) {
        return diffDay(date1, date2, false);
    }

    public static Integer diffDay(Date fDate, Date oDate, boolean includeToday) {
        if (fDate != null && oDate != null) {
            DateTime begin = new DateTime(fDate);
            DateTime end = new DateTime(oDate);
            int days = Days.daysBetween(end.toLocalDate(), begin.toLocalDate()).getDays();
            return includeToday ? days + 1 : days;
        } else {
            return null;
        }
    }

    /**
     * 获取某个时间段内的每天时间日期
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 返回结果
     */
    public static List<String> getBetweenTimes(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(beginDate);
            dEnd = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return findDates(dBegin, dEnd);
    }

    /**
     * 获取上一天结束时间
     *
     * @return 获得该日期的结束
     */
    public static Date getSpecifyLastDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        //获取前几天的都可以，对-1进行改变即可
        //获取后一天的时间也可以，把-1改为1即可
        //后几天和前几天同理
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * @param dBegin 开始时间
     * @param dEnd   结束时间
     * @return 结果集
     */
    public static List<String> findDates(Date dBegin, Date dEnd) {
        List<String> lDate = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }


    /**
     * 计算当期时间相差的日期
     *
     * @param date   设置时间
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(Date date, int field, int amount) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 获取指定天开始时间
     *
     * @param date 日期
     * @return 获得该日期的开始
     */
    public static Date getDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalender(calendar, 0, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天开始时间
     *
     * @return 获得该日期的开始
     */
    public static Date getDayBegin() {
        return getDayBegin(new Date());
    }

    /**
     * 获取指定天结束时间
     *
     * @param date 日期
     * @return 获得该日期的结束
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalender(calendar, 23, 59, 59, 999);
        return calendar.getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return 获得该日期的开始
     */
    public static Date getDayEnd() {
        return getDayEnd(new Date());
    }

    public static Date getDayEndInSencond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalender(calendar, 23, 59, 59, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间
     *
     * @param date    指定日期
     * @param diffDay 天数
     * @return 自指定日期后的若干天的日期
     */
    public static Date getDay(Date date, Integer diffDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, diffDay);
        return cal.getTime();
    }

    /**
     * 获取天数
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置Calendar的小时、分钟、秒、毫秒
     *
     * @param calendar    日历
     * @param hour        小时
     * @param minute      分钟
     * @param second      秒
     * @param milliSecond 毫秒
     */
    public static void setCalender(Calendar calendar, int hour, int minute, int second, int milliSecond) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
    }

    public static int getWeek(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    /**
     * 计算年纪
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 判断时间是否大于7点
     *
     * @param time
     * @return
     */
    public static boolean isMoreThanSeven(LocalTime time) {
        return time.isAfter(SEVEN_HOUR_AFTER);
    }

    /**
     * 转化 年月日时分 格式为 2222-02-02
     *
     * @param date
     * @return
     */
    public static String dateFormat(String date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        SimpleDateFormat birthday = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isNotEmpty(date)) {
                return birthday.format(format.parse(date));
            }
        } catch (ParseException e) {
            log.error("日期转化失败 参数:【{}】目标格式:【{}】", date, dateFormat);
            return date;
        }
        return date;
    }


    /**
     * 格式化日期 时分 格式为 2222-02-02 21:00:00 -> 21:00
     *
     * @param sourceDate   原始日期
     * @param sourceFormat 原始格式
     * @return 目标格式
     */
    public static String getHourMinOfDay(String sourceDate, String sourceFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT_HOUR_MINUTE);
        try {
            if (StringUtils.isEmpty(sourceDate)) {
                throw new IllegalArgumentException(
                        "sourceDate is null");
            }
            return targetFormat.format(format.parse(sourceDate));
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "converter error");
        }
    }

    /**
     * 00:00:00 转秒
     *
     * @param time 00:00:12
     * @return
     */
    public static long timeToTimestamp(String time) {
        int hh = Integer.parseInt(time.substring(0, 2));
        int mi = Integer.parseInt(time.substring(3, 5));
        int ss = Integer.parseInt(time.substring(6));
        long ts = (hh * 60 * 60) + (mi * 60) + ss;
        return ts;
    }


    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int age = yearNow - yearBirth + 1;

        return age;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(String birthday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_YEAR_MONTH_DAY2);
        Calendar cal = Calendar.getInstance();
        try {
            Date parse = simpleDateFormat.parse(birthday);
            if (cal.before(parse)) {
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);
            cal.setTime(parse);
            int yearBirth = cal.get(Calendar.YEAR);
            return yearNow - yearBirth + 1;
        } catch (Exception e) {
            log.error("日期转化失败", e);
        }
        return 0;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthdayNew(String birthday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_YEAR_MONTH_DAY2);
        try {
            Date birth = simpleDateFormat.parse(birthday);
            Calendar cal = Calendar.getInstance();
            if (cal.before(birth)) {
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int thisYear = cal.get(Calendar.YEAR);
            int thisMonth = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTime(birth);
            int birthYear = cal.get(Calendar.YEAR);
            int birthMonth = cal.get(Calendar.MONTH);
            int birthdayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            int age = thisYear - birthYear;

            // 未足月
            if (thisMonth <= birthMonth) {
                // 当月
                if (thisMonth == birthMonth) {
                    // 未足日
                    if (dayOfMonth < birthdayOfMonth) {
                        age--;
                    }
                } else {
                    age--;
                }
            }
            return age;
        } catch (Exception e) {
            log.error("日期转化失败", e);
        }
        return 0;
    }

    /**
     * 增加几天
     * @param date
     * @param day
     * @return
     */
    public static Date getDateAddDay(Date date, Integer day) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, day);
        return cd.getTime();
    }

    /**
     * 增加几分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date getDateAddMinute(Date date, Integer minute) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MINUTE, minute);
        return cd.getTime();
    }

    /**
     * 按指定日期单位计算两个日期间的间隔
     *
     * @param timeInterval，
     * @param date1，
     * @param date2
     * @return date1-date2，
     */
    public static synchronized long dateDiff(String timeInterval, Date date1, Date date2) {

        if (timeInterval.equals("year")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int time = calendar.get(Calendar.YEAR);
            calendar.setTime(date2);
            return time - calendar.get(Calendar.YEAR);
        }

        if (timeInterval.equals("month")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int time = calendar.get(Calendar.YEAR) * 12;
            calendar.setTime(date2);
            time -= calendar.get(Calendar.YEAR) * 12;
            calendar.setTime(date1);
            time += calendar.get(Calendar.MONTH);
            calendar.setTime(date2);
            return time - calendar.get(Calendar.MONTH);
        }

        if (timeInterval.equals("week")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int time = calendar.get(Calendar.YEAR) * 52;
            calendar.setTime(date2);
            time -= calendar.get(Calendar.YEAR) * 52;
            calendar.setTime(date1);
            time += calendar.get(Calendar.WEEK_OF_YEAR);
            calendar.setTime(date2);
            return time - calendar.get(Calendar.WEEK_OF_YEAR);
        }

        if (timeInterval.equals("day")) {
            long time = date1.getTime() / 1000 / 60 / 60 / 24;
            return time - date2.getTime() / 1000 / 60 / 60 / 24;
        }

        if (timeInterval.equals("hour")) {
            long time = date1.getTime() / 1000 / 60 / 60;
            return time - date2.getTime() / 1000 / 60 / 60;
        }

        if (timeInterval.equals("minute")) {
            long time = date1.getTime() / 1000 / 60;
            return time - date2.getTime() / 1000 / 60;
        }

        if (timeInterval.equals("second")) {
            long time = date1.getTime() / 1000;
            return time - date2.getTime() / 1000;
        }

        return date1.getTime() - date2.getTime();
    }

    /**
     * 按指定日期单位计算两个日期间的间隔
     *
     * @param timeInterval，
     * @param date1，
     * @param date2
     * @return date1-date2，
     */
    public static synchronized long getMinuteDiff(String timeInterval, Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();//这样得到的差值是微秒级别
        long result  = diff/(1000*60);
        return diff % (1000*60) == 0 ? result : result + 1;
    }

    public static long getDateDifference(String startDate, String endDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(endDate);
            Date d2 = df.parse(startDate);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            return diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            log.error("日期转化失败", e);
        }
        return 0L;
    }

    public static Date getDateByLocalDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * LocalDateTime转为日期
     *
     * @param localDateTime LocalDateTime
     * @return 日期
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
        final Date date = Date.from(zdt.toInstant());
        return date;
    }

    public static String timeTransfer(String strDate) {
        if(StringUtils.isEmpty(strDate)) return null;
        SimpleDateFormat formatterPre = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY2);
        SimpleDateFormat formatterLater = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY4);
        Date strtodate;
        String dateResult = null;
        try {
            strtodate = formatterPre.parse(strDate);
            dateResult = formatterLater.format(strtodate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateResult;
    }

    /**
     * 获得该月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime()) + " 00:00:00";
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = 0;
        // 2月的平年瑞年天数
        if (month == 2) {
            lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime()) + " 23:59:59";
        return lastDayOfMonth;
    }

}