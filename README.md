# Tic Tac Toe - Multithreaded Game Implementation

A professional-grade Java implementation of Tic Tac Toe featuring two automated AI players competing on a shared grid with concurrent execution and thread synchronization.

## Overview

![Build & Run](https://github.com/AbdoCooder/tic-tac-toe/blob/main/tic-tac-toe-showcase.gif)

This project demonstrates advanced multithreading concepts in Java through a fully playable Tic Tac Toe game. Two AI players autonomously compete while respecting thread safety and shared resource management.

## Key Features

- **Multithreaded Architecture**: Each player runs on its own thread with proper synchronization
- **Thread-Safe Shared State**: Implements locks and synchronized blocks to prevent race conditions
- **Customizable Game Board**: Configurable grid size and game parameters
- **AI Player Implementation**: Autonomous game logic and decision-making
- **Real-time Game Monitoring**: Concurrent game state updates and winner detection

## Technical Stack

- **Language**: Java 17+
- **Concurrency**: Java Threading API, Synchronization, Locks
- **Architecture**: Object-Oriented Design with clean separation of concerns

## What I Learned

### 1. **Multithreading Fundamentals**
   - Creating and managing multiple threads independently
   - Thread lifecycle management (creation, execution, termination)
   - Understanding thread scheduling and execution models

### 2. **Thread Synchronization & Safety**
   - Preventing race conditions with synchronized blocks
   - Using volatile variables for visibility across threads
   - Implementing proper locking mechanisms
   - Understanding happens-before relationships in Java

### 3. **Shared Resource Management**
   - Coordinating access to shared game board state
   - Implementing turn-based execution with mutual exclusion
   - Avoiding deadlock scenarios in concurrent systems

### 4. **Concurrent Programming Patterns**
   - Producer-consumer pattern implementation
   - Wait-notify mechanisms for inter-thread communication
   - Designing thread-safe collections and data structures

### 5. **Software Design Principles**
   - Separation of concerns (game logic vs. threading)
   - Encapsulation of thread management
   - Maintainable and testable concurrent code

### 6. **Debugging Concurrent Systems**
   - Identifying and fixing race conditions
   - Using proper logging for concurrent execution tracking
   - Understanding thread dumps and stack traces

## Project Structure

```
tic-tac-toe/
├── Board.java                  # Shared game state with synchronization
├── Player.java                 # AI player implementation (Runnable)
├── Symbol.java                 # Enum for game symbols (X, O, E)
├── Main.java                   # Entry point
├── README.md
└── .gitignore
```

## How to Run

```bash
# Compile
javac *.java

# Run
java Main
```

## Example Output

When running the game, you'll see output like:
```
=== Tic Tac Toe Game Started ===
Board Size: 3x3 | Winning: 3
Ahmed (X) vs Hamza (O)

Ahmed (X) -> [1, 1] ✓
X E E
E X E
E E E

Hamza (O) -> [0, 0] ✓
O E E
E X E
E E E

...

=== Game Over ===
X O O
X X O
X O E

🎉 Winner: X
```

## Key Code Insights

### Thread-Safe Board Access
- All board operations are synchronized via the `play()` method to prevent concurrent modification
- Players wait for their turn using wait-notify mechanisms in the `play()` method
- Game state remains consistent across all threads through synchronized access
- The `currentTurn` field tracks whose turn it is and coordinates alternation

### AI Strategy
- Players use randomized move selection (random coordinates on the board)
- Players attempt moves up to 3 times before giving up on a turn
- Decision-making happens independently on each thread with configurable thinking time (0-2 seconds)
- Turn management is enforced by the synchronized `play()` method

### Win Detection
- The `findWinningLine()` method checks four directions: horizontal, vertical, and two diagonals
- Win detection is atomic with the move placement through synchronization
- Supports configurable winning sequences (not just 3-in-a-row)

## Challenges & Solutions

| Challenge | Solution |
|-----------|----------|
| Race conditions on board updates | Synchronized `play()` method on Board class |
| Turn synchronization | Wait-notify pattern with `currentTurn` field |
| Missed win conditions | Atomic `checkEnd()` called within synchronized `play()` |
| Thread termination | Main thread joins both player threads after game ends |
| Board state visibility | Volatile `gameOver` flag for visibility across threads |

## Future Enhancements

- Minimax algorithm implementation for optimal AI strategy
- Difficulty levels (easy, medium, hard)
- Game statistics and win/loss tracking
- Network multiplayer support
- GUI implementation with JavaFX
- Better AI that analyzes board state instead of random moves

## Conclusion

This project reinforced essential multithreading concepts and demonstrated how concurrent programming challenges are solved in real-world applications. It serves as a foundation for understanding larger distributed systems and concurrent frameworks.

---

**Author**: AbdoCooder
**Created**: 2025