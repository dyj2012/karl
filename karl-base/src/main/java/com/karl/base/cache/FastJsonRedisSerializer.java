package com.karl.base.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * FastJson序列化
 *
 * @author Think
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;

    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    public FastJsonRedisSerializer(Class<T> type) {
        super();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setSerializeConfig(new SerializeConfig());
        fastJsonConfig.setParserConfig(new ParserConfig());
        fastJsonConfig.getParserConfig().setAutoTypeSupport(true);
        this.clazz = type;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(t, fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializerFeatures());
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            String str = new String(bytes, fastJsonConfig.getCharset());
            return (T) JSON.parseObject(str, clazz, fastJsonConfig.getParserConfig(), fastJsonConfig.getFeatures());
        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
    }

}
