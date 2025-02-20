package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParallelStreamTest {

    @Test
    void testIsPrime() {
        // Проверка простых чисел
        assertTrue(ParallelStream.isPrime(2));
        assertTrue(ParallelStream.isPrime(17));
        assertTrue(ParallelStream.isPrime(7919)); // Простое число

        // Проверка составных чисел
        assertFalse(ParallelStream.isPrime(1));
        assertFalse(ParallelStream.isPrime(4));
        assertFalse(ParallelStream.isPrime(100));
    }

    @Test
    void testIsContain_AllPrimes() {

        int[] array = new int[1000];
        Arrays.fill(array, 17);

        ParallelStream parallelStream = new ParallelStream();
        assertFalse(parallelStream.isContain(array));
    }

    @Test
    void testIsContain_ContainsNonPrime() {

        int[] array = new int[1000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;

        ParallelStream parallelStream = new ParallelStream();
        assertTrue(parallelStream.isContain(array));
    }

    @Test
    void testIsContain_Performance() {

        int[] array = new int[100_000_000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;
        ParallelStream parallelStream = new ParallelStream();
        long startTime = System.nanoTime();
        boolean result = parallelStream.isContain(array);
        long endTime = System.nanoTime();

        assertTrue(result);


        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000_000f + " seconds");
    }
}