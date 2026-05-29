# Java Tetris Game

A fully functional Tetris game built in Java using Swing for the GUI. Developed as a college course project (CSCI), featuring all 7 classic Tetris pieces, score tracking, a persistent leaderboard, and save/load game functionality.

---

## Features

- All 7 classic Tetris bricks — L, J, S, Z, T (Stack), Long (I), and Square
- Rotation and movement for every piece
- Line clearing with combo scoring (1–4 lines)
- Persistent leaderboard (Hall of Fame) saved to file
- Save and load game state to/from `.gam` files
- Adjustable board dimensions
- Pause/resume support
- Game Over screen with leaderboard prompt

---

## Controls

| Key | Action |
|---|---|
| Arrow Left | Move piece left |
| Arrow Right | Move piece right |
| Arrow Up | Rotate piece |
| Arrow Down | Hard drop (instant fall) |
| Space | Pause / Resume |
| N | New game |

---

## Scoring

| Lines Cleared | Points |
|---|---|
| 1 line | 100 |
| 2 lines | 300 |
| 3 lines | 600 |
| 4 lines | 1200 |

---

## Project Structure

```
├── TetrisWindow.java        # Main entry point — game window and menu bar
├── TetrisGame.java          # Core game logic, board state, scoring, save/load
├── TetrisDisplay.java       # Rendering, animation, keyboard input
├── TetrisBrick.java         # Abstract base class for all brick types
├── ElBrick.java             # L-shaped piece
├── JayBrick.java            # J-shaped piece
├── EssBrick.java            # S-shaped piece
├── ZeeBrick.java            # Z-shaped piece
├── StackBrick.java          # T-shaped piece
├── LongBrick.java           # I-shaped (long) piece
├── SquareBrick.java         # Square (O) piece
├── LeaderBoard.java         # Hall of Fame display and score persistence
├── Leaderboard_scores.txt   # Persistent leaderboard data
└── Saved_Games/             # Folder for saved .gam files
    ├── OneLineDel.gam
    ├── TwoLineDel.gam
    ├── ThreeLineDel.gam
    └── FourLineDel.gam
```

---

## Requirements

- Java 8 or higher (Swing is included in the standard JDK)

---

## Running the Game

**Compile all files:**

```bash
javac *.java
```

**Run the game:**

```bash
java TetrisWindow
```

---

## Menu Options

The menu bar has two menus:

**Game**
- New Game — start a fresh game
- Save Game — save the current board state to a `.gam` file
- Retrieve Saved Game — load a previously saved game
- Change Dimensions — set a custom number of rows and columns
- Quit — exit the application

**Leaderboard**
- Show Leaderboard — display the Hall of Fame
- Clear Leaderboard — reset all scores to 0

---

## Leaderboard

When a game ends, if your score qualifies for the top 10, you'll be prompted to enter your name. Scores are saved to `Leaderboard_scores.txt` and persist between sessions.

---

## Course

Developed for an introductory Java programming course in college.
