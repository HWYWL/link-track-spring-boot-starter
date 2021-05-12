package com.github.hwywl.linktrack.handler;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.service.InvokeService;
import com.github.hwywl.linktrack.system.Constant;
import com.github.hwywl.linktrack.system.MethodType;
import com.github.hwywl.linktrack.utils.CacheUtil;
import com.github.hwywl.linktrack.utils.DateFormatUtil;
import com.github.hwywl.linktrack.utils.TimerExpireHashMapUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 调用方法拦截器，方法将进入这里进行计算
 *
 * @author huangwenyi
 */
public class LinkTrackHandler implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long begin = System.nanoTime();
        Object obj = invocation.proceed();
        long end = System.nanoTime();
        double runTime = (end - begin) / 1000000.0;
        String packName = invocation.getMethod().getDeclaringClass().getPackage().getName();
        LinkTrackNode parent = InvokeService.createParentRunTimeNode(packName);
        LinkTrackNode current = InvokeService.createCurrentRunTimeNode(invocation, runTime);
        InvokeService.createGraph(parent, current);

        // 响应统计只统计Controller层
        if (parent.getMethodType() == MethodType.Controller) {
            String cttDate = DateFormatUtil.todayCttDate(DateFormatUtil.DATE_FORMAT_YYYY_MM_DD);
            // 线程耗时阈值
            Object o = CacheUtil.get(Constant.TIME_THRESHOLD_KEY);
            String key = Double.parseDouble(o.toString()) > runTime ? Constant.NORMAL_STATISTICS_KEY : Constant.DELAY_STATISTICS_KEY;
            TimerExpireHashMapUtil.addOrUpdateCache(key, cttDate);
        }

        return obj;
    }
}
