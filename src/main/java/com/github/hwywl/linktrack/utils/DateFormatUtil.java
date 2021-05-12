package com.github.hwywl.linktrack.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: YI
 * @description: 时间工具类
 * @date: create in 2021/5/10 11:35
 */
public class DateFormatUtil {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);

    /**
     * 获取今天的日期
     *
     * @param timeZone 时区
     * @return 今天的日期
     */
    public static String todayDate(String timeZone) {
        LocalDateTime ldt = StringUtils.isEmpty(timeZone) ? LocalDateTime.now() : LocalDateTime.now(ZoneId.of(timeZone));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return ldt.format(dtf);
    }

    /**
     * 获取今天的日期
     *
     * @param timeZone   时区
     * @param dateFormat 格式化格式
     * @return 今天的日期
     */
    public static String todayDate(String timeZone, String dateFormat) {
        LocalDateTime ldt = StringUtils.isEmpty(timeZone) ? LocalDateTime.now() : LocalDateTime.now(ZoneId.of(timeZone));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);

        return ldt.format(dtf);
    }

    /**
     * 获取今天东八区的日期
     *
     * @param dateFormat 格式化格式
     * @return 今天的日期
     */
    public static String todayCttDate(String dateFormat) {
        return todayDate(ZoneId.SHORT_IDS.get("CTT"), dateFormat);
    }

    /**
     * 获取今天东八区的日期
     *
     * @return 今天的日期
     */
    public static String todayCttDate() {
        return todayDate(ZoneId.SHORT_IDS.get("CTT"));
    }

    /**
     * 获取今天的日期
     *
     * @return 今天的日期
     */
    public static String todayDate() {
        return todayDate(null);
    }

    /**
     * 判断时间是否超时
     *
     * @param startDateStr 开始时间
     * @param endDateStr   当前时间
     * @param checkTimeDay 超时天数
     * @return
     */
    public static boolean fewDaysApart(String startDateStr, String endDateStr, Integer checkTimeDay) {
        try {
            Date startDate = FORMAT.parse(startDateStr);
            Date endDate = FORMAT.parse(endDateStr);
            long diff = endDate.getTime() - startDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            return days > checkTimeDay;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
