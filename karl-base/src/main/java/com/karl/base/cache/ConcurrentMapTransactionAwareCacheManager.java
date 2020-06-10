package com.karl.base.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Callable;

/**
 * jvm内存缓存
 *
 * @author 杜永军
 * @date 2019/9/11
 */
public class ConcurrentMapTransactionAwareCacheManager extends TransactionAwareCacheManagerProxy {

    public ConcurrentMapTransactionAwareCacheManager() {
        super(new ConcurrentMapCacheManager());
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if (cache == null) {
            return null;
        }
        return new CopyCache(cache);
    }

    static class CopyCache implements Cache {
        private Cache cache;
        FastJsonRedisSerializer<Object> s = new FastJsonRedisSerializer<>(Object.class);

        public CopyCache(Cache cache) {
            this.cache = cache;
        }

        @Override
        public String getName() {
            return cache.getName();
        }

        @Override
        public Object getNativeCache() {
            return cache.getNativeCache();
        }

        @Override
        public ValueWrapper get(Object key) {
            //模拟redis 序列化key
            new StringRedisSerializer().serialize((String) key);
            ValueWrapper value = cache.get(key);
            if (value == null) {
                return null;
            }
            return new CopyValueWrapper(value, s);
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            return cache.get(key, type);
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return cache.get(key, valueLoader);
        }

        @Override
        public void put(Object key, Object value) {
            cache.put(key, s.serialize(value));
        }

        @Override
        public ValueWrapper putIfAbsent(Object key, Object value) {
            return new CopyValueWrapper(cache.putIfAbsent(key, s.serialize(value)), s);
        }

        @Override
        public void evict(Object key) {
            cache.evict(key);
        }

        @Override
        public void clear() {
            cache.clear();
        }
    }

    static class CopyValueWrapper implements Cache.ValueWrapper {
        private Cache.ValueWrapper value;
        private FastJsonRedisSerializer s;

        public CopyValueWrapper(Cache.ValueWrapper value, FastJsonRedisSerializer s) {
            this.value = value;
            this.s = s;
        }

        @Override
        public Object get() {
            Object v = value.get();
            if (v == null) {
                return null;
            }
            if (v instanceof byte[]) {
                return s.deserialize((byte[]) v);
            }
            return null;
        }
    }

}
