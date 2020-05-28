package com.karl.base.model;

/**
 * 缓存空值
 *
 * @author 杜永军
 * @date 2019/6/6
 */
public class CacheNull {
    public static final CacheNull INSTANCE = new CacheNull();

    @Override
    public boolean equals(Object obj) {
        return obj != null && CacheNull.class.equals(obj.getClass());
    }

    @Override
    public String toString() {
        return "Cache Null";
    }
}
