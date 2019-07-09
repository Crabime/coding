package cn.crabime.practice.proxy;

import java.lang.reflect.*;

public class MyProxyInvocationHandler implements InvocationHandler {

    private static Constructor<?> CONSTRUCTOR;

    static {
        try {
            CONSTRUCTOR = Proxy.getProxyClass(HelloImpl.class.getClassLoader(),
                    HelloImpl.class.getInterfaces()).getConstructor(InvocationHandler.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Hello hello;

    MyProxyInvocationHandler(Hello hello) {
        super();
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("来到这里了");
        return method.invoke(this.hello, args);
    }

    public static Hello newProxy(Hello hello) {
        try {
            return (Hello) CONSTRUCTOR.newInstance(new MyProxyInvocationHandler(hello));
        } catch (InstantiationException ex) {
            throw new IllegalStateException(ex);
        } catch (InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
