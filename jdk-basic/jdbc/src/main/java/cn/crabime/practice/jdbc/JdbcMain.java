package cn.crabime.practice.jdbc;

public class JdbcMain {

    public static void main(String[] args) {
        JdbcMain main = new JdbcMain();
        main.sayHello("well done!");
        main.sayHello("good!");
    }

    private void sayHello(String str) {
        System.out.println("result is " + str);
        System.out.println("length is " + str.length());
    }
}
