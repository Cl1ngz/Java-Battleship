import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GameEngine engine = GameEngine.getInstance();
        engine.addObserver(new StatsLogger());
        engine.addObserver(new AchievementSystem());
        engine.addObserver(new RankingSystem());

        boolean keepPlaying = true;

        while (keepPlaying) {
            MatchBuilder builder = new MatchBuilder();
            builder.setBoardSize(5);

            System.out.println("\n=== BATTLESHIP COMMANDER ===");
            System.out.println("1. Nowa Gra (Gracz vs Komputer)");
            System.out.println("2. Symulacja (AI vs AI)");
            System.out.println("3. Wyjście z programu");
            System.out.print("Twój wybór: ");

            int mode;
            try {
                mode = scanner.nextInt();
            } catch(Exception e) {
                scanner.nextLine();
                continue;
            }

            if (mode == 3) {
                System.out.println("Do zobaczenia, Admirale!");
                break;
            }

            if (mode == 1) {
                builder.setPlayer1("Ty (Dowódca)", new InteractiveStrategy());

                System.out.println("\nWybierz poziom trudności przeciwnika:");
                System.out.println("1. Łatwy (Random - strzela na oślep)");
                System.out.println("2. Trudny (Smart - poluje i dobija)");
                System.out.print("Twój wybór: ");
                int diff = scanner.nextInt();

                builder.setPlayer2(diff == 2 ? "Smart Gigol" : "Random Barti", diff == 2 ? new SmartStrategy() : new RandomStrategy());

            } else if (mode == 2) {
                System.out.println("\n[Bot 1 - Lewa strona] Wybierz poziom:");
                System.out.println("1. Łatwy (Random)");
                System.out.println("2. Trudny (Smart)");
                int d1 = scanner.nextInt();
                ShootingStrategy s1 = (d1 == 2) ? new SmartStrategy() : new RandomStrategy();
                String n1 = (d1 == 2) ? "Smart Gigol A" : "Random Barti A";

                System.out.println("\n[Bot 2 - Prawa strona] Wybierz poziom:");
                System.out.println("1. Łatwy (Random)");
                System.out.println("2. Trudny (Smart)");
                int d2 = scanner.nextInt();
                ShootingStrategy s2 = (d2 == 2) ? new SmartStrategy() : new RandomStrategy();
                String n2 = (d2 == 2) ? "Smart Gigol B" : "Random Barti B";

                builder.setPlayer1(n1, s1);
                builder.setPlayer2(n2, s2);
            } else {
                continue;
            }

            builder.buildAndStart();

            System.out.println("\nRozgrywka zakończona.");
            System.out.println("Czy chcesz zagrać ponownie? (t/n)");
            String decision = scanner.next();

            if (decision.equalsIgnoreCase("n")) {
                keepPlaying = false;
                System.out.println("Koniec służby. Do zobaczenia!");
            } else {
                System.out.println("Resetowanie planszy...\n");
            }
        }
    }

}