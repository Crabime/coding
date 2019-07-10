package env;

public class ChinaWorld implements World {

    @Override
    public void sayHello(String country) {
        System.out.println("中国人说:" + country);
    }
}
