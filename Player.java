
import java.util.Random;

public class Player implements Runnable {
    private Board   gameBoard;
    private Symbol  symbol;
    private String  name;
    private final Random random = new Random();

    public Player(String name, Board gBoard, Symbol symbol) {
        this.gameBoard = gBoard;
        this.symbol    = symbol;
        this.name    = name;
    }

    public void playTurn() {
        int x;
        int y;
        int tries = 0;
        while (!gameBoard.isGameOver() && tries < 3) {
            x = random.nextInt(gameBoard.size());
            y = random.nextInt(gameBoard.size());
            boolean success = gameBoard.play(symbol, x, y);
            
            if (success) {
                gameBoard.printMoveAndBoard(name, symbol, x, y);
                return;
            } else {
                System.out.println(name + " (" + symbol + ") -> [" + x + ", " + y + "] ✗");
            }
            tries++;
        }
    }

    @Override
    public void run() {
        while (!this.gameBoard.isGameOver()) {
            try {
                // Think what the player should play simulation
                Thread.sleep(random.nextInt(2000)); // 0~2s
                playTurn();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
