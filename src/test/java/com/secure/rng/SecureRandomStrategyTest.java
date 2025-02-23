package com.secure.rng;

import com.secure.rng.strategies.SecureRandomStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SecureRandomStrategyTest {
    private final SecureRandomStrategy strategy = new SecureRandomStrategy();
    private final EntropySource entropy = new EntropySource();

    @Test
    @DisplayName("Should produce different values after reseed")
    void testReseeding() {
        int first = strategy.nextInt(100, entropy);
        strategy.reseed(entropy);
        int second = strategy.nextInt(100, entropy);

        assertNotEquals(first, second);
    }
}