package cn.crabime.mvc.basic.service;

import static org.junit.Assert.*;

public class SimpleCacheServiceTest {

    @org.junit.Test
    public void getProperName() {
        SimpleCacheService service = new SimpleCacheService();
        service.put("张三", 25);
        String name = service.getProperName("张三");
        assertEquals("张三1", name);

        service.put("张三1", 26);
        name = service.getProperName("张三");
        assertEquals("张三2", name);

        service.put("张三2", 28);
        name = service.getProperName("张三");
        assertEquals("张三3", name);
    }
}