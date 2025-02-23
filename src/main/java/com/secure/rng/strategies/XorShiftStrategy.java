package com.secure.rng.strategies;

import com.secure.rng.EntropySource;
import com.secure.rng.RandomStrategy;
import lombok.Data;

@Data
public class XorShiftStrategy implements RandomStrategy {

    private long state;

    @Override
    public int nextInt(int bound, EntropySource entropy) {
        state ^= (state << 21);
        state ^= (state >>> 35);
        state ^= (state << 4);
        return (int) (Math.abs(state) % bound);
    }

    @Override
    public void reseed(EntropySource entropy) {
        state = entropy.getSeed() ^ (entropy.getNanoTime() << 32);
    }
}