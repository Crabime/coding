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

    /**
     * 数组内元素长度
     */
    int count;

    /**
     * insert指针
     */
    int putIndex = 0;

    /**
     * take指针
     */
    int takeIndex = 0;

    /**
     * 生产队列总大小
     */
    private int size;

    public CBlockingQueue(int size) {
        this.size = size;
        items = new Object[this.size];
    }

    /**
     * put操作如果队列已满，待插入的元素会等待队列有位置时再插入，等待过程不会改变队列大小
     */
    @Override
    public void put(T i) {
        lock.lock();
        try {
            // 如果队列满了，停止继续加对象
            while (count == this.size) {
                notFull.await();
            }
            enqueue(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 插入元素到队列尾，最多等待timeout时间
     */
    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        lock.lock();
        try {
            while (count == this.size) {
                // 如果等待时间为负数，表示等待超时了
                if (nanos < 0)
                    return false;
                nanos = notFull.awaitNanos(nanos);
            }
            enqueue(t);
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 插入一个元素，若插入成功返回true
     */
    @Override
    public boolean offer(T t) {
        lock.lock();
        try {
            if (count == this.size) {
                return false;
            } else {
                enqueue(t);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        T obj = null;
        try {
            lock.lock();
            // 如果队列为空，停止取对象
            while (count == 0) {
                notEmpty.await();
            }
            obj = dequeue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    /**
     * 队列等待一段时间，如果这段时间仍然没有元素进入，直接返回null
     */
    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        lock.lock();
        long nanos = unit.toNanos(timeout);
        try {
            while (count == 0) {
                if (nanos < 0) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        lock.lock();
        return false;
    }

    public int remainingCapacity() {
        try {
            lock.lock();
            return this.size - count;
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

    // TODO: 8/6/19 完成迭代器部分
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return this.count;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T poll() {
        lock.lock();
        try {
            return count == 0 ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队列首个元素但不删除元素，如果队列为空，返回null
     */
    @Override
    public T peek() {
        lock.lock();
        try {
            if (count == 0) {
                return null;
            }
            return (T) items[takeIndex];
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(T i) {
        items[putIndex] = i;
        // 如果插入元素指针到达末尾，等待队列首个元素被消费，消费完后，put指针重新回到0的位置
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
        notEmpty.signal();
    }

    private T dequeue() {
        T t = (T) items[takeIndex];
        items[takeIndex] = null;
        // 如果从队列中获取元素达到末尾，等待队列中重现填充元素，take指针回到0的位置
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        notFull.signal();
        return t;
    }
}
