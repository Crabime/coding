package cn.crabime.lock.benchmark;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

public class KeyGeneratorThread implements Runnable {

    private JedisPool jedisPool;

    private Random random;

    /**
     * 单个线程循环次数
     */
    private int times;

    protected KeyGeneratorThread(JedisPool jedisPool, int times) {
        this.jedisPool = jedisPool;
        this.random = new Random();
        this.times = times;
    }

    @Override
    public void run() {
        Jedis jedis = jedisPool.getResource();
        for (int i = 0; i < times; i++) {
            gen(jedis);
        }
        jedis.close();
    }

    private void gen(Jedis jedis) {
        String key = RandomUtil.generate(5);
        int len = random.nextInt(30);

        String val = RandomUtil.generate(len);

        jedis.set(key, val);

    }
}
