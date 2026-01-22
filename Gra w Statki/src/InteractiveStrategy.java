import java.util.Scanner;

public class InteractiveStrategy implements ShootingStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Point determineShot(Board enemyBoard) {
        int size = enemyBoard.getSize();

        while (true) {
            System.out.print("Podaj koordynaty strzału (wiersz kolumna, np. 1 3): ");
            try {
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                if (x < 0 || x >= size || y < 0 || y >= size) {
                    System.out.println(">>> BŁĄD: Współrzędne poza planszą! Podaj liczby od 0 do " + (size - 1));
                    continue;
                }

                Cell cell = enemyBoard.getCell(x, y);
                if (cell.status == CellStatus.HIT || cell.status == CellStatus.MISS) {
                    System.out.println(">>> BŁĄD: Już strzelałeś w to pole! Nie marnuj amunicji.");
                    continue;
                }

                return new Point(x, y);

            } catch (Exception e) {
                System.out.println(">>> BŁĄD: To nie są liczby! Użyj formatu '1 3'.");
                scanner.nextLine();
            }
        }
    }
}
