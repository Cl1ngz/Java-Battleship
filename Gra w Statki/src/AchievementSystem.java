import java.util.HashMap;
import java.util.Map;


public class AchievementSystem implements Observer {
    private Map<String, Integer> playerHits = new HashMap<>();

    @Override
    public void update(String event) {
        if (event.contains("Trafiony")) {
            String[] parts = event.split(":");
            String shooterName = parts[0].trim();

            int currentHits = playerHits.getOrDefault(shooterName, 0);
            currentHits++;

            playerHits.put(shooterName, currentHits);

            if (currentHits == 3) {
                System.out.println("\n>>>  OSIĄGNIĘCIE ODBLOKOWANE dla gracza " + shooterName + ": Snajper (3 celne strzały)  <<<");
            }

            if (currentHits == 10) {
                System.out.println("\n>>>  OSIĄGNIĘCIE ODBLOKOWANE dla gracza " + shooterName + ": Admirał (10 celnych strzałów)  <<<");
            }
        }
        else if (event.contains("Wygrana")) {
            System.out.println("\n>>>  OSIĄGNIĘCIE ODBLOKOWANE  Get The Feeling( za wygraną)  <<<");
        }
    }
}
