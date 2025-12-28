
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Scan stdin to get the parameters from the user
        int size;
        int winningNumber;

        try (Scanner console = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("Select the board size (3, 5, ...) :");
                    size = console.nextInt();
                    System.out.println("Select the number of strikes to win (3, 5, ...) :");
                    winningNumber = console.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("Please enter a valid number!");
                    console.nextLine(); // clear invalid token
                }
            }
        }

        // constructing the board
        try {
            Board mainBoard = new Board(size, winningNumber);
            Player p1 = new Player("Ahmed", mainBoard, Symbol.X);
            Player p2 = new Player("Hamza", mainBoard, Symbol.O);

            System.out.println("\n=== Tic Tac Toe Game Started ===");
            System.out.println("Board Size: " + size + "x" + size + " | Winning: " + winningNumber);
            System.out.println("Ahmed (X) vs Hamza (O)\n");

            Thread t1 = new Thread(p1);
            Thread t2 = new Thread(p2);
            t1.start();
            t2.start();

            while (!mainBoard.isGameOver()) {
                if (pause(100)) {
                    break;
                }
            }

            t1.join();
            t2.join();

            System.out.println("\n=== Game Over ===");
            System.out.println(mainBoard.displayBoard());
            if (mainBoard.winner() != Symbol.E) {
                System.out.println("\n🎉 Winner: " + mainBoard.winner());
            } else {
                System.out.println("\n🤝 Draw!");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean pause(long millis) {
        try {
            Thread.sleep(millis);
            return false;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return true;
        }
    }
}
