package com.github.hwywl.linktrack.model;

/**
 * @author: YI
 * @description: 接口响应统计
 * @date: create in 2021/5/11 11:00
 */
public class ResponseStatistics {
    /**
     * 今天访问数量
     */
    private Integer todayStatistics = 0;
    /**
     * 昨天访问数量
     */
    private Integer yesterdayStatistics = 0;
    /**
     * 前天访问数量
     */
    private Integer theDayBeforeYesterdayStatistics = 0;

    /**
     * 当前日期时间
     */
    private String cttDateStr;

    public Integer getTodayStatistics() {
        return todayStatistics;
    }

    public void setTodayStatistics(Integer todayStatistics) {
        this.todayStatistics = todayStatistics;
    }

    public Integer getYesterdayStatistics() {
        return yesterdayStatistics;
    }

    public void setYesterdayStatistics(Integer yesterdayStatistics) {
        this.yesterdayStatistics = yesterdayStatistics;
    }

    public Integer getTheDayBeforeYesterdayStatistics() {
        return theDayBeforeYesterdayStatistics;
    }

    public void setTheDayBeforeYesterdayStatistics(Integer theDayBeforeYesterdayStatistics) {
        this.theDayBeforeYesterdayStatistics = theDayBeforeYesterdayStatistics;
    }

    public String getCttDateStr() {
        return cttDateStr;
    }

    public void setCttDateStr(String cttDateStr) {
        this.cttDateStr = cttDateStr;
    }
}
