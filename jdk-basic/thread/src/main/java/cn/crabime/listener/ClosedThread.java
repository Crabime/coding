package cn.crabime.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crabime on 12/20/17.
 */
public class ClosedThread extends AbstractNotifyingThread implements ThreadCompleteListener {

    private final static Logger logger = LoggerFactory.getLogger(ClosedThread.class);

    public void doRun() {
        logger.info("执行ClosedThread的doRun方法");
        try {
            Thread.sleep(2000); // 睡眠两秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyOfComplete(Runnable runnable) {
        logger.info("收到通知了");
    }
}
