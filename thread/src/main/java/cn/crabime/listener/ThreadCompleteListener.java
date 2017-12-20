package cn.crabime.listener;

/**
 * Created by crabime on 12/20/17.
 * 线程事件完成通知器
 */
public interface ThreadCompleteListener {

    void notifyOfComplete(final Runnable runnable);
}
