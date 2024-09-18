package org.example;

public class HeapSort {
    public void toHeap(int[] arr, int i, int len){
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int larg = i;
        int buf;
        if (left < len && arr[left] > arr[larg]){
           larg = left;
        }
        if (right < len && arr[right] > arr[larg]){
           larg = right;
        }
        if (larg != i){
            buf = arr[larg];
            arr[larg] =arr[i];
            arr[i]= buf;
            toHeap(arr, larg, len);
        }
    }
    public int[] heapsort(int[] arr) {
        int[arr.length] bufferArr;
        for (int i = 0; i < arr.length; i++){
            bufferArr[i] = arr[i];
        }

        int len = arr.length;
        int num_of_nodes = (len / 2) - 1;
        for (int i = num_of_nodes; i >= 0; i--){
            toHeap(bufferArr, i, len);
        }
        for(int i = len -1; i >0; i--){
            int buf = bufferArr[i];
            bufferArr[i] = bufferArr[0];
            bufferArr[0] = buf;
            toHeap(arr, 0, i);
        }
        return bufferArr;
    }
}