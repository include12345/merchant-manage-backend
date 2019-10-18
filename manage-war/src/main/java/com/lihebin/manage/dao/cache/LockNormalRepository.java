package com.lihebin.manage.dao.cache;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2019/10/18.
 */
@Slf4j
@Component
public class LockNormalRepository {

    @Autowired
    private RedissonClient redissonClient;

    // 加锁
    public boolean lock(String lockName) {
        try {
            if (redissonClient == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            // 锁10秒后自动释放，防止死锁
            lock.lock(10, TimeUnit.SECONDS);

            log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
            return false;
        }
    }


    public boolean tryLock(String lockName, long leaseTime, long waitTime, int tries, long sleepTime) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockName);
        int tried = 0;
        do {
            tried++;
            if (lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS)) {
                return true;
            } else {
                if (tried != tries) {
                    Thread.sleep(sleepTime);
                }
            }
        } while (tried == tries);
            return false;
    }


    public boolean tryLockAndExe(String lockName, long leaseTime, long waitTime, int tries, long sleepTime, ExeCallback exeCallback) throws InterruptedException {
        try {
            if (tryLock(lockName, leaseTime, waitTime, tries, sleepTime)) {
                exeCallback.callback();
                return true;
            } else {
                return false;
            }
        } finally {
            unlock(lockName);
        }
    }

    // 释放锁
    public boolean unlock(String lockName) {
        try {
            if (redissonClient == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            lock.unlock();
            log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
            return false;
        }
    }
}
