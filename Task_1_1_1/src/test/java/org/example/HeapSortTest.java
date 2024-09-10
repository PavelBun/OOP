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
}