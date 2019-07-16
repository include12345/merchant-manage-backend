package com.lihebin.manage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //按cacheName设置spring-cache-redis缓存时间
        Map<String, Long> expires = new HashMap<>();
        expires.put("tenMinutes", (long) (60));
        expires.put("oneHour", (long) (60*60));
        expires.put("oneDay", (long) (60*60*24));
        expires.put("permanent", (long) 0);
        cacheManager.setExpires(expires);
        return cacheManager;
    }


    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler(){
        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            logger.warn("handleCacheGetError in redis: {}", exception.getMessage());
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            logger.warn("handleCachePutError in redis: {}", exception.getMessage());
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            logger.warn("handleCacheEvictError in redis: {}", exception.getMessage());
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            logger.warn("handleCacheClearError in redis: {}", exception.getMessage());
        }};
    }
}
