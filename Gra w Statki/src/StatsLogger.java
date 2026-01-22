public class StatsLogger implements Observer {
    @Override
    public void update(String event) {
        System.out.println("[LOG]: Zapisano w historii: " + event);
    }
}
