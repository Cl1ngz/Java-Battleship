import java.util.Random;


public class SmartStrategy implements ShootingStrategy {
    private Random rand = new Random();

    @Override
    public Point determineShot(Board enemyBoard) {
        int size = enemyBoard.getSize();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Cell cell = enemyBoard.getCell(x, y);

                if (cell.status == CellStatus.HIT && cell.ship != null && !cell.ship.isSunk()) {

                    boolean horizontalHit = isHit(enemyBoard, x, y - 1) || isHit(enemyBoard, x, y + 1);
                    boolean verticalHit = isHit(enemyBoard, x - 1, y) || isHit(enemyBoard, x + 1, y);

                    // LPGD
                    if (horizontalHit) {
                        Point target = checkNeighbor(enemyBoard, x, y - 1);
                        if (target != null) return target;

                        target = checkNeighbor(enemyBoard, x, y + 1);
                        if (target != null) return target;
                    } else if (verticalHit) {
                        Point target = checkNeighbor(enemyBoard, x - 1, y);
                        if (target != null) return target;

                        target = checkNeighbor(enemyBoard, x + 1, y);
                        if (target != null) return target;
                    } else {
                        // GPDL
                        Point target = checkNeighbor(enemyBoard, x - 1, y);
                        if (target != null) return target;

                        target = checkNeighbor(enemyBoard, x, y + 1);
                        if (target != null) return target;

                        target = checkNeighbor(enemyBoard, x + 1, y);
                        if (target != null) return target;

                        target = checkNeighbor(enemyBoard, x, y - 1);
                        if (target != null) return target;
                    }
                }
            }
        }

        while (true) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            Cell cell = enemyBoard.getCell(x, y);

            if (cell.status != CellStatus.HIT && cell.status != CellStatus.MISS) {
                // strzela co 2 troche lepsze
                 if ((x + y) % 2 == 0) return new Point(x, y);

                return new Point(x, y);
            }
        }
    }

    private boolean isHit(Board board, int x, int y) {
        if (x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize()) {
            return board.getCell(x, y).status == CellStatus.HIT;
        }
        return false;
    }

    private Point checkNeighbor(Board board, int x, int y) {
        if (x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize()) {
            Cell cell = board.getCell(x, y);
            if (cell.status != CellStatus.HIT && cell.status != CellStatus.MISS) {
                return new Point(x, y);
            }
        }
        return null;
    }
}