import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private static GameEngine instance;
    private Player player1;
    private Player player2;
    private List<Observer> observers = new ArrayList<>();
    private boolean isGameOver = false;

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null) instance = new GameEngine();
        return instance;
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    private void notifyObservers(String event) {
        for (Observer o : observers) o.update(event);
    }

    public void configureMatch(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.isGameOver = false;
    }

    public void startGame() {
        System.out.println("\n=== ROZPOCZĘCIE BITWY MORSKIEJ ===");
        setupShips(player1);
        setupShips(player2);

        Player current = player1;
        Player enemy = player2;

        while (!isGameOver) {
            playTurn(current, enemy);
            if (enemy.getBoard().allShipsSunk()) {
                System.out.println("\n!!! KONIEC GRY !!! Zwyciężył: " + current.getName());
                notifyObservers("Wygrana: " + current.getName());
                isGameOver = true;
            } else {
                Player temp = current;
                current = enemy;
                enemy = temp;
            }
        }
    }

    private void setupShips(Player p) {
        Ship s1 = ShipFactory.createShip("destroyer");
        Ship s2 = ShipFactory.createShip("battleship");
        Ship s3 = new GoldenSkin(ShipFactory.createShip("destroyer")); // Udekorowany statek

        p.getBoard().placeShipRandomly(s1);
        p.getBoard().placeShipRandomly(s2);
        p.getBoard().placeShipRandomly(s3);
    }

    private void playTurn(Player shooter, Player target) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" TURA GRACZA: " + shooter.getName());
        System.out.println("=".repeat(60));

        boolean shooterIsHuman = shooter.getStrategy() instanceof InteractiveStrategy;
        boolean targetIsHuman = target.getStrategy() instanceof InteractiveStrategy;

        boolean showMap = true;

        if (!shooterIsHuman && targetIsHuman) {
            showMap = false;
        }

        if (showMap) {
            printSideBySide(shooter.getBoard(), target.getBoard());
        }

        Point shot = shooter.makeMove(target.getBoard());

        System.out.println("\n>>> " + shooter.getName() + " strzela w pole: [" + shot.x + ", " + shot.y + "]");
        String result = target.getBoard().receiveShot(shot.x, shot.y);

        System.out.println(">>> WYNIK: " + result);

        if (result.equals("HIT") || result.equals("SUNK")) {
            notifyObservers(shooter.getName() + ": Trafiony statek!");
        }

        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    }

    private void printSideBySide(Board myBoard, Board enemyBoard) {
        int size = myBoard.getSize();

        String separator = "   |   ";

        System.out.printf("%-12s" + separator + "%-12s%n", "[TWOJA FLOTA]", "[RADAR - WROG]");

        System.out.print("  ");
        for (int i = 0; i < size; i++) System.out.print(i + " ");

        System.out.print(separator);

        System.out.print("  ");
        for (int i = 0; i < size; i++) System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.println(
                    myBoard.getRowString(i, true) +
                            separator +
                            enemyBoard.getRowString(i, false)
            );
        }
    }
}