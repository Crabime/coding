package cn.crabime.jvm.gc;

/**
 * CMS DEMO演示篇
 * -Xmx10m -Xms10m -Xmn5m -XX:+PrintGCDetails
 */
public class CMSPracticeDemo {

    private final static int _1MB = 1024;

    public void start() {
        System.out.println("======开始======");
        byte[] b1 = new byte[_1MB * 5];
        byte[] b2 = new byte[_1MB * 5];
        byte[] b3 = new byte[_1MB * 5];
        System.out.println("======结束======");
    }

    public static void main(String[] args) {
        final CMSPracticeDemo demo = new CMSPracticeDemo();
        Thread t = new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {
                while (count++ < 3) {
                    demo.start();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        demo.start();
    }

}
