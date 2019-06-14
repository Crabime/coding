package cn.crabime.redis;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by crabime on 1/17/18.
 */
public class RedisTest extends TestCase {
    private Jedis jedis;

    @Before
    public void setUp(){
        jedis = new Jedis("127.0.0.1");
    }

    @Test
    public void testString(){
        jedis.set("name", "张三");
        jedis.set("school", "technology");
//        jedis.del("name");
        jedis.append("name", " Is My Friend");
        System.out.println(jedis.get("name"));
        jedis.mset("name", "crabime", "age", "24", "company", "gooalgene");
        System.out.println(jedis.get("age"));
        jedis.incr("age");
        System.out.println(jedis.get("age"));
    }
}
