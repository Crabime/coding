package cn.crabime.listener;

/**
 * Created by crabime on 12/20/17.
 */
public class ClosedThread extends AbstractNotifyingThread implements ThreadCompleteListener {
    public void doRun() {
        System.out.println("执行ClosedThread的doRun方法");
        try {
            Thread.sleep(2000); // 睡眠两秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyOfComplete(Runnable runnable) {

        System.out.println(Thread.currentThread().getName() + "收到了通知");
    }
}
