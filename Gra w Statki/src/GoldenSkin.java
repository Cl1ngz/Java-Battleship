public class GoldenSkin extends ShipDecorator {
    public GoldenSkin(Ship ship) {
        super(ship);
    }

    @Override
    public String getDescription() {
        return "Złoty " + wrappedShip.getDescription();
    }
}
