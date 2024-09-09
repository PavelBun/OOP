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
    public void heapsort(int[] arr) {
        int len = arr.length;
        int num_of_nodes = (len / 2) - 1;
        for (int i = num_of_nodes; i >= 0; i--){
            toHeap(arr, i, len);
        }
        for(int i = len -1; i >0; i--){
            int buf = arr[i];
            arr[i] = arr[0];
            arr[0] = buf;
            toHeap(arr, 0, i);
        }
    }
    public static void main(String[] args){
        HeapSort Sort = new HeapSort();
        int[] arr = {1, 3, 4 ,2, 5};
        Sort.heapsort(arr);
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.print(arr[i]);
            } else {
                System.out.print(arr[i] + ", ");
            }
        }
        System.out.println("]");
    }

}