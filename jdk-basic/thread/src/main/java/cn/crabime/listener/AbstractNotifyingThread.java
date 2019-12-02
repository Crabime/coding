package cn.crabime.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by crabime on 12/20/17.
 */
public abstract class AbstractNotifyingThread implements Runnable{

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

    private final static Logger logger = LoggerFactory.getLogger(AbstractNotifyingThread.class);

    public final void addListener(final ThreadCompleteListener listener){
        this.listeners.add(listener);
    }

    public final void deleteListener(final ThreadCompleteListener listener){
        this.listeners.remove(listener);
    }

    private final void notifyListeners(){
        for (ThreadCompleteListener listener : listeners){
            listener.notifyOfComplete(this);
        }
    }

    /**
     * 实现Runnable接口,但是线程实际执行体仍然为doRun方法
     */
    public void run() {
        try {
            logger.info("开始跑任务");
            doRun();
        }finally {
            logger.info("任务运行结束，通知后续监听者");
            notifyListeners();
        }
    }

    /**
     * 线程实际执行体
     */
    public abstract void doRun();
}
