public abstract class Ship {
    protected int length;
    protected int health;

    public void hit() {
        health--;
    }

    public boolean isSunk() {
        return health <= 0;
    }

    public int getLength() {
        return length;
    }

    public abstract String getDescription();
}