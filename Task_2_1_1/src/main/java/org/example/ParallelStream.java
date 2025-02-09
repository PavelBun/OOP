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

    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[100_000_000];
        Arrays.fill(array, 17); // Заполняем простыми числами
        array[array.length - 1] = 100; // Добавляем составное число
        long startTime = System.nanoTime();
        boolean result = isContain(array);
        long endTime = System.nanoTime();
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) /1000000000f + " ns");
    }
}
