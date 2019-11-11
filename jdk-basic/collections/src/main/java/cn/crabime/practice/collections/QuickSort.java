package cn.crabime.practice.collections;

import java.util.Arrays;

public class QuickSort {

    private final static int CUTOFF = 10;

    public void quickSort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    private void swap(int[] arr, int left, int right) {
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }

    private int median(int[] arr, int left, int right) {
        int center = (left + right) / 2;

        if (arr[left] > arr[center]) {
            swap(arr, left, center);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }

        if (arr[center] > arr[right]) {
            swap(arr, center, right);
        }
        swap(arr, center, right - 1);
        return arr[right - 1];
    }

    public void quickSort(int[] arr, int left, int right) {
        if (right - left > CUTOFF) {
            int pivot = median(arr, left, right);
            int i = left, j = right - 1;
            for (;;) {
                while (arr[++i] < pivot) {}
                while (arr[--j] > pivot) {}
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    break;
                }
            }
            swap(arr, i, right - 1);
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);

        } else {
            insertSort(arr, left, right);
        }
    }

    private void insertSort(int[] arr, int left, int right) {
        int j;
        for (int i = left; i <= right; i++) {
            int tmp = arr[i];
            for (j = i; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {-1, -2, 0, 5, 2, 4, 10, -5, -2};
        QuickSort sort = new QuickSort();
        sort.insertSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        int[] qa = new int[] {-1, -2, 0, 5, 2, 4, 10, -5, -2, -12, 50, 38, 18, 0, 2, 9, -13, -5, 9, -12};
        sort.quickSort(qa);
        System.out.println(Arrays.toString(qa));
    }
}
