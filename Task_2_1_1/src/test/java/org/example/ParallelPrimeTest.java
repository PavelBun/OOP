package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParallelPrimeTest {

    @Test
    void testIsPrime() {

        assertTrue(ParallelPrime.isPrime(2));
        assertTrue(ParallelPrime.isPrime(17));
        assertTrue(ParallelPrime.isPrime(7919)); // Простое число

        // Проверка составных чисел
        assertFalse(ParallelPrime.isPrime(1));
        assertFalse(ParallelPrime.isPrime(4));
        assertFalse(ParallelPrime.isPrime(100));
    }

    @Test
    void testIsContain_AllPrimes() throws InterruptedException {
        int[] array = new int[1000];
        Arrays.fill(array, 17);

        ParallelPrime parallelPrime = new ParallelPrime(array, 4);
        assertFalse(parallelPrime.isContain());
    }

    @Test
    void testIsContain_ContainsNonPrime() throws InterruptedException {
        int[] array = new int[1000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;

        ParallelPrime parallelPrime = new ParallelPrime(array, 4);
        assertTrue(parallelPrime.isContain());
    }

    @Test
    void testIsContain_Performance() throws InterruptedException {
        int[] array = new int[100_000_000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;
        ParallelPrime parallelPrime = new ParallelPrime(array, 4);
        long startTime = System.nanoTime();
        boolean result = parallelPrime.isContain();
        long endTime = System.nanoTime();


        assertTrue(result);

        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000_000f + " seconds");
    }
}