package cn.crabime;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 手把手实现阻塞队列
 * @author crabime
 */
public class CBlockingQueue<T> extends AbstractQueue<T> implements BlockingQueue<T> {

    private Lock lock = new ReentrantLock();

    /**
     * 非空
     */
    private Condition notEmpty = lock.newCondition();

    /**
     * 非满
     */
    private Condition notFull = lock.newCondition();

    /**
     * 生产的消息
     */
    private Object[] items;

    private volatile int index = 0;

    /**
     * 生产队列的大小
     */
    private int size;

    public CBlockingQueue(int size) {
        this.size = size - 1;
        items = new Object[this.size];
    }

    public void put(T i) {
        try {
            lock.lock();
            // 如果队列满了，停止继续加对象
            while (index == this.size) {
                System.out.println("队列已满");
                notFull.await();
            }
            items[index++] = i;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public T take() {
        T obj = null;
        try {
            lock.lock();
            // 如果队列为空，停止取对象
            while (index == 0) {
                System.out.println("队列已空");
                notEmpty.await();
            }
            obj = (T) items[--index];
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    public int remainingCapacity() {
        try {
            lock.lock();
            return this.size - index;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return this.index;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(T t) {
        try {
            lock.lock();

        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }
}
