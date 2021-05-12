package com.github.hwywl.linktrack.service;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.utils.DateFormatUtil;
import com.github.hwywl.linktrack.utils.MethodTypeUtil;
import org.aopalliance.intercept.MethodInvocation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 节点的创建和父子节点的查询
 *
 * @author huangwenyi
 */
public class InvokeService {

    /**
     * 创建父节点信息
     *
     * @param packName 方法名
     * @return
     */
    public static LinkTrackNode createParentRunTimeNode(String packName) {
        String parentClassName = "";
        String parentMothodName = "";
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = MethodTypeUtil.filter(stacks, packName);
        if (stack != null) {
            parentClassName = stack.getClassName();
            parentMothodName = stack.getMethodName();
        }

        LinkTrackNode parent = new LinkTrackNode();
        parent.setClassName(parentClassName);
        parent.setMethodName(parentMothodName);
        parent.setName(parentClassName.substring(parentClassName.lastIndexOf(".") + 1) + "." + parentMothodName);
        parent.setMethodType(MethodTypeUtil.getMethodType(parentClassName));
        parent.setChildren(new ArrayList<>());

        // 获取当前东八区时间
        String todayCttDate = DateFormatUtil.todayCttDate();
        parent.setMaxRunCreationTime(todayCttDate);
        parent.setMinRunCreationTime(todayCttDate);

        return parent;
    }

    /**
     * 创建子节点信息
     *
     * @param pjp     调用方法
     * @param runTime 调用耗时
     * @return
     */
    public static LinkTrackNode createCurrentRunTimeNode(MethodInvocation pjp, Double runTime) {
        String className = pjp.getMethod().getDeclaringClass().getName();
        String methodName = pjp.getMethod().getName();
        LinkTrackNode current = new LinkTrackNode();
        current.setName(className.substring(className.lastIndexOf(".") + 1) + "." + methodName);
        current.setClassName(className);
        current.setMethodName(methodName);
        BigDecimal bg = new BigDecimal(runTime);
        runTime = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        current.setAvgRunTime(runTime);
        current.setMaxRunTime(runTime);
        current.setMinRunTime(runTime);
        current.setChildren(new ArrayList<>());
        current.setMethodType(MethodTypeUtil.getMethodType(pjp));

        // 获取当前东八区时间
        String todayCttDate = DateFormatUtil.todayCttDate();
        current.setMaxRunCreationTime(todayCttDate);
        current.setMinRunCreationTime(todayCttDate);

        return current;
    }

    public static void createGraph(LinkTrackNode parent, LinkTrackNode current) {
        if (current.getMethodName().contains("$")) {
            return;
        }

        String parentKey = parent.getClassName() + "." + parent.getMethodName();
        String currentKey = current.getClassName() + "." + current.getMethodName();

        if (".".equals(parentKey)) {
            RunTimeNodeService.addOrUpdate(currentKey, current);
        } else if (RunTimeNodeService.containsNode(parent)) {
            RunTimeNodeService.addOrUpdateChildren(parent, current);
        } else {
            List<LinkTrackNode> list = new ArrayList<>();
            list.add(current);
            parent.setChildren(list);
            RunTimeNodeService.add(parentKey, parent);
        }
    }

}
