package com.github.hwywl.linktrack.annotation;

import java.lang.annotation.*;

/**
 * 注解apo拦截日志
 *
 * @author YI
 * @date 2021年5月8日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    /**
     * 方法描述
     *
     * @return
     */
    String description() default "";
}
