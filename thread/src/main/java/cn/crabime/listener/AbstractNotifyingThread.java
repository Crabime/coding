package cn.crabime.listener;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by crabime on 12/20/17.
 */
public abstract class AbstractNotifyingThread implements Runnable{

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

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
            doRun();
        }finally {
            notifyListeners();
        }
    }

    /**
     * 线程实际执行体
     */
    public abstract void doRun();
}
