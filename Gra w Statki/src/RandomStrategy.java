import java.util.Random;

public class RandomStrategy implements ShootingStrategy {
    private final Random rand = new Random();

    @Override
    public Point determineShot(Board enemyBoard) {
        int size = enemyBoard.getSize();

        while (true) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            Cell cell = enemyBoard.getCell(x, y);

            if (cell.status != CellStatus.HIT && cell.status != CellStatus.MISS) {
                return new Point(x, y);
            }
        }
    }
}