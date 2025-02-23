package com.secure.rng;

public interface RandomStrategy {

    int nextInt(int bound, EntropySource entropy);

    void reseed(EntropySource entropy);
}
