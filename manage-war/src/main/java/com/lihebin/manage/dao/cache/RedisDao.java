package com.lihebin.manage.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2018/12/2.
 */
@Repository
public class RedisDao{


    private static final String KEY_PREFIX_VALUE = "merchant_manage_";

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheValue(String key, String value, long time, TimeUnit timeUnit) {
        if(value.equalsIgnoreCase(getValue(key))) {
            return false;
        }
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            boolean status = valueOps.setIfAbsent(key, value);
            if (!status) {
                return false;
            }
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     * @return
     */
    public boolean cacheValue(String key, String value) {
        return cacheValue(key, value, -1, TimeUnit.SECONDS);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public boolean containsValueKey(String key) {
        return containsKey(key);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public boolean containsSetKey(String key) {
        return containsKey(key);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public boolean containsListKey(String key) {
        return containsKey(key);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(key);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeValue(String key) {
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        return remove(key);
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeSet(String key) {
        return remove(key);
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeList(String key) {
        return remove(key);
    }

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheSet(String key, String value, long time) {
        try {
            SetOperations<String, String> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @return
     */
    public boolean cacheSet(String key, String value) {
        return cacheSet(key, value, -1);
    }

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheSet(String key, Set<String> value, long time) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(key, value.toArray(new String[value.size()]));
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 缓存Set
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String k, Set<String> v) {
        return cacheSet(k, v, -1);
    }

    /**
     * 获取Set
     *
     * @param k
     * @return
     */
    public Set<String> getSet(String k) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            return setOps.members(k);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheList(String k, String v, long time) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPush(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheList(String k, String v) {
        return cacheList(k, v, -1);
    }

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheList(String k, List<String> v, long time) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPushAll(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheList(String k, List<String> v) {
        return cacheList(k, v, -1);
    }

    /**
     * 获取List
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<String> getList(String k, long start, long end) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.range( k, start, end);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 获取页码
     *
     * @param key
     * @return
     */
    public long getListSize(String key) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Throwable t) {
            return 0;
        }
    }

    /**
     * 移除list缓存
     *
     * @param k
     * @return
     */
    public boolean removeOneOfList(String k) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPop(k);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 移除缓存
     *
     * @param key key
     * @return boolean
     */
    private boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
