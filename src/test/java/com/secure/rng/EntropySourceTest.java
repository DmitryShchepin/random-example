package com.secure.rng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntropySourceTest {
    private final EntropySource entropy = new EntropySource();

    @Test
    @DisplayName("Should generate non-zero composite seeds")
    void testSeedGeneration() {
        for(int i = 0; i < 100; i++) {
            long seed = entropy.getCompositeSeed();
            assertNotEquals(0L, seed,
                "Generated zero seed at iteration " + i);
        }
    }

    @Test
    @DisplayName("Should provide valid system metrics")
    void testSystemMetrics() {
        assertTrue(entropy.getCpuCores() > 0);
        assertTrue(entropy.getThreadCount() > 0);
        assertTrue(entropy.getNanoTime() > 0);
    }
}
