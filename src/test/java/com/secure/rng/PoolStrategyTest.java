package com.secure.rng;

import com.secure.rng.strategies.PoolStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoolStrategyTest {
    @Test
    @DisplayName("Should reshuffle when pointer exceeds pool")
    void testPoolReset() {
        PoolStrategy strategy = new PoolStrategy(5);
        EntropySource entropy = new EntropySource();

        for (int i = 0; i < 5; i++) {
            strategy.nextInt(10, entropy);
        }

        int postReset = strategy.nextInt(10, entropy);
        assertTrue(postReset >= 0 && postReset < 10);
    }
}