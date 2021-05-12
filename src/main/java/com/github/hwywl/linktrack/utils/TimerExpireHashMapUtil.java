package com.github.hwywl.linktrack.utils;

import com.github.hwywl.linktrack.model.ResponseStatistics;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: YI
 * @description: 自动删除N天前的数据
 * @date: create in 2021/5/11 10:58
 */
public class TimerExpireHashMapUtil {
    private static final ConcurrentHashMap<String, ResponseStatistics> TIMER_EXPIRE_MAP = new ConcurrentHashMap<>(15);

    /**
     * 移除过期数据，并更新新的数据
     *
     * @param key        数据类型
     * @param cttDateStr 当前日期
     */
    public static void addOrUpdateCache(String key, String cttDateStr) {
        ResponseStatistics value = selectCache(key);
        if (cttDateStr.equals(value.getCttDateStr())) {
            value.setTodayStatistics(value.getTodayStatistics() + 1);
        } else {
            value.setTheDayBeforeYesterdayStatistics(value.getYesterdayStatistics());
            value.setYesterdayStatistics(value.getTodayStatistics());
            value.setTodayStatistics(1);
            value.setCttDateStr(cttDateStr);
        }

        TIMER_EXPIRE_MAP.put(key, value);
    }

    /**
     * 获取缓存数据
     *
     * @param key 数据类型
     * @return
     */
    public static ResponseStatistics selectCache(String key) {
        ResponseStatistics responseStatistics = TIMER_EXPIRE_MAP.get(key);
        return responseStatistics == null ? new ResponseStatistics() : responseStatistics;
    }
}
