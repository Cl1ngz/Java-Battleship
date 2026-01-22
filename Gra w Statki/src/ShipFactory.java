public class ShipFactory {
    public static Ship createShip(String type) {
        return switch (type.toLowerCase()) {
            case "destroyer" -> new Destroyer();
            case "battleship" -> new Battleship();
            default -> throw new IllegalArgumentException("Unknown ship");
        };
    }
}