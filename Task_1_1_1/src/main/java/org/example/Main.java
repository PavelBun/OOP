package org.example;
public class Main {
    public static void main(String[] args){
        HeapSort Sort = new HeapSort();
        int[] arr = {1, 3, 4 ,2, 5};
        int[] finalarr = Sort.heapsort(arr);
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
