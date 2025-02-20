package org.example;

import java.util.Arrays;

public class ParallelPrime extends Thread implements PrimeChecker{
    private int[] array;
    private int start;
    private int end;
    private boolean found = false;
    private static volatile boolean isStopped = false;
    private static int numThreads;
    public ParallelPrime(int start, int end, int[] array, int numThreads) {
        this.start = start;
        this.end = end;
        this.array = array;
        ParallelPrime.numThreads = numThreads;
    }
    public static boolean isPrime(int n) {
        if (n < 2){
            return false;
        }
        for (int i = 2; i * i <= n; i++){
            if (n % i == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        for (int i = start; i < end && !isStopped; i++) {
            if (!isPrime(array[i])) {
                found = true;
                isStopped = true;
                break;
            }
        }
    }
    public boolean isFoundNonPrime() {
        return found;
    }
    public boolean isContain(int[] arr) throws InterruptedException{
        int chunk = arr.length / numThreads;
        ParallelPrime[] Threads = new ParallelPrime[numThreads];
        isStopped = false;
        for (int i = 0; i < numThreads; i++){
            int start = i * chunk;
            int end = (i == numThreads - 1) ? arr.length : start + chunk;
            Threads[i] = new ParallelPrime(start, end, arr, 4);
            Threads[i].start();
        }
        boolean res = false;
        for (ParallelPrime thread : Threads) {
            thread.join();
            if (thread.isFoundNonPrime()) {
                res = true;
                break;
            }
        }
        return res;
    }

}
