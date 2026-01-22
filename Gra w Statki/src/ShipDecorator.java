public abstract class ShipDecorator extends Ship {
    protected Ship wrappedShip;

    public ShipDecorator(Ship ship) {
        this.wrappedShip = ship;
        this.length = ship.getLength();
        this.health = ship.health;
    }

    @Override
    public boolean isSunk() {
        return wrappedShip.isSunk();
    }

    @Override
    public void hit() {
        wrappedShip.hit();
    }
}