
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player implements Runnable {
    private Board   gameBoard;
    private Symbol  symbol;
    private String  name;
    Scanner console;

    public Player(String name, Board gBoard, Symbol symbol, Scanner s) {
        this.gameBoard = gBoard;
        this.symbol    = symbol;
        this.name      = name;
        this.console = s;
    }

    public void playTurn() {
        while (!gameBoard.isGameOver()) {
            int[] coordinates = getUserInput(gameBoard.size());
            if (gameBoard.play(symbol, coordinates[0], coordinates[1])) {
                break;
            }
            synchronized (console) {
                System.out.println(name + " (" + symbol + ") -> Position occupied or invalid, retrying...");
            }
        }
    }

    private int[] getUserInput(int boardSize) {
        int x;
        int y;
        synchronized (console) {
            try {
                while (gameBoard.getTurn() != symbol) {
                    console.wait(100);
                }
                System.out.println("==================Turn for "+symbol+"==================");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println();
            }
            while (true) {
                try {
                    System.out.println("Enter x:");
                    y = console.nextInt();
                    System.out.println("Enter y:");
                    x = console.nextInt();
                    if (isValidCoordinate(x, y, boardSize)) {
                        break;
                    }
                    System.err.println("Coordinates must be between 0 and " + (boardSize - 1));
                } catch (InputMismatchException e) {
                    System.err.println("Please enter a valid number!");
                    console.nextLine(); // clear invalid token
                }
            }
        }
        return new int[]{x, y};
    }

    private boolean isValidCoordinate(int x, int y, int boardSize) {
        return x >= 0 && y >= 0 && x < boardSize && y < boardSize;
    }

    @Override
    public void run() {
        while (!this.gameBoard.isGameOver()) {
            playTurn();
        }
    }
}
