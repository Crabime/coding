package cn.crabime.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * 确保加锁的四个条件：
 * 1、互斥性。在任何时刻，只有一个客户端能持有锁
 * 2、不会发生死锁。不会因为某个持有锁的客户端由于崩溃没有主动释放锁，也能保证后续客户端仍然能正常加锁
 * 3、具有容错性。只要大部分Redis节点能正常运行，客户端都能正常加锁、解锁
 * 4、加锁解锁必须是同一客户端。不能客户端A加的锁被客户端B解了
 */
public class RedisLock {
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String LOCK_PREFIX = "dlock_";
    private static final String LOCK_MSG = "OK";
    private static final Long UNLOCK_MSG = 1L;
    // 锁默认超时时间
    private static final int DEFAULT_EXPIRE_TIME = 5 * 1000;
    // 每秒钟尝试加锁一次
    private static final long DEFAULT_SLEEP_TIME = 1000;

    private JedisPool jedisPool;

    public RedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void lock(String key, String value) throws InterruptedException {
        Jedis jedis = jedisPool.getResource();
        while (true) {
            if (setLockToRedis(key, value, jedis)) return;
        }
    }

    public void lock(String key, String value, int timeout) throws InterruptedException {
        Jedis jedis = jedisPool.getResource();

        while (timeout >= 0) {
            if (setLockToRedis(key, value, jedis)) return;
            timeout -= DEFAULT_SLEEP_TIME;
        }
    }

    public boolean tryLock(String key, String value) throws InterruptedException {
        Jedis jedis = jedisPool.getResource();
        return setLockToRedis(key, value, jedis);
    }

    private boolean setLockToRedis(String key, String value, Jedis jedis) throws InterruptedException {
        String lockKey = LOCK_PREFIX + key;
        System.out.println(Thread.currentThread().getName() + "正在尝试加锁[" + lockKey + "]");
        String result = jedis.set(lockKey, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, DEFAULT_EXPIRE_TIME);
        System.out.println(Thread.currentThread().getName() + "加锁" + (result == null ? false : result));
        if (LOCK_MSG.equals(result)) {
            jedis.close();
            return true;
        }

        // 隔一秒钟重新获取一次锁
        Thread.sleep(DEFAULT_SLEEP_TIME);
        return false;
    }

    public boolean unlock(String key, String value) {
        Jedis jedis = jedisPool.getResource();

        // 使用lua脚本作用有如下
        // 1、减少中间的网络消耗，get、del操作在一个脚本中
        // 2、这里没有采取任何jdk lock机制，因为lua脚本的执行可以看作是一次原子性操作
        // 3、如果这里用java的if/else来完成，如：if (jedis.get(key) != null) jedis.del(key);
        // 那上述操作并不是原子性操作，可能客户端A在if判断中返现key此时还存在，但是在进入if体内时，key刚好过期了
        // 注意：过期后B客户端便可以正常获取锁，由于此时A客户端根据前面if判断知道A还存在，因而并没有意识到A的key已经过期，
        // 就直接把A对应的key删除了，B正常获取到锁却莫名其妙被删，这样就会导致死锁
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(LOCK_PREFIX + key), Collections.singletonList(value));

        jedis.close();
        return UNLOCK_MSG.equals(result);
    }

    public boolean isLocked(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(LOCK_PREFIX + key);
        jedis.close();
        return result != null;
    }

    public void flushAll() {
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
    }

}