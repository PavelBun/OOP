package org.example;

import java.util.Arrays;

public class SequentialPrime extends PrimeChecker{
   private int[] array;
   public SequentialPrime(int[] array){
       this.array = array;
   }
    public boolean isContain(){
        for (int i : array){
            if (!isPrime(i)){
                return true;
            }
        }
        return false;
    }
}
