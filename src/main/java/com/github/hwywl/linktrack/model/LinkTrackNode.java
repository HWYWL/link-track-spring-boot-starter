package com.github.hwywl.linktrack.model;

import com.github.hwywl.linktrack.system.MethodType;

import java.util.List;
import java.util.Objects;

/**
 * 数据节点模型
 *
 * @author huangwenyi
 */
public class LinkTrackNode implements Comparable<LinkTrackNode> {
    /**
     * 类名+方法名
     */
    private String name;
    /**
     * 类的完成包引用名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 平均耗时
     */
    private Double avgRunTime = 0.0;
    /**
     * 最大耗时
     */
    private Double maxRunTime = 0.0;
    /**
     * 最小耗时
     */
    private Double minRunTime = 10000.0;
    /**
     * 显示的耗时
     */
    private Double value = 0.0;
    /**
     * 耗时单位
     */
    private String avgRunTimeUnit = "ms";

    /**
     * 方法类型
     */
    private MethodType methodType;

    /**
     * 最小耗时产生的时间
     */
    private String minRunCreationTime = null;

    /**
     * 最大耗时产生的时间
     */
    private String maxRunCreationTime = null;

    /**
     * 子节点
     */
    private List<LinkTrackNode> children;

    @Override
    public int compareTo(LinkTrackNode ob) {
        return this.avgRunTime.compareTo(ob.getAvgRunTime());
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvgRunTimeUnit() {
        return avgRunTimeUnit;
    }

    public void setAvgRunTimeUnit(String avgRunTimeUnit) {
        this.avgRunTimeUnit = avgRunTimeUnit;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Double getAvgRunTime() {
        return avgRunTime;
    }

    public void setAvgRunTime(Double avgRunTime) {
        this.avgRunTime = avgRunTime;
    }

    public Double getMaxRunTime() {
        return maxRunTime;
    }

    public void setMaxRunTime(Double maxRunTime) {
        this.maxRunTime = maxRunTime;
    }

    public Double getMinRunTime() {
        return minRunTime;
    }

    public void setMinRunTime(Double minRunTime) {
        this.minRunTime = minRunTime;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public String getMinRunCreationTime() {
        return minRunCreationTime;
    }

    public void setMinRunCreationTime(String minRunCreationTime) {
        this.minRunCreationTime = minRunCreationTime;
    }

    public String getMaxRunCreationTime() {
        return maxRunCreationTime;
    }

    public void setMaxRunCreationTime(String maxRunCreationTime) {
        this.maxRunCreationTime = maxRunCreationTime;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public List<LinkTrackNode> getChildren() {
        return children;
    }

    public void setChildren(List<LinkTrackNode> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkTrackNode that = (LinkTrackNode) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName);
    }

    @Override
    public String toString() {
        return "LinkTrackNode{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", avgRunTime=" + avgRunTime +
                ", maxRunTime=" + maxRunTime +
                ", minRunTime=" + minRunTime +
                ", value=" + value +
                ", avgRunTimeUnit='" + avgRunTimeUnit + '\'' +
                ", methodType=" + methodType +
                ", mixRunCreationTime='" + minRunCreationTime + '\'' +
                ", maxRunCreationTime='" + maxRunCreationTime + '\'' +
                ", children=" + children +
                '}';
    }
}
