package bundle.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static bundle.main.UrlUtils.convertToURLArray;

public class BundleCrossClLoadMain {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        URLClassLoader u1Cl = new URLClassLoader(convertToURLArray(
                "/Users/crabime/IdeaProjects/coding/bundle/module1/target/module1-1.0-SNAPSHOT.jar"));

        CustomizeClassLoader u2Cl = new CustomizeClassLoader(convertToURLArray(
                "/Users/crabime/IdeaProjects/coding/bundle/module2/target/module2-1.0-SNAPSHOT.jar"), u1Cl);

        // 正常输出Hello#sayHello方法结果
        Class<?> hClass = u1Cl.loadClass("bundle.m1.Hello");
        Object helloInstance = hClass.newInstance();
        Method sayHel = hClass.getMethod("sayHello");
        sayHel.invoke(helloInstance);

        // 加载Person，但是依赖的Hello并不在当前ClassLoader中
        Class<?> perClass = u2Cl.loadClass("bundle.m2.Person");
        Object perInstance = perClass.newInstance();
        Method greeting = perClass.getMethod("greeting", hClass);
        greeting.invoke(perInstance, helloInstance);
    }

    static class CustomizeClassLoader extends URLClassLoader {

        private ClassLoader parentCl;

        public CustomizeClassLoader(URL[] urls, ClassLoader parentCl) {
            super(urls);
            this.parentCl = parentCl;
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            Class<?> aClass = loadFromParentCl(name);
            if (aClass != null) {
                return aClass;
            }
            return super.loadClass(name);
        }

        public Class<?> loadFromParentCl(String name) {
            // 优先加载当前cl下的类
            Class<?> loadedClass = findLoadedClass(name);

            try {

                if (loadedClass == null) {
                    loadedClass = this.parentCl.loadClass(name);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return loadedClass;
        }
    }
}
