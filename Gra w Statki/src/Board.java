import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int size;
    private final Cell[][] cells;
    private final List<Ship> ships = new ArrayList<>();

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                cells[i][j] = new Cell();
    }

    public int getSize() {
        return size;
    }

    public void placeShipRandomly(Ship ship) {
        Random rand = new Random();
        boolean placed = false;
        while (!placed) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            boolean horizontal = rand.nextBoolean();

            if (canPlace(ship, x, y, horizontal)) {
                doPlace(ship, x, y, horizontal);
                ships.add(ship);
                placed = true;
            }
        }
    }

    private boolean canPlace(Ship ship, int x, int y, boolean horizontal) {
        int length = ship.getLength();
        if (horizontal) {
            if (y + length > size) return false;
            for (int i = 0; i < length; i++) if (cells[x][y + i].status != CellStatus.EMPTY) return false;
        } else {
            if (x + length > size) return false;
            for (int i = 0; i < length; i++) if (cells[x + i][y].status != CellStatus.EMPTY) return false;
        }
        return true;
    }

    private void doPlace(Ship ship, int x, int y, boolean horizontal) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (horizontal) cells[x][y + i].status = CellStatus.SHIP;
            else cells[x + i][y].status = CellStatus.SHIP;

            if (horizontal) cells[x][y + i].ship = ship;
            else cells[x + i][y].ship = ship;
        }
    }

    public String receiveShot(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return "OUT_OF_BOUNDS";
        Cell cell = cells[x][y];

        if (cell.status == CellStatus.HIT || cell.status == CellStatus.MISS) return "ALREADY_SHOT";

        if (cell.status == CellStatus.SHIP) {
            cell.status = CellStatus.HIT;
            cell.ship.hit();
            return cell.ship.isSunk() ? "SUNK" : "HIT";
        } else {
            cell.status = CellStatus.MISS;
            return "MISS";
        }
    }

    public boolean allShipsSunk() {
        return ships.stream().allMatch(Ship::isSunk);
    }

    public void printBoard(boolean showHidden) {
        System.out.print("  ");
        for (int i = 0; i < size; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(cells[i][j].getSymbol(showHidden) + " ");
            }
            System.out.println();
        }
    }

    public String getRowString(int row, boolean showHidden) {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append(" "); // Numer wiersza
        for (int j = 0; j < size; j++) {
            sb.append(cells[row][j].getSymbol(showHidden)).append(" ");
        }
        return sb.toString();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}