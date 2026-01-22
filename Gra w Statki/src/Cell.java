enum CellStatus {EMPTY, SHIP, HIT, MISS}

public class Cell {
    CellStatus status = CellStatus.EMPTY;
    Ship ship = null;

    public char getSymbol(boolean showHidden) {
        return switch (status) {
            case HIT -> 'X';
            case MISS -> 'O';
            case SHIP -> showHidden ? 'S' : '.';
            default -> '.';
        };
    }
}