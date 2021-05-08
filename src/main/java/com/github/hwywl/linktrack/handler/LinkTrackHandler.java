package com.github.hwywl.linktrack.handler;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.service.InvokeService;
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
        String packName = invocation.getMethod().getDeclaringClass().getPackage().getName();
        LinkTrackNode parent = InvokeService.createParentRunTimeNode(packName);
        LinkTrackNode current = InvokeService.createCurrentRunTimeNode(invocation, ((end - begin) / 1000000.0));
        InvokeService.createGraph(parent, current);

        return obj;
    }
}
