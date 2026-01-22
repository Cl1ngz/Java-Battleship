public class Player {
    private String name;
    private Board board;
    private ShootingStrategy strategy;

    public Player(String name, int boardSize) {
        this.name = name;
        this.board = new Board(boardSize);
    }

    public void setStrategy(ShootingStrategy strategy) {
        this.strategy = strategy;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public Point makeMove(Board enemyBoard) {
        return strategy.determineShot(enemyBoard);
    }
    public ShootingStrategy getStrategy() { return strategy; }
}
