package com.secure.rng.strategies;

import com.secure.rng.EntropySource;
import com.secure.rng.RandomStrategy;

import java.security.SecureRandom;

public class SecureRandomStrategy implements RandomStrategy {

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public int nextInt(int bound, EntropySource entropy) {
        return secureRandom.nextInt(bound);
    }

    @Override
    public void reseed(EntropySource entropy) {
        secureRandom.setSeed(entropy.getSeed());
    }
}