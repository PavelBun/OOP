package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    public void testHS() {
        HeapSort HS = new HeapSort();
        int[] input1 = {5, 4, 2, 3, 1};
        int[] expected = {1, 2, 3, 4, 5};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void negHS() {
        HeapSort HS = new HeapSort();
        int[] input1 = {-5, -3, -4, -1, -2};
        int[] expected = {-5, -4, -3, -2, -1};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void emptyHS() {
        HeapSort HS = new HeapSort();
        int[] input1 = {};
        int[] expected = {};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testAlreadySorted() {
        HeapSort HS = new HeapSort();
        int[] input1 = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testSame() {
        HeapSort HS = new HeapSort();
        int[] input1 = {1, 1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1, 1};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testOne() {
        HeapSort HS = new HeapSort();
        int[] input1 = {1};
        int[] expected = {1};
        int[] result = HS.heapsort(input1);
        assertArrayEquals(expected, result);
    }

    @Test
    void testTime() {
        HeapSort HS = new HeapSort();

        for (int size = 10000; size <= 100000; size += 10000) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (int) (Math.random() * 1000);
            }

            long startTime = System.nanoTime();
            int[] sortedArr = HS.heapsort(arr);
            long endTime = System.nanoTime();

            long duration = (endTime - startTime) / 100000; // Приведение времени к миллисекундам
            System.out.println(size + " | " + duration);
        }
    }
}
