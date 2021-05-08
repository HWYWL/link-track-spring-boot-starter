package com.github.hwywl.linktrack.service;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.model.SystemStatistic;
import com.github.hwywl.linktrack.system.Constant;
import com.github.hwywl.linktrack.utils.CacheUtil;
import com.github.hwywl.linktrack.utils.GraphMap;
import com.github.hwywl.linktrack.system.MethodType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 节点的增删改查封装
 *
 * @author huangwenyi
 */
public class RunTimeNodeService {

    /**
     * 添加/更新节点
     *
     * @param key         方法名称
     * @param linkTrackNode 节点信息
     */
    public static void addOrUpdate(String key, LinkTrackNode linkTrackNode) {
        if (GraphMap.containsKey(key)) {
            LinkTrackNode oldNode = GraphMap.get(key);
            if (0 == oldNode.getAvgRunTime()) {
                GraphMap.get(key).setAvgRunTime((linkTrackNode.getAvgRunTime()));
            } else {
                BigDecimal bg = BigDecimal.valueOf((linkTrackNode.getAvgRunTime() + oldNode.getAvgRunTime()) / 2.0);
                double avg = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                GraphMap.get(key).setAvgRunTime(avg);
            }
            GraphMap.get(key).setMaxRunTime(linkTrackNode.getMaxRunTime() > oldNode.getMaxRunTime() ? linkTrackNode.getMaxRunTime() : oldNode.getMaxRunTime());
            GraphMap.get(key).setMinRunTime(linkTrackNode.getMinRunTime() < oldNode.getMinRunTime() ? linkTrackNode.getMinRunTime() : oldNode.getMinRunTime());
        } else {
            GraphMap.put(key, linkTrackNode);
        }
    }

    /**
     * 添加节点
     *
     * @param key         方法名称
     * @param linkTrackNode 节点信息
     */
    public static void add(String key, LinkTrackNode linkTrackNode) {
        GraphMap.put(key, linkTrackNode);
    }

    /**
     * 判断节点是否存在
     *
     * @param node 节点
     * @return
     */
    public static boolean containsNode(LinkTrackNode node) {
        String key = node.getClassName() + "." + node.getMethodName();
        return GraphMap.containsKey(key);
    }

    /**
     * 获取节点
     *
     * @param key 父节点
     * @return
     */
    public static LinkTrackNode getRunTimeNode(String key) {
        return GraphMap.get(key);
    }

    /**
     * 拼接节点
     *
     * @param parent  父节点
     * @param current 当前节点
     */
    public static void addOrUpdateChildren(LinkTrackNode parent, LinkTrackNode current) {
        String parentKey = parent.getClassName() + "." + parent.getMethodName();
        LinkTrackNode hisLinkTrackNode = RunTimeNodeService.getRunTimeNode(parentKey);
        List<LinkTrackNode> hisLinkTrackNodeChildren = hisLinkTrackNode.getChildren();
        if (hisLinkTrackNodeChildren != null) {
            if (hisLinkTrackNodeChildren.contains(current)) {
                updateChildren(current, hisLinkTrackNodeChildren);
            } else {
                hisLinkTrackNodeChildren.add(current);
            }
        } else {
            List<LinkTrackNode> list = new ArrayList<>();
            list.add(current);
            hisLinkTrackNode.setChildren(list);
        }
        GraphMap.put(parentKey, hisLinkTrackNode);
    }

    /**
     * 更新子节点数据
     *
     * @param child                  子节点
     * @param hisLinkTrackNodeChildren
     */
    public static void updateChildren(LinkTrackNode child, List<LinkTrackNode> hisLinkTrackNodeChildren) {
        int hisLength = hisLinkTrackNodeChildren.size();
        for (int i = 0; i < hisLength; i++) {
            LinkTrackNode hisLinkTrackNodeChild = hisLinkTrackNodeChildren.get(i);
            if (hisLinkTrackNodeChild.equals(child)) {
                double avg = (child.getAvgRunTime() + hisLinkTrackNodeChild.getAvgRunTime()) / 2.0;
                BigDecimal bg = new BigDecimal(avg);
                avg = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                child.setAvgRunTime(avg);
                if (hisLinkTrackNodeChild.getMaxRunTime() > child.getMaxRunTime()) {
                    child.setMaxRunTime(hisLinkTrackNodeChild.getMaxRunTime());
                }
                if (hisLinkTrackNodeChild.getMinRunTime() < child.getMinRunTime()) {
                    child.setMinRunTime(hisLinkTrackNodeChild.getMinRunTime());
                }
                hisLinkTrackNodeChildren.set(i, child);
                break;
            }
        }
    }

    /**
     * 获取方法耗时统计的数据
     *
     * @return 耗时信息
     */
    public static SystemStatistic getRunStatistic() {
        SystemStatistic systemStatistic = new SystemStatistic();
        List<LinkTrackNode> controllerApis = GraphMap.get(MethodType.Controller);
        if (null == controllerApis || controllerApis.size() == 0) {
            return systemStatistic;
        }
        // 计算超过阈值时间的数量
        int delayNum = (int) controllerApis.stream().filter(controllerApi -> controllerApi.getAvgRunTime() >= Double.parseDouble(CacheUtil.get(Constant.TIME_THRESHOLD_KEY).toString())).count();
        systemStatistic.setDelayNum(delayNum);
        // 计算未超过阈值时间的数量
        int normalNum = (int) controllerApis.stream().filter(controllerApi -> controllerApi.getAvgRunTime() < Double.parseDouble(CacheUtil.get(Constant.TIME_THRESHOLD_KEY).toString())).count();
        systemStatistic.setNormalNum(normalNum);
        // 计算总的数量
        int totalNum = (int) controllerApis.stream().count();
        systemStatistic.setTotalNum(totalNum);
        Double max = controllerApis.stream().map(LinkTrackNode::getAvgRunTime).max(Double::compareTo).get();
        Double min = controllerApis.stream().map(LinkTrackNode::getAvgRunTime).min(Double::compareTo).get();
        Double avg = controllerApis.stream().map(LinkTrackNode::getAvgRunTime).collect(Collectors.averagingDouble(Double::doubleValue));
        BigDecimal bg = new BigDecimal(avg);
        avg = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        systemStatistic.setMaxRunTime(max);
        systemStatistic.setMinRunTime(min);
        systemStatistic.setAvgRunTime(avg);

        return systemStatistic;
    }

    /**
     * 获取接口总数
     *
     * @return 接口总数
     */
    public static List<LinkTrackNode> getControllers() {

        return GraphMap.get(MethodType.Controller);
    }

    /**
     * 获取树结构数据
     *
     * @param methodName 方法名称
     * @return
     */
    public static LinkTrackNode getGraph(String methodName) {
        return GraphMap.getTree(methodName);
    }
}
