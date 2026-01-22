import java.util.HashMap;
import java.util.Map;

public class RankingSystem implements Observer {
    private Map<String, Integer> wins = new HashMap<>();

    @Override
    public void update(String event) {
        if (event.contains("Wygrana")) {
            String winner = event.split(":")[1].trim();

            wins.put(winner, wins.getOrDefault(winner, 0) + 1);

            System.out.println("\n#################################");
            System.out.println("       AKTUALNY RANKING");
            System.out.println("#################################");
            if (wins.isEmpty()) {
                System.out.println("(Brak danych)");
            } else {
                wins.forEach((player, count) ->
                        System.out.println(" * " + player + ": " + count + " zwycięstw")
                );
            }
            System.out.println("#################################\n");
        }
    }
}