package com.secure.rng;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RandomManagerTest {
    private RandomManager randomManager;
    private EntropySource mockEntropy;

    @BeforeEach
    void setup() {
        mockEntropy = mock(EntropySource.class);
        randomManager = new RandomManager() {
            @Override
            protected EntropySource createEntropySource() {
                return mockEntropy;
            }
        };
    }

    @Test
    @DisplayName("Should rotate strategies when threshold reached")
    void testStrategyRotation() {
        // Setup mock sequence
        when(mockEntropy.getNanoTime())
                .thenReturn(0L)        // Initial timestamp
                .thenReturn(600_000_000L); // Second call (600ms > 500ms threshold)

        when(mockEntropy.getCompositeSeed()).thenReturn(1L);

        // First call - should not rotate
        randomManager.nextInt(100);
        RandomStrategy firstStrategy = randomManager.getCurrentStrategy();

        // Second call - should rotate
        randomManager.nextInt(100);
        RandomStrategy secondStrategy = randomManager.getCurrentStrategy();

        // Verify rotation
        assertNotSame(firstStrategy, secondStrategy);

        // Verify mock interactions
        verify(mockEntropy, times(5)).getNanoTime();
        verify(mockEntropy, atLeastOnce()).getCompositeSeed();
    }

    @Test
    @DisplayName("Should generate numbers within specified bounds")
    void testOutputRange() {
        for(int i = 0; i < 1000; i++) {
            int result = randomManager.nextInt(10);
            assertTrue(result >= 0 && result < 10, 
                "Generated value out of bounds: " + result);
        }
    }

}





