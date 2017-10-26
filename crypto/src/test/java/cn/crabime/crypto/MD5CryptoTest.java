package cn.crabime.crypto;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by crabime on 10/26/17.
 * 使用MD5、BCrypto进行密码加密测试
 */
public class MD5CryptoTest extends TestCase {

    private MD5Crypto crypto;

    @Before
    public void setUp(){
        crypto = new MD5Crypto();
    }

    @Test
    public void testMD5Crypto(){
        String name = "crabime";
        System.out.println(crypto.md5(name));
    }

    @Test
    public void testBCryptEncoder(){
        String name = crypto.bcrypt("crabime");
        System.out.println(name);
    }

    @Test
    public void testBCryptEncoderMatches(){
        boolean result =
                crypto.matches("crabime", "$2a$10$RKilTR3a6LXeG0H83PTCMOwt4MzJVDE9TmCkkpsOcoSr6STYPnDbK");
        assertTrue(result);
    }
}
