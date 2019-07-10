package env;

public class JapanWorld implements World {
    @Override
    public void sayHello(String country) {
        System.out.println("日本人说：" + country);
    }
}
