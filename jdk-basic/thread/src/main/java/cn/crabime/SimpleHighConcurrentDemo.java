package cn.crabime;

/**
 * 高并发下的锁自旋与synchronized操作性能比对
 */
public class SimpleHighConcurrentDemo {

    public int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        SimpleHighConcurrentDemo highConcurrentDemo = new SimpleHighConcurrentDemo();
        int r = highConcurrentDemo.fibonacci(100);
        System.out.println(r);
    }
}
