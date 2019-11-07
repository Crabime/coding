package cn.crabime.lock;

import junit.framework.TestCase;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Created by crabime on 1/17/18.
 */
public class RedisTest extends TestCase {
    private RedisLock lock;

    @Before
    public void setUp(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "47.103.3.149", 63791, 3000, "123");
        lock = new RedisLock(jedisPool);
    }

    @Test
    public void testSingleThreadAddLock() throws InterruptedException {
        String lockKey = "l1";
        String mainLV = UUID.randomUUID().toString();
        lock.lock(lockKey, mainLV);
    }

    @Test
    public void testShouldWaitWhenOneLockAndAnotherShouldWait() throws InterruptedException {
        String lockKey1 = "l1";
        // 生成lock key
        String mainLV = UUID.randomUUID().toString();
        Thread t = new Thread(() -> {
            try {
                String lv = UUID.randomUUID().toString();
                lock.lock(lockKey1, lv);
                System.out.println(Thread.currentThread().getName() + "正在执行任务...");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "执行结束");
                lock.unlock(lockKey1, lv);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.setName("自定义线程一");
        t.start();

        lock.lock(lockKey1, mainLV, 3000);
        // 防止junit直接退出
        t.join();
    }
}
