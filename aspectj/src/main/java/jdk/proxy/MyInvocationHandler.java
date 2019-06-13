package jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class MyInvocationHandler implements InvocationHandler {

    /**
     *
     * @param proxy 被代理对象
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals("gameList")) {
            String userName = String.valueOf(args[0]);
            switch (userName) {
                case "crabime":
                    result = new ArrayList<>(Arrays.asList("王者荣耀", "QQ飞车", "英雄联盟"));
                    break;
                default:
                    System.err.println("不认识的用户名：" + userName);
                    break;
            }
        }

        return result;
    }
}
