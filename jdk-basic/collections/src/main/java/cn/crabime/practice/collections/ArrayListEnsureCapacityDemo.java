package cn.crabime.practice.collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 查看ArrayList扩容策略
 * @author crabime
 */
public class ArrayListEnsureCapacityDemo {

    private final Logger logger = LoggerFactory.getLogger(ArrayListEnsureCapacityDemo.class);

   private List<Integer> list = new ArrayList<>(8);

   private int getListLength() {
       Class<? extends List> listClass = list.getClass();
       try {
           Field data = listClass.getDeclaredField("elementData");
           data.setAccessible(true);
           Object[] elementData = (Object[]) data.get(list);
           return elementData.length;
       } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
       }
       return -1;
   }

   public void addItems(int increaseSize) {
       Random random = new Random();
       for (int i = 0; i < increaseSize; i++) {
           list.add(random.nextInt(increaseSize));
           int length = getListLength();
           logger.info("当前list数组容量为{}", length);
       }
   }

    public static void main(String[] args) {
        ArrayListEnsureCapacityDemo capacityDemo = new ArrayListEnsureCapacityDemo();
        capacityDemo.addItems(9);
    }
}
