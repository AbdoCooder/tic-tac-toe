
import java.util.Objects;

public class Board {
    private int                 size            =  0;
    private int                 winningNumber   =  3;
    private int                 lastMoveX       = -1;
    private int                 lastMoveY       = -1;
    private Symbol              currentTurn     = Symbol.X;             // Let assume that the X plays first
    private Symbol              winner          = Symbol.E;
    private Symbol[][]          grid;
    private volatile boolean    gameOver        = false;

    public Board(int size, int winningNumber) throws IllegalArgumentException {
        if (size < 3 || winningNumber < 3) {
            throw new IllegalArgumentException("The borad size and the winning Number must be higher than 3");
        } else if (size < winningNumber) {
            throw new IllegalArgumentException("The borad size must be higher than the winning number");
        } else {
            this.size = size;
            this.winningNumber = winningNumber;
            this.grid = new Symbol[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    this.grid[i][j] = Symbol.E;
                }
            }
        }
    }

    public synchronized boolean play(Symbol symbol, int x, int y) {
        if (gameOver || x < 0 || y < 0 || x >= size || y >= size) {
            return false;
        }
        while (!Objects.equals(symbol, currentTurn) && !gameOver) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to wait");
                break;
            }
        }
        if (gameOver) {
            return false;
        }
        if (grid[y][x] == Symbol.E) {
            grid[y][x] = symbol;
            lastMoveX = x;
            lastMoveY = y;
            currentTurn = (currentTurn == Symbol.X) ? Symbol.O : Symbol.X;
            checkEnd();
            notifyAll();
            return true;
        }
        return false;
    }

    private void checkEnd() {
        if (gameOver) {
            return;
        }

        Symbol lineWinner = findWinningLine();
        if (lineWinner != Symbol.E) {
            winner = lineWinner;
            gameOver = true;
            return;
        }

        if (isBoardFull()) {
            gameOver = true;
        }
    }

    private Symbol findWinningLine() {
        int[][] directions = new int[][] { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 1, -1 } };

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (isEmptyCell(row, col)) {
                    continue;
                }
                Symbol lineWinner = winningFromOrigin(row, col, directions);
                if (lineWinner != Symbol.E) {
                    return lineWinner;
                }
            }
        }
        return Symbol.E;
    }

    private Symbol winningFromOrigin(int row, int col, int[][] directions) {
        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            if (canFitSequence(row, col, dr, dc) && matchesDirection(row, col, dr, dc)) {
                return grid[row][col];
            }
        }
        return Symbol.E;
    }

    private boolean matchesDirection(int row, int col, int dr, int dc) {
        Symbol target = grid[row][col];
        for (int step = 1; step < winningNumber; step++) {
            int r = row + step * dr;
            int c = col + step * dc;
            if (grid[r][c] != target) {
                return false;
            }
        }
        return true;
    }

    private boolean canFitSequence(int row, int col, int dr, int dc) {
        int endRow = row + (winningNumber - 1) * dr;
        int endCol = col + (winningNumber - 1) * dc;
        return endRow >= 0 && endRow < size && endCol >= 0 && endCol < size;
    }

    private boolean isEmptyCell(int row, int col) {
        return grid[row][col] == Symbol.E;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == Symbol.E) {
                    return false;
                }
            }
        }
        return true;
    }

    public String displayBoard() {
        final String RED   = "\u001B[31m";
        final String WHITE = "\u001B[0m";
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == lastMoveY && j == lastMoveX) {
                    sb.append(RED).append(grid[i][j]).append(WHITE);
                } else {
                    sb.append(grid[i][j]);
                }
                if (j < size - 1) {
                    sb.append(" ");
                }
            }
            if (i < size - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public synchronized void printMoveAndBoard(String playerName, Symbol symbol, int x, int y) {
        System.out.println(playerName + " (" + symbol + ") -> [" + y + ", " + x + "] ✓");
        System.out.println(displayBoard());
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j]);
                if (j < size - 1) {
                    sb.append(" ");
                }
            }
            if (i < size - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public int size() {
        return this.size;
    }
    
    public boolean isGameOver() {
        return this.gameOver;
    }

    public Symbol winner() {
        return this.winner;
    }

}
