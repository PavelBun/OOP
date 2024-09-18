package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HeapSortTest {
    @Test
    public void testHS(){
        HeapSort HS = new HeapSort();
        int[] input = {5, 4, 2, 3, 1};
        int [] needed = {1, 2, 3, 4, 5};
        HS.heapsort(input);
        assertArrayEquals(needed, input);
    }
    @Test
    public void negHS(){
        HeapSort HS = new HeapSort();
        int[] input = {-5, -3, -4, -1, -2};
        int [] needed = {-5, -4, -3, -2, -1};
        HS.heapsort(input);
        assertArrayEquals(needed, input);
    }
    @Test
    public void emptyHS(){
        HeapSort HS = new HeapSort();
        int[] input = {};
        int[] needed = {};
        HS.heapsort(input);
        assertArrayEquals(needed, input);
    }
    @Test
    public void testAlreadySorted() {
        HeapSort HS = new HeapSort();
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        HS.heapsort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    public void testSame() {
        HeapSort HS = new HeapSort();
        int[] input = {1, 1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1, 1};
        HS.heapsort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    public void testOne() {
        HeapSort HS = new HeapSort();
        int[] input = {1};
        int[] expected = {1};
        HS.heapsort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    void testTime() {
        HeapSort HS = new HeapSort();

        for (int size = 10000; size <= 100000; size += 10000) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (int) (Math.random() * 1000);
            }

            long time = System.nanoTime();
            HS.heapsort(arr);
            long endTime = System.nanoTime();

            long duration = (endTime - time) / 100000;
            System.out.println(size + " | " + duration);
        }
    }
}