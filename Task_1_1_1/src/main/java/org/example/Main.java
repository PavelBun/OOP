package org.example;
public class Main {
    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        int[] arr = {1, 3, 4, 2, 5};
        int[] arr1 = HeapSort.heapsort(arr);

        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.print(arr1[i]);
            } else {
                System.out.print(arr1[i] + ", ");
            }
        }
        System.out.println("]");
    }
}