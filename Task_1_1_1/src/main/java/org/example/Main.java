package org.example;
public class Main {
    public static void main(String[] args){
        HeapSort Sort = new heapsortt();
        int[] arr = {1, 3, 4 ,2, 5};
        int[] finalarr = Sort.heapsortt(arr);
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.print(finalarr);
            } else {
                System.out.print(finalarr + ", ");
            }
        }
        System.out.println("]");
    }
}
