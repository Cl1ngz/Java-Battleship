public class MatchBuilder {
    private int boardSize = 5;
    private ShootingStrategy p1Strategy = new InteractiveStrategy();
    private ShootingStrategy p2Strategy = new RandomStrategy();
    private String p1Name = "Gorgogemeus";
    private String p2Name = "Computer";

    public MatchBuilder setBoardSize(int size) {
        this.boardSize = size;
        return this;
    }

    public MatchBuilder setPlayer1(String name, ShootingStrategy strategy) {
        this.p1Name = name;
        this.p1Strategy = strategy;
        return this;
    }

    public MatchBuilder setPlayer2(String name, ShootingStrategy strategy) {
        this.p2Name = name;
        this.p2Strategy = strategy;
        return this;
    }

    public void buildAndStart() {
        Player p1 = new Player(p1Name, boardSize);
        p1.setStrategy(p1Strategy);

        Player p2 = new Player(p2Name, boardSize);
        p2.setStrategy(p2Strategy);

        GameEngine engine = GameEngine.getInstance();

        engine.configureMatch(p1, p2);
        engine.startGame();
    }
}