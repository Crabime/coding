package cn.crabime.practice.proxy;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

public class HelloImplTest {

    @Before
    public void setUp() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    }

    @Test
    public void testProxyHello() {
        Hello hello = new HelloImpl();
        Hello proxyHello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), new MyProxyInvocationHandler(hello));
        proxyHello.sayHello("你好");
    }

}