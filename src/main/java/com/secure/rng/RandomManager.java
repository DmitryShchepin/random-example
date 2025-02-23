package com.secure.rng;

import com.secure.rng.strategies.EnvironmentalStrategy;
import com.secure.rng.strategies.PoolStrategy;
import com.secure.rng.strategies.SecureRandomStrategy;
import com.secure.rng.strategies.XorShiftStrategy;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class RandomManager {

    private final List<RandomStrategy> strategies;
    private final EntropySource entropy;
    private final AtomicLong lastSwitch;
    @Getter
    private volatile RandomStrategy currentStrategy;

    public RandomManager() {
        strategies = List.of(
                new SecureRandomStrategy(),
                new XorShiftStrategy(),
                new PoolStrategy(1000),
                new EnvironmentalStrategy()
        );
        entropy = createEntropySource();
        currentStrategy = strategies.getFirst();
        lastSwitch = new AtomicLong(entropy.getNanoTime());
    }

    protected EntropySource createEntropySource() {
        return new EntropySource();
    }

    public int nextInt(int bound) {
        if (shouldRotateStrategy()) {
            rotateStrategy();
        }
        return currentStrategy.nextInt(bound, entropy);
    }

    private boolean shouldRotateStrategy() {
        final long currentTime = entropy.getNanoTime();
        final long timeSinceLastSwitch = currentTime - lastSwitch.get();
        final long rotationThreshold = 500_000_000L; // 500ms in nanoseconds
        return timeSinceLastSwitch > rotationThreshold;
    }

    private void rotateStrategy() {
        final int newIndex = (int) (entropy.getCompositeSeed() % strategies.size());
        currentStrategy = strategies.get(newIndex);
        currentStrategy.reseed(entropy);
        lastSwitch.set(entropy.getNanoTime());
    }
}
