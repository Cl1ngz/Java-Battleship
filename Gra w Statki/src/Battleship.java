public class Battleship extends Ship {
    public Battleship() {
        this.length = 4;
        this.health = 4;
    }

    @Override
    public String getDescription() {
        return "Pancernik";
    }
}