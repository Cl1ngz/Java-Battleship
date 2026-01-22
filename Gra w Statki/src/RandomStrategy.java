import java.util.Random;

public class RandomStrategy implements ShootingStrategy {
    private Random rand = new Random();

    @Override
    public Point determineShot(Board enemyBoard) {
        return new Point(rand.nextInt(enemyBoard.getSize()), rand.nextInt(enemyBoard.getSize()));
    }
}
