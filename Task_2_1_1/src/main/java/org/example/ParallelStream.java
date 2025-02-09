package org.example;
import java.util.Arrays;

public class ParallelStream {
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isContain(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(num -> !isPrime(num));
    }

    
}
