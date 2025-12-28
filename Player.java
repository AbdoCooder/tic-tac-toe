
import java.util.Random;

public class Player implements Runnable {
    private Board   gameBoard;
    private Symbol  symbol;
    private String  name;
    private final Random random = new Random();

    public Player(String name, Board gBoard, Symbol symbol) {
        this.gameBoard = gBoard;
        this.symbol    = symbol;
        this.name      = name;
    }

    public void playTurn() {
        int x;
        int y;
        while (!gameBoard.isGameOver()) {
            x = random.nextInt(gameBoard.size());
            y = random.nextInt(gameBoard.size());
            if (gameBoard.play(symbol, x, y)) {
                gameBoard.printMoveAndBoard(name, symbol, x, y);
                return;
            } else {
                System.out.println(name + " (" + symbol + ") -> Thinking...");
                try {
                    Thread.sleep(random.nextInt(2000)); // 0~2s
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void run() {
        while (!this.gameBoard.isGameOver()) {
            playTurn();
        }
    }
}
