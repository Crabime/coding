package cn.crabime;

import java.util.Arrays;
import java.util.List;

/**
 * 直接使用快排（单线程）对一千万数字进行排序
 */
public class NonWorkStealingPoolTask {

    private final static int CUTOFF = 10;

    public static void quickSort(int[] nums, int left, int right) {
        if (left + CUTOFF < right) {
            int pivot = median(nums, left, right);
            int i = left, j = right;
            for (;;) {
                while (nums[++i] < pivot) {}
                while (nums[--j] > pivot) {}
                if (i < j)
                    swap(nums, i, j);
                else
                    break;
            }
            swap(nums, i, j - 1);
            quickSort(nums, left, i - 1);
            quickSort(nums, i + 1, right);
        } else {
            int j;
            for (int i = left + 1; i <= right; i++) {
                int tmp = nums[i];
                for (j = i; j > 0 && nums[j - 1] > tmp; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[j] = tmp;
            }
        }
    }

    private static int median(int[] nums, int left, int right) {
        int center = (left + right) / 2;
        if (nums[left] > nums[center])
            swap(nums, left, center);
        if (nums[left] > nums[right])
            swap(nums, left, right);
        if (nums[center] > nums[right])
            swap(nums, center, right);
        swap(nums, center, right - 1);
        return nums[right - 1];
    }

    private static void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

    public static void main(String[] args) {
        List<Integer> list = MillionNumberGenerator.generateNumbers(3000000);
        int[] num = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            num[i] = list.get(i);
        }
        long start = System.currentTimeMillis();
        quickSort(num, 0, num.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("总共耗时：" + (end - start));

    }

}
