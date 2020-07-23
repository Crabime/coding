package cn.crabime.practice.proxy;

import java.lang.reflect.*;

public class MyProxyInvocationHandler implements InvocationHandler {

    private Object object;

    MyProxyInvocationHandler(Object object) {
        super();
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("来到这里了");
        return method.invoke(this.object, args);
    }
}
