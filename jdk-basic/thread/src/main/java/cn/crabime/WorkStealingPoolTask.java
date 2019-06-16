package cn.crabime;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 演示如何使用jdk1.8新增的WorkStealingPool
 */
public class WorkStealingPoolTask extends RecursiveAction {

    private static final int CUTOFF = 10;

    private int[] nums;

    private int left;

    private int right;

    public WorkStealingPoolTask(int[] nums, int left, int right) {
        this.nums = nums;
        this.left = left;
        this.right = right;
    }

    public static void insertSort(int[] nums) {
        int j;
        for (int i = 1; i < nums.length; i++) {
            int tmp = nums[i];
            for (j = i; j > 0 && tmp < nums[j - 1]; j--) {
                nums[j] = nums[j - 1];
            }
            nums[j] = tmp;
        }
    }

    public static void quickSort(int[] nums, int left, int right) {
        if (left + CUTOFF < right) {
            int pivot = median3(nums, left, right);
            int i = left, j = right - 1;
            for (;;) {
                while (nums[++i] < pivot) {}
                while (nums[--j] > pivot) {}
                if (i < j) {
                    swap(nums, i, j);
                } else {
                    // i >= j情况即可直接跳出循环
                    break;
                }
            }

            // i此时在j右侧，将num[i]值与pivot进行互换
            swap(nums, i, right - 1);
            quickSort(nums, left, i - 1);
            quickSort(nums, i + 1, right);
        } else {
            insertSort(nums);
        }
    }

    /**
     * 三数中值法获取pivot值
     */
    public static int median3(int[] nums, int left, int right) {
        int center = (left + right) / 2;
        if (nums[left] > nums[center]) {
            swap(nums, left, center);
        }
        if (nums[left] > nums[right]) {
            swap(nums, left, right);
        }
        if (nums[center] > nums[right]) {
            swap(nums, center, right);
        }

        // center与right－1位置元素进行交换
        swap(nums, center, right - 1);
        return nums[right - 1];
    }

    private static void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> list = MillionNumberGenerator.generateNumbers(30000000);
        int[] num = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            num[i] = list.get(i);
        }
        WorkStealingPoolTask stealingPoolTask = new WorkStealingPoolTask(num, 0, num.length - 1);
        forkJoinPool.submit(stealingPoolTask);
        long start = System.currentTimeMillis();
        try {
            stealingPoolTask.get();
            long end = System.currentTimeMillis();
            System.out.println("总共耗时：" + (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void compute() {
        WorkStealingPoolTask task1 = null;
        WorkStealingPoolTask task2 = null;
        //在左右间距小于10时，可直接采用插入排序
        if (left + CUTOFF < right) {
            int pivot = median3(nums, left, right);
            int i = left, j = right - 1;
            for (;;) {
                while (nums[++i] < pivot) {}
                while (nums[--j] > pivot) {}
                if (i < j) {
                    swap(nums, i, j);
                } else {
                    // i >= j情况即可直接跳出循环
                    break;
                }
            }

            // i此时在j右侧，将num[i]值与pivot进行互换
            swap(nums, i, right - 1);

            task1 = new WorkStealingPoolTask(nums, left, i - 1);
            task1.fork();
            task2 = new WorkStealingPoolTask(nums, i + 1, right);
            task2.fork();
            if (!task1.isDone()) {
                task1.join();
            }
            if (!task2.isDone()) {
                task2.join();
            }
        } else {
            insertSort(nums);
        }
    }
}
