package org.example;

import java.util.Arrays;

public class ParallelPrime extends PrimeChecker implements Runnable {
    private final int[] array;
    private int start;
    private int end;
    private volatile boolean found = false;
    private final int numThreads;
    public ParallelPrime(int[] array, int numThreads) {
        this.array = array;
        this.numThreads = numThreads;
    }

    @Override
    public void run() {
        for (int i = start; i < end && !found; i++) {
            if (!isPrime(array[i])) {
                found = true;
                break;
            }
        }
    }

    public boolean isFoundNonPrime() {
        return found;
    }

    public boolean isContain() throws InterruptedException {
        int chunk = array.length / numThreads;
        Thread[] threads = new Thread[numThreads];
        ParallelPrime[] workers = new ParallelPrime[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunk;
            int end = (i == numThreads - 1) ? array.length : (i + 1) * chunk;

            workers[i] = new ParallelPrime(array, numThreads);
            workers[i].start = start;
            workers[i].end = end;
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        boolean res = false;
        for (Thread thread : threads) {
            thread.join();
        }

        for (ParallelPrime worker : workers) {
            if (worker.isFoundNonPrime()) {
                res = true;
                break;
            }
        }

        return res;
    }

}
