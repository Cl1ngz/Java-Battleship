import java.util.*;

 public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MatchBuilder builder = new MatchBuilder();
        builder.setBoardSize(5);

        System.out.println("=== BATTLESHIP COMMANDER ===");
        System.out.println("Wybierz tryb gry:");
        System.out.println("1. Gracz vs Komputer");
        System.out.println("2. Symulacja (AI vs AI)");
        System.out.print("Twój wybór: ");

        int mode = scanner.nextInt();

        if (mode == 1) {
            builder.setPlayer1("Ty (Dowódca)", new InteractiveStrategy());

            System.out.println("\nWybierz poziom trudności przeciwnika:");
            System.out.println("1. Łatwy (Random - strzela na oślep)");
            System.out.println("2. Trudny (Smart - poluje i dobija)");
            System.out.print("Wybór: ");
            int diff = scanner.nextInt();

            ShootingStrategy botStrategy = (diff == 2) ? new SmartStrategy() : new RandomStrategy();
            String botName = (diff == 2) ? "Smart AI" : "Random AI";

            builder.setPlayer2(botName, botStrategy);

        } else if (mode == 2) {
            System.out.println("\nKonfiguracja AI 1 (Lewa strona):");
            System.out.println("1. Łatwy");
            System.out.println("2. Trudny");
            int ai1Diff = scanner.nextInt();
            ShootingStrategy s1 = (ai1Diff == 2) ? new SmartStrategy() : new RandomStrategy();
            String n1 = (ai1Diff == 2) ? "Smart Bot 1" : "Random Bot 1";

            System.out.println("\nKonfiguracja AI 2 (Prawa strona):");
            System.out.println("1. Łatwy");
            System.out.println("2. Trudny");
            int ai2Diff = scanner.nextInt();
            ShootingStrategy s2 = (ai2Diff == 2) ? new SmartStrategy() : new RandomStrategy();
            String n2 = (ai2Diff == 2) ? "Smart Bot 2" : "Random Bot 2";

            builder.setPlayer1(n1, s1);
            builder.setPlayer2(n2, s2);

        } else {
            System.out.println("Nieznany tryb.");
            return;
        }

        builder.buildAndStart();
    }
}