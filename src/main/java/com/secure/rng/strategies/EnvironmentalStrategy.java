package com.secure.rng.strategies;

import com.secure.rng.EntropySource;
import com.secure.rng.RandomStrategy;

// Environmental Noise
public class EnvironmentalStrategy implements RandomStrategy {

    @Override
    public int nextInt(int bound, EntropySource entropy) {
        long seed = entropy.getCompositeSeed();
        return (int) (seed % bound);
    }

    @Override
    public void reseed(EntropySource entropy) {
        // Environmental strategy is stateless
    }
}