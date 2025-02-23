package com.secure.rng;

import com.secure.rng.strategies.EnvironmentalStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnvironmentalStrategyTest {
    @Test
    @DisplayName("Should utilize multiple entropy sources")
    void testEntropyDiversity() {
        EnvironmentalStrategy strategy = new EnvironmentalStrategy();
        EntropySource entropy = new EntropySource();
        
        Set<Integer> seeds = new HashSet<>();
        for(int i = 0; i < 100; i++) {
            seeds.add(strategy.nextInt(100_000, entropy));
        }
        
        assertTrue(seeds.size() > 90, 
            "Insufficient entropy diversity: " + seeds.size());
    }
}