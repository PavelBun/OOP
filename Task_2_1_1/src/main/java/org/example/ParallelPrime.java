package org.example;

import java.util.Arrays;

public class ParallelPrime extends Thread{
    private int[] array;
    private int start;
    private int end;
    private boolean found = false;
    public ParallelPrime(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
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
            for (int i = start; i < end; i++) {
                if (!isPrime(array[i])) {
                    found = true;
                    break;
                }
            }
        }
    public boolean isFoundNonPrime() {
        return found;
    }
    public static boolean isContain(int[] arr, int numThreads) throws InterruptedException{
        int chunk = arr.length / numThreads;
        ParallelPrime[] Threads = new ParallelPrime[numThreads];
        for (int i = 0; i < numThreads; i++){
            int start = i * chunk;
            int end = (i == numThreads - 1) ? arr.length : start + chunk;
            Threads[i] = new ParallelPrime(start, end, arr);
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
