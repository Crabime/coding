package cn.crabime.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class CallbackFilterDemo {

    public static void main(String[] args) {
        Callback[] callbacks = new Callback[] {
                new MethodInterceptorImpl(), NoOp.INSTANCE
        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyClass.class);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilterImpl());

        MyClass myClass = (MyClass) enhancer.create();
        myClass.method1();
        myClass.method2();
    }

    private static class CallbackFilterImpl implements CallbackFilter {

        @Override
        public int accept(Method method) {
            if (method.getName().equals("method1")) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("cglib拦截方法" + method.getName() + "之前预处理的事情");
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("cglib拦截此方法" + method.getName() + "执行完后的代理");
            return result;
        }
    }
}
