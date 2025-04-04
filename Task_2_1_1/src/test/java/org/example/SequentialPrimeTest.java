package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class SequentialPrimeTest {

    @Test
    void testIsPrime() {

        assertTrue(SequentialPrime.isPrime(2));
        assertTrue(SequentialPrime.isPrime(17));
        assertTrue(SequentialPrime.isPrime(7919));


        assertFalse(SequentialPrime.isPrime(1));
        assertFalse(SequentialPrime.isPrime(4));
        assertFalse(SequentialPrime.isPrime(100));
    }

    @Test
    void testIsContain_AllPrimes() {

        int[] array = new int[1000];
        Arrays.fill(array, 17);

        SequentialPrime sequentialPrime = new SequentialPrime(array);
        assertFalse(sequentialPrime.isContain());
    }

    @Test
    void testIsContain_ContainsNonPrime() {

        int[] array = new int[1000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;

        SequentialPrime sequentialPrime = new SequentialPrime(array);
        assertTrue(sequentialPrime.isContain());
    }

    @Test
    void testIsContain_Performance() {

        int[] array = new int[100_000_000];
        Arrays.fill(array, 17);
        array[array.length - 1] = 100;

        SequentialPrime sequentialPrime = new SequentialPrime(array);
        long startTime = System.nanoTime();
        boolean result = sequentialPrime.isContain();
        long endTime = System.nanoTime();

        assertTrue(result);

        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000_000f + " seconds");
    }
}