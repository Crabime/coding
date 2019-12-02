package cn.crabime.practice.collections.hashmap;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class HashMapInCglib {

    public static void main(String[] args) {
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/lib");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HashMap.class);
        enhancer.setCallbacks(new Callback[] {new HashMapMethodInterceptor(), NoOp.INSTANCE});
        enhancer.setCallbackFilter(new HashMapCallbackFilter());
        HashMap map = (HashMap) enhancer.create(new Class[]{int.class}, new Object[]{3});
        map.put("张三", 25);
        map.put("李四", 18);
        map.put("王五", 22);
        map.put("李六", 32);
    }

    static class HashMapMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("hashmap进行扩容了");
            return proxy.invokeSuper(obj, args);
        }
    }

    static class HashMapCallbackFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            if (method.getName().equals("resize")) {
                return 0;
            }
            return 1;
        }
    }
}
