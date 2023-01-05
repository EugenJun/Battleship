package battleship;

import java.util.Scanner;

public class Player {

    int numOfShips;
    String[] battleshipNames;
    int[] numOfCells;
    Scanner scanner = new Scanner(System.in);
    Table playerTable;
    Table foggedPlayerTable;
    boolean havingShips;
    Ship aircraftCarrier;
    Ship battleship;
    Ship submarine;
    Ship cruiser;
    Ship destroyer;
    int sunkShipsCounter;

    public Player() {
        this.numOfShips = 5;
        this.playerTable = new Table();
        this.foggedPlayerTable = new Table();
        this.havingShips = true;
        this.aircraftCarrier = new Ship("Aircraft Carrier", 5, new int[4]);
        this.battleship = new Ship("Battleship", 4, new int[4]);
        this.submarine = new Ship("Submarine", 3, new int[4]);
        this.cruiser = new Ship("Cruiser", 3, new int[4]);
        this.destroyer = new Ship("Destroyer", 2, new int[4]);
        this.battleshipNames = new String[]{aircraftCarrier.battleShipName, battleship.battleShipName,
                submarine.battleShipName, cruiser.battleShipName, destroyer.battleShipName};
        this.numOfCells = new int[]{aircraftCarrier.numOfCells, battleship.numOfCells, submarine.numOfCells,
                cruiser.numOfCells, destroyer.numOfCells};
        this.sunkShipsCounter = 0;
    }

