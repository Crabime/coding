package cn.crabime;

import java.util.Arrays;

/**
 * 演示如何使用jdk1.8新增的WorkStealingPool
 */
public class WorkStealingPoolExample {

    public static void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int leftKey = nums[left];
        int i = left, j = right;
        while (i < j) {
            while (nums[j] >= leftKey && j > i) {
                j--;
            }
            while (nums[i] <= leftKey && j > i) {
                i++;
            }
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        nums[left] = nums[i];
        nums[i] = leftKey;
        quickSort(nums, left, i-1);
        quickSort(nums, i+1, right);
    }

    public static void main(String[] args) {
        int[] num = {3,45,78,64,52,11,64,55,99,11,18};
        quickSort(num, 0, num.length - 1);
        System.out.println(Arrays.toString(num));
    }
}
