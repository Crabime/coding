package jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;

public class JDKProxyMain {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InvocationHandler handler = new MyInvocationHandler();

        Class<?> myInterfaceClazz =
                Proxy.getProxyClass(MyInterface.class.getClassLoader(), MyInterface.class);


        // 通过JDK动态代理，动态生成MyInterface对象代理类
        MyInterface myInterface = (MyInterface) myInterfaceClazz
                .getConstructor(new Class[]{ InvocationHandler.class })
                .newInstance(new Object[]{ handler });


        List<String> crabimeGameList = myInterface.gameList("crabime");

        System.out.println("User crabime game list are : " + crabimeGameList);

        Integer crabimeAge = myInterface.getAge("crabime");

        System.out.println("User crabime age is : " + crabimeAge);

    }
}