    public static void pressEnterToCont() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
    }

    protected void allocateBattleships(int playerNum) {
        System.out.println("Player " + playerNum + ", place your ships on the game field\n");

        this.playerTable.drawTable();
        int counter = 0;

        while (this.numOfShips > 0) {
            System.out.println("\nEnter the coordinates of the " + this.battleshipNames[counter] +
                    " (" + this.numOfCells[counter] + " cells): ");
            String userInput = scanner.nextLine().trim();
            String[] numericalIndexes = userInput.split("[a-zA-Z\\s]+");
            String[] alphabeticalIndexes = userInput.split("[0-9\\s]+");
            int firstNumericIndex = Integer.parseInt(numericalIndexes[1]);
            int secondNumericIndex = Integer.parseInt(numericalIndexes[2]);
            int firstAlphaIndex = new String(this.playerTable.getVerticalIndices()).indexOf(alphabeticalIndexes[0].charAt(0));
            int secondAlphaIndex = new String(this.playerTable.getVerticalIndices()).indexOf(alphabeticalIndexes[1].charAt(0));
            int minAlphaInd = Math.min(secondAlphaIndex, firstAlphaIndex);
            int maxAlphaInd = Math.max(secondAlphaIndex, firstAlphaIndex);
            int minNumInd = Math.min(secondNumericIndex, firstNumericIndex);
            int maxNumInd = Math.max(secondNumericIndex, firstNumericIndex);

            if (this.checkShipPlacementCoordinates(firstNumericIndex, firstAlphaIndex,
                    secondNumericIndex, secondAlphaIndex, this.numOfCells[counter],
                    minAlphaInd, maxAlphaInd, minNumInd, maxNumInd)) {
                this.playerTable.updateTable(firstNumericIndex, firstAlphaIndex, secondNumericIndex, secondAlphaIndex,
                        minAlphaInd, maxAlphaInd, minNumInd, maxNumInd);
                this.playerTable.drawTable();
                switch (counter) {
                    case 0 -> {
                        aircraftCarrier.shipCoords[0] = minNumInd;
                        aircraftCarrier.shipCoords[1] = maxNumInd;
                        aircraftCarrier.shipCoords[2] = minAlphaInd;
                        aircraftCarrier.shipCoords[3] = maxAlphaInd;
                    }
                    case 1 -> {
                        battleship.shipCoords[0] = minNumInd;
                        battleship.shipCoords[1] = maxNumInd;
                        battleship.shipCoords[2] = minAlphaInd;
                        battleship.shipCoords[3] = maxAlphaInd;
                    }
                    case 2 -> {
                        submarine.shipCoords[0] = minNumInd;
                        submarine.shipCoords[1] = maxNumInd;
                        submarine.shipCoords[2] = minAlphaInd;
                        submarine.shipCoords[3] = maxAlphaInd;
                    }
                    case 3 -> {
                        cruiser.shipCoords[0] = minNumInd;
                        cruiser.shipCoords[1] = maxNumInd;
                        cruiser.shipCoords[2] = minAlphaInd;
                        cruiser.shipCoords[3] = maxAlphaInd;
                    }
                    case 4 -> {
                        destroyer.shipCoords[0] = minNumInd;
                        destroyer.shipCoords[1] = maxNumInd;
                        destroyer.shipCoords[2] = minAlphaInd;
                        destroyer.shipCoords[3] = maxAlphaInd;
                    }
                }
                counter++;
                this.numOfShips--;
            }
        }
        Player.pressEnterToCont();
    }

    private boolean checkShipPlacementCoordinates(int firstNumInd, int firstAlphaInd, int secondNumInd,
                                                  int secondAlphaInd, int numOfShipCells, int minAlphaInd,
                                                  int maxAlphaInd, int minNumInd, int maxNumInd) {

        boolean verticalCoordinatesCheck = Math.abs(secondAlphaInd - firstAlphaInd) + 1 == numOfShipCells;
        boolean horizontalCheck = Math.abs(secondNumInd - firstNumInd) + 1 == numOfShipCells;

        if ((firstNumInd <= 0 || firstNumInd > 10) || (secondNumInd <= 0 || secondNumInd > 10) ||
                (firstAlphaInd == -1) || (secondAlphaInd == -1)) {
            System.out.print("\nError! You must enter a horizontal coordinates between 0 and 10" +
                    " and vertical coordinates between A and J. ");
            return false;
        } else if (firstNumInd == secondNumInd && verticalCoordinatesCheck) {
            return this.checkCellsByEqualNumeric(minAlphaInd, maxAlphaInd, minNumInd);
        } else if (horizontalCheck && firstAlphaInd == secondAlphaInd) {
            return this.checkCellsByEqualAlpha(minNumInd, maxNumInd, minAlphaInd);
        } else {
            System.out.print("\nError! Wrong ship location! ");
            return false;
        }
    }

    private boolean checkShotCoordinates(int numInd, int alphaInd) {
        if (numInd <= 0 || numInd > 10 || alphaInd == -1) {
            System.out.print("""

                    Error! You must enter a horizontal coordinates between 0 and 10 and vertical coordinates between A and J.\s

                    """);
            return false;
        }
        return true;
    }

    private void recordHits(Player targerPlayer, int numericIndex, int alphaIndex) {
        if ((numericIndex >= targerPlayer.aircraftCarrier.shipCoords[0] && numericIndex <= targerPlayer.aircraftCarrier.shipCoords[1]) &&
                (alphaIndex >= targerPlayer.aircraftCarrier.shipCoords[2] && alphaIndex <= targerPlayer.aircraftCarrier.shipCoords[3])) {
            targerPlayer.numOfCells[0]--;
        } else if ((numericIndex >= targerPlayer.battleship.shipCoords[0] && numericIndex <= targerPlayer.battleship.shipCoords[1]) &&
                (alphaIndex >= targerPlayer.battleship.shipCoords[2] && alphaIndex <= targerPlayer.battleship.shipCoords[3])) {
            targerPlayer.numOfCells[1]--;
        } else if ((numericIndex >= targerPlayer.submarine.shipCoords[0] && numericIndex <= targerPlayer.submarine.shipCoords[1]) &&
                (alphaIndex >= targerPlayer.submarine.shipCoords[2] && alphaIndex <= targerPlayer.submarine.shipCoords[3])) {
            targerPlayer.numOfCells[2]--;
        } else if ((numericIndex >= targerPlayer.cruiser.shipCoords[0] && numericIndex <= targerPlayer.cruiser.shipCoords[1]) &&
                (alphaIndex >= targerPlayer.cruiser.shipCoords[2] && alphaIndex <= targerPlayer.cruiser.shipCoords[3])) {
            targerPlayer.numOfCells[3]--;
        } else if ((numericIndex >= targerPlayer.destroyer.shipCoords[0] && numericIndex <= targerPlayer.destroyer.shipCoords[1]) &&
                (alphaIndex >= targerPlayer.destroyer.shipCoords[2] && alphaIndex <= targerPlayer.destroyer.shipCoords[3])) {
            targerPlayer.numOfCells[4]--;
        }
    }

    private boolean checkSunkShips(Player targetPlayer) {
        for (int i = 0; i < targetPlayer.numOfCells.length; i++) {
            if (targetPlayer.numOfCells[i] == 0) {
                System.out.println("You sank a ship!");
                targetPlayer.numOfCells[i] = -1;
                return true;
            }
        }
        return false;
    }

    protected void makeShot(Player actualPlayer, Player targetPlayer, int counter) {

        targetPlayer.foggedPlayerTable.drawTable();
        System.out.println("---------------------");
        actualPlayer.playerTable.drawTable();
        System.out.println("\nPlayer " + counter + ", it's your turn:");

        String userShotInput = scanner.nextLine().trim();
        String[] numericalIndexes = userShotInput.split("[a-zA-Z\\s]+");
        String[] alphabeticalIndexes = userShotInput.split("[0-9\\s]+");
        int numericIndex = Integer.parseInt(numericalIndexes[1]);
        int alphaIndex = new String(targetPlayer.playerTable.getVerticalIndices()).indexOf(alphabeticalIndexes[0].charAt(0));

        if (checkShotCoordinates(numericIndex, alphaIndex)) {
            if (targetPlayer.playerTable.getBattleCell(alphaIndex, numericIndex - 1) == 'O') {
                targetPlayer.foggedPlayerTable.updateTable(numericIndex - 1, alphaIndex, true);
                targetPlayer.playerTable.updateTable(numericIndex - 1, alphaIndex, true);
                recordHits(targetPlayer, numericIndex, alphaIndex);
                if (!checkSunkShips(targetPlayer)) {
                    System.out.println("You hit a ship!");
                    Player.pressEnterToCont();
                } else {
                    targetPlayer.sunkShipsCounter++;
                    if (targetPlayer.sunkShipsCounter == 5) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        targetPlayer.havingShips = false;
                    }
                    Player.pressEnterToCont();
                }

            } else if (targetPlayer.playerTable.getBattleCell(alphaIndex, numericIndex - 1) == '~') {
                targetPlayer.foggedPlayerTable.updateTable(numericIndex - 1, alphaIndex, false);
                targetPlayer.playerTable.updateTable(numericIndex - 1, alphaIndex, false);
                System.out.println("You missed!");
                Player.pressEnterToCont();
            } else {
                System.out.println("You missed!");
                Player.pressEnterToCont();
            }
        }
    }

    public boolean isHavingShips() {
        return havingShips;
    }

    private boolean checkCellsByEqualNumeric(int minAlphaInd, int maxAlphaInd, int numInd) {
        if (numInd == 1 && minAlphaInd == 0) {
            for (int i = 0; i <= numInd; i++) {
                for (int j = minAlphaInd; j <= maxAlphaInd + 1; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else if (numInd == 10 && minAlphaInd == 0) {
            for (int i = numInd - 2; i < numInd; i++) {
                for (int j = minAlphaInd; j <= maxAlphaInd + 1; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else if (numInd == 1 && maxAlphaInd == 9) {
            for (int i = 0; i <= numInd; i++) {
                for (int j = minAlphaInd - 1; j <= maxAlphaInd; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else if (numInd == 10 && maxAlphaInd == 9) {
            for (int i = numInd - 2; i < numInd; i++) {
                for (int j = minAlphaInd - 1; j <= maxAlphaInd; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else if (maxAlphaInd == 9 || minAlphaInd == 0) {
            for (int i = numInd - 2; i <= numInd; i++) {
                if (maxAlphaInd == 9) {
                    for (int j = minAlphaInd - 1; j <= maxAlphaInd; ++j) {
                        if (!(playerTable.getBattleCell(j, i) == '~')) {
                            System.out.print("\nError! Cannot place it here! ");
                            return false;
                        }
                    }
                } else {
                    for (int j = minAlphaInd; j <= maxAlphaInd + 1; ++j) {
                        if (!(playerTable.getBattleCell(j, i) == '~')) {
                            System.out.print("\nError! Cannot place it here! ");
                            return false;
                        }
                    }
                }

            }
        } else if (numInd == 1) {
            for (int i = 0; i <= numInd; i++) {
                for (int j = minAlphaInd - 1; j <= maxAlphaInd + 1; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else if (numInd == 10) {
            for (int i = numInd - 2; i < numInd; i++) {
                for (int j = minAlphaInd - 1; j <= maxAlphaInd + 1; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        } else {
            for (int i = numInd - 2; i <= numInd; i++) {
                for (int j = minAlphaInd - 1; j <= maxAlphaInd + 1; ++j) {
                    if (!(playerTable.getBattleCell(j, i) == '~')) {
                        System.out.print("\nError! Cannot place it here! ");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkCellsByEqualAlpha(int minNumInd, int maxNumInd, int alphaInd) {
        if (alphaInd == 0 && minNumInd == 1) {
            for (int i = 0; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (alphaInd == 0 && maxNumInd == 10) {
            for (int i = minNumInd - 2; i < maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (alphaInd == 9 && minNumInd == 1) {
            for (int i = 0; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (alphaInd == 9 && maxNumInd == 10) {
            for (int i = minNumInd - 2; i < maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (maxNumInd == 10) {
            for (int i = minNumInd - 2; i < maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (minNumInd == 1) {
            for (int i = 0; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (alphaInd == 9) {
            for (int i = minNumInd - 2; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else if (alphaInd == 0) {
            for (int i = minNumInd - 2; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        } else {
            for (int i = minNumInd - 2; i <= maxNumInd; ++i) {
                if (!(playerTable.getBattleCell(alphaInd, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd - 1, i) == '~') ||
                        !(playerTable.getBattleCell(alphaInd + 1, i) == '~')) {
                    System.out.print("\nError! Cannot place it here! ");
                    return false;
                }
            }
        }
        return true;
    }
}

