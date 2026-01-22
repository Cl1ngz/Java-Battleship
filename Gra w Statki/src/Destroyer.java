public class Destroyer extends Ship {
    public Destroyer() {
        this.length = 2;
        this.health = 2;
    }

    @Override
    public String getDescription() {
        return "Niszczyciel";
    }
}