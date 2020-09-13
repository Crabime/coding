package cn.crabime.practice.classloader;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class AbsPathClassTest {

    @Test
    public void loadFromRandomPlace() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        File file = new File("/Users/crabime/Downloads/temp/");
        URL url = file.toURI().toURL();
        URL[] urls = new URL[1];
        urls[0] = url;
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class<?> helloClass = urlClassLoader.loadClass("Hello");
        Method[] declaredMethods = helloClass.getDeclaredMethods();
        Optional<Method> testMethod = Arrays.stream(declaredMethods).filter(e -> "test".equals(e.getName())).findFirst();
        assertTrue(testMethod.isPresent());
        testMethod.get().invoke(helloClass.newInstance());

        Hello hello = new Hello();
        hello.test1();
    }
}