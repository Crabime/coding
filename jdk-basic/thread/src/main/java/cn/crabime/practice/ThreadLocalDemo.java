package cn.crabime.practice;

/**
 * 尝试JDK中ThreadLocal类
 *
 * @author crabime
 * create at 2019/10/11
 */
public class ThreadLocalDemo {

    protected static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Police(), "警察");
        Thread t2 = new Thread(new Thief(), "小偷");
        t1.start();
        t2.start();
    }

    interface Process extends Runnable {

        void start();

        void end();
    }

    static abstract class Action implements Process {

        @Override
        public void run() {

            start();

            // 模拟中间停顿时间
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            end();
        }
    }

    static class Police extends Action {

        @Override
        public void start() {
            String str = "police";
            System.out.println("开始报道");
            threadLocal.set(str);
        }

        @Override
        public void end() {
            System.out.println("报道结束，拿到的ID为：" + threadLocal.get());
        }
    }

    static class Thief extends Action {

        @Override
        public void start() {
            String str = "thief";
            System.out.println("开始报名");
            threadLocal.set(str);
        }

        @Override
        public void end() {
            System.out.println("抓捕结束，抓到了" + threadLocal.get());
        }

    }
}
