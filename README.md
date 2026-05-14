# Quoridor

A fully playable implementation of the classic **Quoridor** board game in Java, featuring both a graphical user interface (GUI) and a terminal-based version. Supports 2 or 4 players, human or computer-controlled, with wall placement and pathfinding validation.

---

## Table of Contents

- [About the Game](#about-the-game)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Running the GUI](#running-the-gui)
  - [Running the Terminal Version](#running-the-terminal-version)
- [How to Play](#how-to-play)
  - [Objective](#objective)
  - [On Your Turn](#on-your-turn)
  - [Wall Rules](#wall-rules)
  - [Winning](#winning)
- [Terminal Input Format](#terminal-input-format)
- [Architecture Overview](#architecture-overview)
- [Package Reference](#package-reference)

---

## About the Game

Quoridor is a 2–4 player abstract strategy game. Each player controls a pawn that starts on one side of a 9×9 board and must reach the opposite side. On each turn, a player may either move their pawn one step or place a wall segment to block opponents — but walls can never completely cut off any player's path to their goal.

---

## Features

- 9×9 board faithful to the original Quoridor rules
- 2-player and 4-player modes
- Human and Computer player types (configurable per seat)
- Custom player names
- Wall placement with full path-blocking validation (DFS-based)
- Win detection by goal-row arrival or last-player-standing
- GUI with color-coded players, wall rendering, and win/error popups
- Terminal mode for text-based play

---

## Project Structure

```
Quoridor/
│
├── Quoridor.java                  # Entry point for terminal mode
│
├── core/
│   ├── Board.java                 # Static board state, wall logic, pathfinding
│   ├── Direction.java             # UP/DOWN/LEFT/RIGHT enum with deltas
│   ├── Piece.java                 # Pawn position and movement
│   ├── Wall.java                  # Wall data and random wall generation
│   │
│   ├── action/
│   │   ├── Action.java            # Marker interface for all actions
│   │   ├── StepAction.java        # Pawn move action
│   │   └── WallAction.java        # Wall placement action
│   │
│   ├── exception/
│   │   ├── ActionFormatException.java   # Malformed input
│   │   ├── IllegalActionException.java  # Rule violation
│   │   └── PlayerCountException.java    # Wrong number of players
│   │
│   └── player/
│       ├── Player.java            # Abstract base: piece, walls, win condition
│       ├── HumanPlayer.java       # Human-controlled player
│       └── ComputerPlayer.java    # Computer player with random action logic
│
├── gui/
│   ├── QuoridorGUI.java           # Main GUI frame and game loop
│   └── Window.java                # Error and win message popups
│
└── resources/
    ├── startIcon.png
    ├── redPlayer.png
    ├── bluePlayer.png
    ├── greenPlayer.png
    └── yellowPlayer.png
```

---

## Getting Started

### Prerequisites

- Java 17 or later
- Any standard Java IDE (IntelliJ IDEA, Eclipse, etc.) or the `javac`/`java` CLI tools

### Running the GUI

Compile all sources and run `QuoridorGUI`:

```bash
javac -sourcepath src -d out src/gui/QuoridorGUI.java
java -cp out gui.QuoridorGUI
```

Or simply run `QuoridorGUI.main()` from your IDE.

### Running the Terminal Version

```bash
javac -sourcepath src -d out src/Quoridor.java
java -cp out Quoridor <NumberOfPlayers>
```

**Example:**
```bash
java -cp out Quoridor 2
```

The number of players must be `2` or `4`.

---

## How to Play

### GUI Walkthrough

1. **Start Screen** — Click the start button to proceed to setup.
2. **Setup Screen** — Choose 2-player or 4-player mode, set each player's name, and toggle each seat between Human and Computer. Computer players cannot be renamed.
3. **Game Screen** — The board is displayed on the right; player info and wall counts appear on the left.
   - Click an **adjacent cell** to move your pawn.
   - Click a **gap between cells** (the thin strips) to place a wall there.
   - The active player's pawn is highlighted with a black border.
4. **Reset** — Click the Reset button at any time to return to the setup screen.

### Objective

Move your pawn from your starting side to the **opposite side** of the board before your opponents do.

| Player | Starts at | Goal |
|--------|-----------|------|
| Player 1 (Red) | Bottom row | Top row |
| Player 2 (Blue) | Top row | Bottom row |
| Player 3 (Green) | Right column | Left column |
| Player 4 (Yellow) | Left column | Right column |

### On Your Turn

You must do exactly one of the following:

- **Move** your pawn one step in any cardinal direction (up, down, left, right), provided no wall blocks the path and the destination is on the board.
- **Place a wall** segment in any valid empty slot, as long as it does not completely cut off any player from their goal.

### Wall Rules

- Each player starts with **10 walls**.
- Walls occupy the gap between two pairs of adjacent cells.
- A wall cannot be placed if it would leave any player with no path to their goal (validated via depth-first search).
- Walls already placed cannot be moved or removed.

### Winning

A player wins when:
- Their pawn reaches any cell on their **goal row/column**, or
- All other players have been eliminated (4-player mode only, where pawns can land on and eliminate opponents).

---

## Terminal Input Format

Each turn, type an action and press Enter.

**Move your pawn:**
```
step <direction>
```
Direction must be one of: `up`, `down`, `left`, `right`.

```
step up
```

**Place a wall:**
```
wall <row> <col> <orientation>
```
- `row` and `col` are 0-indexed coordinates of the wall's top-left anchor cell.
- Orientation: `v` for vertical, `h` for horizontal.

```
wall 3 4 h
```

Invalid or rule-violating inputs display an error message and prompt the same player to try again.

---

## Architecture Overview

```
QuoridorGUI / Quoridor
        │
        ▼
    Player (abstract)
    ├── HumanPlayer
    └── ComputerPlayer
        │
        ▼
      Piece  ──moves on──►  Board (static state)
                                 │
                            Wall validation
                            (DFS pathfinding)
```

- **`Board`** is a static class holding the shared wall state for the current game. Call `Board.resetBoard()` between games.
- **`Player`** delegates movement to `Piece` and wall placement to `Board`, consuming wall inventory on each `WallAction`.
- **`ComputerPlayer`** selects randomly between a random step and a random wall placement each turn.

---

## Package Reference

| Package | Responsibility |
|---|---|
| `core` | Board, pieces, walls, directions |
| `core.action` | Action types passed to `Player.perform()` |
| `core.exception` | Typed exceptions for format and rule errors |
| `core.player` | Player abstraction, Human and Computer subtypes |
| `gui` | Swing GUI, rendering, and user interaction |
