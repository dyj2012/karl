package com.karl.base.util;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Map工具类
 *
 * @author 杜永军
 * @date 2020/5/29
 */
public class MapUtils {

    public static void entityToMap(Object entity, Map<String, String> map, KeyCallback keyCallback, ValueCallback vCallback) {
        try {
            Method[] methods = entity.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String name = method.getName().substring(3);
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    Object value = method.invoke(entity);
                    map.put(keyCallback.call(name), vCallback.call(name, value));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface KeyCallback {
        /**
         * 回调
         *
         * @param key
         * @return
         */
        String call(String key);
    }

    public interface ValueCallback {
        /**
         * 回调
         *
         * @param key
         * @param value
         * @return
         */
        String call(String key, Object value);
    }
}
