/**
 * 元年软件
 *
 * @author
 * @date 2018年4月19日 上午9:46:23
 * @version V1.0
 */
package com.karl.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Json数据转换工具类
 *
 * @date 2018年4月19日 上午9:46:23
 */
public class JsonUtils {

    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.WriteDateUseDateFormat,
//	        SerializerFeature.WriteMapNullValue, // 输出空置字段
//			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
//			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
//			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
//			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
            //SerializerFeature.DisableCircularReferenceDetect, //消除循环引用
            SerializerFeature.WriteDateUseDateFormat};

    /**
     * 私有构造函数，防止实例化
     */
    private JsonUtils() {

    }

    /**
     * JSON对象序列化
     *
     * @param obj 要转换的对象
     * @return
     */
    public static String toJSON(Object obj) {
        return JSON.toJSONString(obj, FEATURES);
    }

    /**
     * 将json字符串反序列化成对象
     *
     * @param json  字符串
     * @param clazz 对象类型
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 转换带有泛型的list对象
     *
     * @param json  字符串
     * @param clazz 对象类型
     * @return
     */
    public static <T> List<T> fromJSONList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
