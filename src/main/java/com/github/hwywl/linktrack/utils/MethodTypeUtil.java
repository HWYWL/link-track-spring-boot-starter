package com.github.hwywl.linktrack.utils;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.system.Constant;
import com.github.hwywl.linktrack.system.MethodType;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * 判断方法所属位置
 *
 * @author huangwenyi
 */
public class MethodTypeUtil {
    public static Logger log = Logger.getLogger(MethodTypeUtil.class.toString());

    /**
     * 获取堆栈调用的数据
     *
     * @param stacks   堆栈跟踪信息
     * @param packName 包名
     * @return 堆栈
     */
    public static StackTraceElement filter(StackTraceElement[] stacks, String packName) {
        String[] packNameSplit = packName.split("\\.");
        String filter = packNameSplit.length > 1 ? packNameSplit[0] + "." + packNameSplit[1] : packNameSplit[0];
        int stacksLength = stacks.length;
        for (int i = 0; i < stacksLength; i++) {
            StackTraceElement stack = stacks[i];
            if (stack.getClassName().startsWith(filter) && !stack.getClassName().contains("$")) {
                return stack;
            }
        }
        return null;
    }

    /**
     * 获取方法类型
     *
     * @param pjp 方法调用的描述，在方法调用时提供给拦截器
     * @return 类型
     */
    public static MethodType getMethodType(MethodInvocation pjp) {
        Class<?> targetClass = Objects.requireNonNull(pjp.getThis()).getClass();
        String className = pjp.getMethod().getDeclaringClass().getName().toLowerCase();

        return getMethodType(targetClass) != null ? getMethodType(targetClass) : getMethodType(className);
    }

    /**
     * 获取方法类型
     *
     * @param pjp 切面信息
     * @return 类型
     */
    public static MethodType getMethodType(ProceedingJoinPoint pjp) {
        Class<?> targetClass = pjp.getTarget().getClass();
        String className = pjp.getTarget().getClass().getName().toLowerCase();

        return getMethodType(targetClass) != null ? getMethodType(targetClass) : getMethodType(className);
    }

    /**
     * 获取方法类型
     *
     * @param className 类名称
     * @return 类型
     */
    public static MethodType getMethodType(String className) {
        className = className.toLowerCase();
        if (className.contains("controller")) {
            return MethodType.Controller;
        } else if (className.contains("service")) {
            return MethodType.Service;
        } else if (className.contains("dao") || className.contains("mapper") || className.contains("com.sun.proxy.$Proxy")) {
            return MethodType.Dao;
        } else {
            return MethodType.Others;
        }
    }

    /**
     * 获取方法类型
     *
     * @param targetClass classz对象
     * @return 类型
     */
    private static MethodType getMethodType(Class<?> targetClass) {
        if (targetClass.getAnnotation(Controller.class) != null || targetClass.getAnnotation(RestController.class) != null) {
            return MethodType.Controller;
        } else if (targetClass.getAnnotation(Service.class) != null) {
            return MethodType.Service;
        } else if (targetClass.getAnnotation(Repository.class) != null) {
            return MethodType.Dao;
        }

        return null;
    }

    /**
     * 打印节点日志
     *
     * @param current 节点
     */
    public static void showLog(LinkTrackNode current) {
        if (Boolean.parseBoolean(CacheUtil.get(Constant.LOG_ENABLE_KEY).toString())) {
            String currentKey = current.getClassName() + "." + current.getMethodName();
            log.info("调用方法=" + currentKey + "，耗时=" + current.getAvgRunTime() + "毫秒");
        }
    }
}

