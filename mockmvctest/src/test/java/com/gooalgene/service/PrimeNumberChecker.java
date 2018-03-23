package com.gooalgene.service;

/**
 * Created by crabime on 3/4/18.
 */
public class PrimeNumberChecker {

    public boolean validate(final Integer primeParameter){
        for (int i = 2; i < (primeParameter / 2); i++) {
            if (primeParameter % i == 0){
                return false;
            }
        }
        return true;
    }
}
