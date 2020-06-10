package com.karl.boot.config;

import com.karl.base.cache.ConcurrentMapTransactionAwareCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author Think
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory connectionFactory) {
        return new ConcurrentMapTransactionAwareCacheManager();
//        return RedisCacheManagerFactory.buildRedisCacheManager(connectionFactory);
    }

}
