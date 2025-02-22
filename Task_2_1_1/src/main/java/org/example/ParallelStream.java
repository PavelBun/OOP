package org.example;
import java.util.Arrays;

public class ParallelStream extends PrimeChecker{
    private final int[] array;
    public ParallelStream(int[] array) {
        this.array = array;
    }

    public boolean isContain() {
        return Arrays.stream(array).parallel().anyMatch(num -> !isPrime(num));
    }
}
