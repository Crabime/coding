package cn.crabime.design.mode.chain;

public class EnvMain {

    public static void main(String[] args) {
        Handler h1 = new MyHandler("h1");
        Handler h2 = new MyHandler("h2");
        h1.setNextHandler(h2);

        System.out.println("======处理h1请求=====");
        h1.handleRequest("hello world");
    }
}
