package org.example;

import java.util.Arrays;

public class SequentialPrime {
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
    public static boolean isContain(int[] arr){
        for (int i : arr){
            if (!isPrime(i)){
                return true;
            }
        }
        return false;
    }
   
}
