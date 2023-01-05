package battleship;

public class Main {

    public static void main(String[] args) {

        boolean active = true;
        int counter = 1;
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        firstPlayer.allocateBattleships(counter);
        secondPlayer.allocateBattleships(counter + 1);

        while (active) {
            if (firstPlayer.isHavingShips() && secondPlayer.isHavingShips()) {
                firstPlayer.makeShot(firstPlayer, secondPlayer, counter);
                counter++;
                secondPlayer.makeShot(secondPlayer, firstPlayer, counter);
                counter--;
            } else if (!firstPlayer.isHavingShips() && secondPlayer.isHavingShips()) {
                System.out.println("\nSecond player won!");
                active = false;
            } else if (firstPlayer.isHavingShips() && !secondPlayer.isHavingShips()) {
                System.out.println("\nFirst player won!");
                active = false;
            }
        }
    }
}
