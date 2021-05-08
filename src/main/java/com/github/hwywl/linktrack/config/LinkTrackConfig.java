package com.github.hwywl.linktrack.config;

import com.github.hwywl.linktrack.handler.LinkTrackHandler;
import com.github.hwywl.linktrack.system.Constant;
import com.github.hwywl.linktrack.utils.CacheUtil;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 配置
 *
 * @author huangwenyi
 */
@Configuration
@ConfigurationProperties(prefix = "link-track.log.conf")
public class LinkTrackConfig {
    /**
     * 接口耗时阈值
     */
    Double timeThreshold = 500.0;
    /**
     * 需要扫描拦截的位置
     */
    String pointcut = "execution(* com.github.hwywl.linktrack.controller..*(..))}";
    /**
     * 前端模板引擎
     */
    String showTemplate = "thymeleaf";

    @Bean
    public AspectJExpressionPointcutAdvisor configurableDivisors() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new LinkTrackHandler());
        return advisor;
    }

    @Bean
    public Map<String, Object> getConfig() {
        CacheUtil.put(Constant.TIME_THRESHOLD_KEY, this.timeThreshold);

        return CacheUtil.getCache();
    }

    public Double getTimeThreshold() {
        return timeThreshold;
    }

    public void setTimeThreshold(Double timeThreshold) {
        this.timeThreshold = timeThreshold;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public String getShowTemplate() {
        return showTemplate;
    }

    public void setShowTemplate(String showTemplate) {
        this.showTemplate = showTemplate;
    }
}
