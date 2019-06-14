package cn.crabime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 生成一千万个随机数后排序
 */
public class MillionNumberGenerator {

    private final static int times = 1000 * 1000 * 10;

    public static List<Integer> generateNumbers(int nums) {
        if (nums <= 0) {
            nums = times;
        }
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int num;
        for (int i = 0; i < nums; i++) {
            num = random.nextInt(times);
            list.add(num);
        }
        return list;
    }
}
