package com.karl.boot.config;

import com.karl.base.cache.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Think
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return buildRedisCacheManager(redisTemplate);
    }

    private static RedisCacheManager buildRedisCacheManager(RedisTemplate redisTemplate) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(buildPair(new FastJsonRedisSerializer<>(Object.class)));
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(43200));
        redisCacheConfiguration = redisCacheConfiguration.computePrefixWith(name -> name + ":");
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    private static <T> RedisSerializationContext.SerializationPair buildPair(RedisSerializer<T> serializer) {
        return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    }

}
