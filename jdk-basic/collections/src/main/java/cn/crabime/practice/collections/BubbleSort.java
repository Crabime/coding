package cn.crabime.practice.collections;

import java.util.Arrays;

public class BubbleSort {

    public void bubble(int[] arr) {

        int swapPos = 0, swapTempPos = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            swapPos = swapTempPos;

            for (int j = arr.length - 1; j > swapPos; j--) {
                if (arr[j] < arr[j - 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;

                    swapTempPos = j;
                }
            }

            if (swapPos == swapTempPos) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        BubbleSort sort = new BubbleSort();
        int[] qa = new int[] {-1, -2, 0, 5, 2, 4, 10, -5, -2, -12, 50, 38, 18, 0, 2, 9, -13, -5, 9, -12};
        sort.bubble(qa);
        System.out.println(Arrays.toString(qa));
    }
}
