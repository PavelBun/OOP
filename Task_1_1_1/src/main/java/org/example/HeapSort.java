package org.example;

public class HeapSort {
    static public void toHeap(int[] arr, int i, int len) {
        //Дочерние элементы
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int larg = i;
        int buf;

        //Находим наибольшее
        if (left < len && arr[left] > arr[larg]) {
            larg = left;
        }
        if (right < len && arr[right] > arr[larg]) {
            larg = right;
        }
        if (larg != i) {
            buf = arr[larg];
            arr[larg] = arr[i];
            arr[i] = buf;
            toHeap(arr, larg, len);
        }
    }

    static public int[] heapsort(int[] arr) {
        int[] bufferArr = new int[arr.length];
        System.arraycopy(arr, 0, bufferArr, 0, arr.length);

        int len = bufferArr.length;

        // Построение кучи
        for (int i = (len / 2) - 1; i >= 0; i--) {
            toHeap(bufferArr, i, len);
        }

        // Сортировка
        for (int i = len - 1; i > 0; i--) {
            int buf = bufferArr[i];
            bufferArr[i] = bufferArr[0];
            bufferArr[0] = buf;

            toHeap(bufferArr, 0, i);
        }
        return bufferArr;
    }
}
