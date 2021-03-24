package bundle.main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static bundle.main.UrlUtils.convertToURLArray;

/**
 * 跨ClassLoader获取实例
 */
public class BundleCrossClMain {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        URLClassLoader u1Cl = new URLClassLoader(convertToURLArray(
                "/Users/crabime/IdeaProjects/coding/bundle/module1/target/module1-1.0-SNAPSHOT.jar"));

        URLClassLoader u2Cl = new URLClassLoader(convertToURLArray(
                "/Users/crabime/IdeaProjects/coding/bundle/module2/target/module2-1.0-SNAPSHOT.jar",
                        "/Users/crabime/IdeaProjects/coding/bundle/module1/target/module1-1.0-SNAPSHOT.jar"));

        // 正常输出Hello#sayHello方法结果
        Class<?> hClass = u1Cl.loadClass("bundle.m1.Hello");
        Object helloInstance = hClass.newInstance();
        Method sayHel = hClass.getMethod("sayHello");
        sayHel.invoke(helloInstance);

        // 加载Person，依赖的Hello类必须使用当前ClassLoader加载，如果使用u1cl还是会报错
        Class<?> perClass = u2Cl.loadClass("bundle.m2.Person");
        Class<?> u2hClass = u2Cl.loadClass("bundle.m1.Hello");
        Object perInstance = perClass.newInstance();
        Method greeting = perClass.getMethod("greeting", u2hClass);

        Object u2HelloInstance = u2hClass.newInstance();
        greeting.invoke(perInstance, u2HelloInstance);
    }

}
