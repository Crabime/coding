package cn.crabime.practice.proxy;

public class HelloMain {

    public static void main(String[] args) {
        // 将代理生成的class文件输出到文件中
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Hello hello = new HelloImpl();
        Hello h = MyProxyInvocationHandler.newProxy(hello);

        hello.sayHello("最近有点意思啊");

        System.out.println("======================");

        h.sayHello("最近有点意思啊");
    }
}
