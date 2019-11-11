package cn.crabime.practice.collections;

import java.util.Arrays;

public class MergeSort {

    public void mergeSort(int[] arr) {
        int[] tmp = new int[arr.length];
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int[] tmpArr, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(arr, tmpArr, left, center);
            mergeSort(arr, tmpArr, center + 1, right);

            merge(arr, tmpArr, left, center + 1, right);
        }
    }

    private void merge(int[] arr, int[] tmpArr, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int arrLen = rightEnd - leftPos + 1;

        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (arr[leftPos] < arr[rightPos]) {
                tmpArr[tmpPos++] = arr[leftPos++];
            } else {
                tmpArr[tmpPos++] = arr[rightPos++];
            }
        }

        while (leftPos <= leftEnd) {
            tmpArr[tmpPos++] = arr[leftPos++];
        }

        while (rightPos <= rightEnd) {
            tmpArr[tmpPos++] = arr[rightPos++];
        }

        for (int i = 0; i < arrLen; i++, rightEnd--) {
            arr[rightEnd] = tmpArr[rightEnd];
        }
    }

    public static void main(String[] args) {
        MergeSort sort = new MergeSort();
        int[] qa = new int[] {-1, -2, 0, 5, 2, 4, 10, -5, -2, -12, 50, 38, 18, 0, 2, 9, -13, -5, 9, -12};
        sort.mergeSort(qa);
        System.out.println(Arrays.toString(qa));
    }
}
