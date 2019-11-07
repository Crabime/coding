package cn.crabime.lock.benchmark;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

public class BenchMarkMain {

    public final static Integer THREAD_COUNT = 100;

    public final static Integer TIMES = 200;

    public static void main(String[] args) {
        // redis服务器仍未开启RDB/AOF模式，建议先打开后尝试，性能有更大折损
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxWaitMillis(5000);
        JedisPool jedisPool = new JedisPool(poolConfig, "47.103.3.149", 63791, 3000, "123");

        for (int i = 0; i < THREAD_COUNT; i++) {
            String name = "线程" + i;
            Thread t = new Thread(new KeyGeneratorThread(jedisPool, TIMES));
            t.setName(name);
            System.out.println(name + "开始工作...");
            t.start();
        }
    }
}
