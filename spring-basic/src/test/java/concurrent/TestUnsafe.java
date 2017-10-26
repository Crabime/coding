package concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by crabime on 9/9/17.
 * 测试使用unsafe方法
 */
public class TestUnsafe {
    public static void main(String[] args) {
        Node node = new Node();
        boolean flag = node.castNext(null, new Node());
        System.out.println(flag);
    }

    private static class Node{
        volatile Node next;

        boolean castNext(Node cmp, Node val){
            /**
             * 使用CAS 方法
             * Unsafe.compareAndSwapObject(Object var1, Object var2, Object var3, Object var4)方法解析:
             * var1:操作的对象
             * var2:操作的对象属性
             * var3:var2与var3进行比较,相等才更新
             * var4:更新值
             */
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        private static final Unsafe UNSAFE;
        private static final long nextOffset;

        static {
            try {
                UNSAFE = getUnsafe();
                Class<?> k = Node.class;
                nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        /**
         * 根据Unsafe类的theUnsafe方法获取unsafe对象
         * @return Unsafe对象
         */
        public static Unsafe getUnsafe(){
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (Unsafe) f.get(null);
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
}
