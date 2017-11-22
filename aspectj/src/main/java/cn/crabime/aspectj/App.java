package cn.crabime.aspectj;

/**
 * Created by crabime on 11/22/17.
 */
public class App {
    public void hello(){
        System.out.println("this's hello method");
    }

    public static void main(String[] args) {
        App app = new App();
        app.hello();
    }
}
