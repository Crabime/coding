package cn.crabime.listener;

/**
 * Created by crabime on 12/20/17.
 * 主执行函数
 */
public class ThreadListenerMain {
    public static void main(String[] args) {
        ClosedThread thread1 = new ClosedThread();
        thread1.addListener(thread1);
        new Thread(thread1).start();
    }
}
