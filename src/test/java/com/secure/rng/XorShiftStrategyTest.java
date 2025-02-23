package com.secure.rng;

import com.secure.rng.strategies.XorShiftStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class XorShiftStrategyTest {
    private final XorShiftStrategy strategy = new XorShiftStrategy();
    private final EntropySource entropy = new EntropySource();

    @Test
    @DisplayName("Should reset state on reseed")
    void testReseedEffect() {
        long initialSeed = strategy.getState();
        strategy.reseed(entropy);
        assertNotEquals(initialSeed, strategy.getState());
    }
}

