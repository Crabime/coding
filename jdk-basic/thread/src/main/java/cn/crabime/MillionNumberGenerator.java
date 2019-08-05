package cn.crabime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成一千万个随机数后排序
 */
public class MillionNumberGenerator {

    private final static int times = 1000 * 1000 * 10;

    private static Random random = new Random();

    public static List<Integer> generateNumbers(int nums) {
        if (nums <= 0) {
            nums = times;
        }
        List<Integer> list = new ArrayList<>();
        int num;
        for (int i = 0; i < nums; i++) {
            num = random.nextInt(times);
            list.add(num);
        }
        return list;
    }

    /**
     * 生成一个随机数
     */
    public static Integer generateSingleNumber(int nums) {
        return random.nextInt(nums);
    }
}
