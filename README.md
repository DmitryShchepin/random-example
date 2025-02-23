# Unpredictable Random Number Generator (RNG)
**A Strategy Pattern-based Secure RNG with Dynamic Behavior**

---

## 📖 Overview
This project implements an unpredictable random number generator using the **Strategy Pattern** combined with dynamic algorithm rotation and environmental entropy. Designed for security-critical applications where predictable outcomes are unacceptable, it combines multiple randomization techniques to create a virtually unbreakable RNG system.

---

## ✨ Features

### 🔄 Strategy Pattern Implementation
- **Four Concrete Strategies**  

  | Strategy                | Description                                       |  
  |-------------------------|---------------------------------------------------|  
  | `SecureRandomStrategy`  | Cryptographic-grade security via `SecureRandom`   |  
  | `XorShiftStrategy`      | Ultra-fast XOR shift with environmental mixing    |  
  | `PoolStrategy`          | Pre-shuffled number pool with chaotic reshuffling |  
  | `EnvironmentalStrategy` | Direct hardware/system metrics utilization        |
_Another strategies can be added_

- **Common Interface**
  ```java
  public interface RandomStrategy {
    int nextInt(int bound, EntropySource entropy);
    void reseed(EntropySource entropy);
  }
- **Separation of Concerns**

  - Strategy management vs. randomization logic
  - Easy hot-swapping of algorithms

## ⚡ Dynamic Strategy Rotation
- Auto-Rotation every 500-800ms (random interval)
- Chaotic Selection Algorithm:
```java
    int index = (entropySeed ^ threadCount) % strategies.size();
```
- Automatic Reseeding during strategy switches

## 🌐 Entropy Management
- EntropySource Facade (RandomManager) aggregates:
    - System nanoTime
    - Free memory stats 
    - Thread activity 
    - CPU core count
    - SecureRandom values

- Composite Seed Generation:
```java
long seed = nanoTime ^ freeMemory ^ threadCount ^ secureRandomLong;
```

## 🛡️ Anti-Prediction Architecture

```mermaid
graph TD
    A[User Calls nextInt()] --> B{Check Rotation Needed?}
    B -->|Yes| C[Select Strategy via Environmental Chaos]
    C --> D[Reseed with Composite Entropy]
    D --> E[Execute Strategy Logic]
    B -->|No| E
    E --> F[Return Unpredictable Result]
```
## 🚀 Usage
Installation
Add as Maven dependency:

```java
RandomManager randomManager = new RandomManager();

// Generate secure numbers
int secureLottery = randomManager.nextInt(100);  // 0-99
boolean coinFlip = randomManager.nextInt(2) == 0;

// Real-time strategy monitoring
System.out.println("Active Strategy: " + randomManager.getCurrentStrategy().getClass().getSimpleName());
```


## 🔧 How It Works

### Component Architecture

| Component                | Responsibility                                  |
|--------------------------|------------------------------------------------|
| `RandomManager`          | Strategy coordination & rotation management    |
| `EntropySource`          | Central entropy aggregation from system metrics|
| Strategy Implementations | Specialized randomization algorithms           |

### Workflow Cycle

1. **Entropy Harvesting**  
   Collects 12+ system metrics including:
    - Nano-second clock precision
    - Memory utilization statistics
    - Thread activity levels
    - CPU core availability

2. **Strategy Selection**  
   Environmental chaos-driven choice using:
   ```java
   index = (entropySeed ^ threadCount << 16) % strategies.size()

3. **Execution:** Delegates to selected strategy
4. **Rotation Check:** Time + entropy-based threshold
```java
if (currentNano - lastSwitch > 500_000_000L + (entropy % 300_000_000L))
```
## 🛡️ Security Features
### Attack Mitigations

| Threat                | Defense Mechanism                |
|-----------------------|----------------------------------|
| Algorithm Prediction  | Dynamic strategy rotation        |
| State Reconstruction  | Frequent reseeding (every 250ms) |
| Pattern Analysis      | Hybrid output mixing             |
| Side-Channel Attacks  | Environmental entropy shielding  |

## ✅ Advantages
| Category          | Benefits                                   |
|-------------------|--------------------------------------------|
| Unpredictability  | 4+ simultaneous randomization layers       |
| Performance       | Optimized strategy delegation              |
| Maintainability   | Add new strategies in <10 LoC              |
| Adaptability      | Real-time response to system changes       |

## 🧪 Testing
```maven
# Run tests and build JAR
mvn clean package

# Run specific test class
mvn test -Dtest=RandomManagerTest

# Create standalone executable
mvn package shade:shade
```

