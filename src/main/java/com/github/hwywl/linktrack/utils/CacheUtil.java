package com.github.hwywl.linktrack.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author huangwenyi
 * @date 2021-5-7
 */
public class CacheUtil {
    private static final ConcurrentHashMap<String, Object> CACHE = new ConcurrentHashMap<>(15);

    /**
     * 赋值
     *
     * @param key
     * @param value
     * @return
     */
    public static Object put(String key, Object value) {
        return CACHE.put(key, value);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Object o = CACHE.get(key);
        return o == null ? "" : o;
    }

    /**
     * 获取整个map
     *
     * @return
     */
    public static ConcurrentHashMap<String, Object> getCache() {
        return CACHE;
    }
}
