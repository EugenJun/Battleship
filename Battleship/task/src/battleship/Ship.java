package battleship;

public class Ship {
    String battleShipName;
    int numOfCells;
    int[] shipCoords;

    Ship(String battleShipName, int numOfCells, int[] shipCoords) {
        this.battleShipName = battleShipName;
        this.numOfCells = numOfCells;
        this.shipCoords = shipCoords;
    }
}
