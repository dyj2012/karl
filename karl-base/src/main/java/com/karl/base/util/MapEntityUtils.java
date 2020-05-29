package com.karl.base.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 *
 * @author 杜永军
 * @date 2020/5/29
 */
@Slf4j
public class MapEntityUtils {

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


    /**
     * 缓存类的属性信息
     */
    private static Map<String, ConvertEntryItem> convertItemCache = new HashMap<>();

    /**
     * Map转换为Entry
     *
     * @param <T>
     * @param valueMap    泛型类型为<String,Object>
     * @param entityClass
     * @param prefix      从map中取值的key为   prefix + attr
     * @return
     */
    public static <T> T convert(Map<String, String> valueMap, Class<T> entityClass, String prefix) {
        ConvertEntryItem convertItem = convertItemCache.get(entityClass.getName());
        if (convertItem == null) {
            convertItem = ConvertEntryItem.createConvertItem(entityClass);
            convertItemCache.put(entityClass.getName(), convertItem);
        }

        //entityClass 的可访问字段名
        List<String> fieldNameList = convertItem.getFieldNameList();
        //字段名和对应的set方法映射
        Map<String, Method> fieldSetMethodMap = convertItem.getFieldSetMethodMap();

        T entity;
        try {
            entity = entityClass.newInstance();
        } catch (Exception e) {
            log.error("create " + entityClass.getName() + " instance failed, Do it has a empty constructed function ?", e);
            return null;
        }

        String fieldValue;
        Method m;
        Class<?>[] parameterTypes;
        Object targetValue;
        //遍历字段列表，分别调用每个字段的set方法
        for (String fieldName : fieldNameList) {
            if (prefix == null) {
                fieldValue = valueMap.get(fieldName);
            } else {
                fieldValue = valueMap.get(prefix + fieldName);
            }

            if (fieldValue == null) {
                continue;
            }
            m = fieldSetMethodMap.get(fieldName);
            if (m == null) {
                continue;
            }

            //set方法的参数类型
            parameterTypes = m.getParameterTypes();
            if (parameterTypes.length != 1) {
                continue;  //只支持单个参数的set方法
            }

            //如果第一个参数类型和当前类型相同，则直接使用
            if (parameterTypes[0].isAssignableFrom(fieldValue.getClass())) {
                targetValue = fieldValue;
            } else {
                //转换当前的值为目标参数类型
                targetValue = MapEntityUtils.convert(parameterTypes[0], fieldValue);
            }

            if (targetValue != null) {
                try {
                    //调用set方法进行赋值
                    m.invoke(entity, targetValue);
                } catch (Exception e) {
                    log.error("set value failed:{method=" + m.getName() + ",value=" + targetValue + "}", e);
                }
            }
        }

        return entity;
    }

    private static Object convert(Class<?> parameterType, String fieldValue) {
        if (parameterType == Integer.class || parameterType == int.class) {
            return Integer.parseInt(fieldValue);
        }
        if (parameterType == Long.class || parameterType == long.class) {
            return Long.parseLong(fieldValue);
        }
        if (parameterType == Boolean.class || parameterType == boolean.class) {
            return Boolean.parseBoolean(fieldValue);
        }
        if (parameterType == LocalDateTime.class) {
            return LocalDateTime.parse(fieldValue);
        }
        if (parameterType == LocalDate.class) {
            return LocalDateTime.parse(fieldValue);
        }
        if (parameterType == Double.class || parameterType == double.class) {
            return Double.parseDouble(fieldValue);
        }
        if (parameterType.isEnum()) {
            Object[] enumConstants = parameterType.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                if (fieldValue.equals(enumConstant.toString())) {
                    return enumConstant;
                }
            }
            throw new RuntimeException(String.format("%s未定义的枚举转化异常,value:%s", parameterType, fieldValue));
        }
        throw new RuntimeException(String.format("%s未定义的转化异常,value:%s", parameterType, fieldValue));
    }

    public static void main(String[] args) {

    }

    static class ConvertEntryItem {
        private List<String> fieldNameList = new ArrayList<>();
        private Map<String, Method> fieldSetMethodMap = new HashMap<>();

        private ConvertEntryItem() {
        }

        public List<String> getFieldNameList() {
            return fieldNameList;
        }

        public Map<String, Method> getFieldSetMethodMap() {
            return fieldSetMethodMap;
        }

        private void parseEntry(Class<?> cls) {
            if (!cls.getSuperclass().equals(Object.class)) {
                parseEntry(cls.getSuperclass());
            }
            Field[] allField = cls.getDeclaredFields();
            Method m;
            String methodName;
            for (Field f : allField) {
                methodName = f.getName();
                methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
                try {
                    //只返回和当前字段类型相符的set方法，不支持多参数以及不同类型的set方法
                    m = cls.getDeclaredMethod(methodName, f.getType());
                    if (m != null) {
                        fieldNameList.add(f.getName());
                        fieldSetMethodMap.put(f.getName(), m);
                    }
                } catch (SecurityException e) {
                    log.error("parseEntry failed: SecurityException", e);
                } catch (NoSuchMethodException e) {
                    log.info("NoSuchMethod in " + cls.getName() + ": " + methodName);
                }
            }
        }

        public static ConvertEntryItem createConvertItem(Class<?> cls) {
            ConvertEntryItem ci = new ConvertEntryItem();
            ci.parseEntry(cls);
            return ci;
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
