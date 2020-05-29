package com.karl.base.util;

/**
 * BigDecimal转化工具类
 *
 * @author 杜永军
 * @date 2018/08/22
 */
public class BooleanUtils {

    private BooleanUtils() {
    }

    public static Boolean convertBoolean(Object obj) {
        if (obj == null) {
            return Boolean.FALSE;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj);
        } else if (obj.getClass().equals(boolean.class)) {
            return (boolean) obj;
        } else if ("true".equalsIgnoreCase(String.valueOf(obj))) {
            return Boolean.TRUE;
        } else if ("1".equals(String.valueOf(obj))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
