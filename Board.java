import java.util.*;

public class Board {
    // Set up the board
    private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    private int count = 0;
    // Ships being used
    private ArrayList<Ship> ships2 = new ArrayList<Ship>();
    //Ships that have been sunk so far
    private ArrayList<String> sunkenShips2 = new ArrayList<String>();

    private int hit = 0;
    private int miss = 0;

    public ArrayList<Ship> initShips() {
        Ship battle = new Ship("Battleship", 4, "BB");
        Ship cruiser= new Ship("Cruiser", 3, "CC");
        Ship submarine = new Ship("Submarine", 3, "SS");
        Ship destroyer = new Ship("Destroyer", 2, "DD");
        ships2.add(battle);
        ships2.add(cruiser);
        ships2.add(submarine);
        ships2.add(destroyer);
        return ships2;
    }

    // This creates our initial layout for the board
    public void populateBoard(String[][] board) {
        for (int x = 1; x < 10; x++) {
            for (int y = 1; y < 10; y++) {
                board[y - 1][x - 1] = letters[x - 1] + Integer.toString(y);
            }
        }

        if (count == 0) {
            ships2 = initShips();
            count++;
            for (Ship ship : ships2) {
                System.out.println(ship.getShipName());
                board = ship.populateBoardWithShip(board, ship);
            }
        }
    }

    public void printBoard(String[][] boardOutcome) {
        System.out.print("|   ");
        for (int x = 0; x < 9; x++) {
            System.out.print("|  " + letters[x] + "  ");
            if (x == 8) {
                System.out.print("|");
            }
        }
        System.out.print("\n|---|-----|-----|-----|-----|-----|-----|-----|-----|-----|");
        for (int x = 1; x < 10; x++) {
            System.out.println();
            System.out.print("| " + x);
            for (int y = 1; y < 10; y++) {
                if (boardOutcome[x - 1][y - 1].compareTo("OO") == 0
                        || boardOutcome[x - 1][y - 1].compareTo("XX") == 0) {
                    String printMissLetters = String.format(" |  " + "%s", boardOutcome[x - 1][y - 1]);
                    System.out.print(printMissLetters);
                } else {
                    String printLetters = String.format(" |  " + "%s", "__");
                    System.out.print(printLetters);
                }
                if (y == 9) {
                    System.out.print(" |");
                }
            }
        }
        System.out.print("\n-----------------------------------------------------------");
        System.out.println("\n");
        for (String ship_down : sunkenShips2) {
            System.out.println(ship_down);
        }
        System.out.print("Hits: " + hit + " Misses: " + miss + "\n");
    }

    // We are checking to see if we got a shot
    public String[][] shotCheck(String[][] board, String shot) {
        String[] splitShot = shot.split("");
        String firstLetter = splitShot[0];
        int secondLetter = Integer.parseInt(splitShot[1]);

        int[] spotCalc = spotCalc(firstLetter, secondLetter);

        if (board[spotCalc[1]][spotCalc[0]].compareTo(shot) == 0) {
            board[spotCalc[1]][spotCalc[0]] = "OO";
            miss += 1;
            return board;
        } else if (board[spotCalc[1]][spotCalc[0]].compareTo("OO") == 0
                || board[spotCalc[1]][spotCalc[0]].compareTo("XX") == 0) {
            System.out.println("You have already shot there");
        } else if (board[spotCalc[1]][spotCalc[0]].compareTo(shot) != 0 && board[spotCalc[1]][spotCalc[0]] != "OO"
                && board[spotCalc[1]][spotCalc[0]] != "XX") {
            String potentialShot = shipCheck(shot, board[spotCalc[1]][spotCalc[0]]);
            board[spotCalc[1]][spotCalc[0]] = potentialShot;
            hit += 1;
            return board;
        } else {
            return board;
        }
        return board;
    }

    public int[] spotCalc(String letter, int number) {
        int[] spot = new int[2];
        for (int x = 0; x < letters.length; x++) {
            if (letter.compareTo(letters[x]) == 0) {
                spot[0] = x;
                spot[1] = number - 1;
            }
        }
        return spot;

    }

    public String shipCheck(String shot, String spot) {
        for (Ship ship : ships2) {
            if (ship.getShipLetter().compareTo(spot) == 0) {
                System.out.println("We got a hit!");
                int lives = ship.getShipLives();
                ship.negateShipLives(lives);
                if (ship.getShipLives() == 0) {
                    sunkenShips2.add(ship.getShipName());
                    System.out.println("You have sunk the " + ship.getShipName() + "!");
                    ships2.remove(ship);
                }
                return "XX";
            }
        }
        return spot;
    }

    public boolean checkForWin() {
        if (ships2.isEmpty()) {
            return true;
        }
        System.out.println("We got to this point");
        return false;
    }
}