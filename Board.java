public class Board {
    // Set up the board
    private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    private int count = 0;
    // Ships being used
    private Ship[] ships = new Ship[4];
    private int hit = 0;
    private int miss = 0;
    private String[] sunkenShips = new String[4];

    public Ship[] initShips() {

        ships[0] = new Ship("Battleship", 4, "BB");
        ships[1] = new Ship("Cruiser", 3, "CC");
        ships[2] = new Ship("Submarine", 3, "SS");
        ships[3] = new Ship("Destroyer", 2, "DD");
        return ships;
    }

    // This creates our initial layout for the board
    public void populateBoard(String[][] board) {
        for (int x = 1; x < 10; x++) {
            for (int y = 1; y < 10; y++) {
                board[y - 1][x - 1] = letters[x - 1] + Integer.toString(y);
            }
        }

        if (count == 0) {
            ships = initShips();
            count++;
            for (int x = 0; x < 4; x++) {
                board = ships[x].populateBoardWithShip(board, ships[x]);
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
        for (int x = 0; x < sunkenShips.length; x++) {
            if (sunkenShips[x] != null) {
                System.out.println(sunkenShips[x]);
            }
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
        for (int x = 0; x < 4; x++) {
            if (ships[x].getShipLetter().compareTo(spot) == 0) {
                System.out.println("We got a hit!");
                int lives = ships[x].getShipLives();
                ships[x].negateShipLives(lives);
                if (ships[x].getShipLives() == 0) {
                    sunkenShips[x] = "You have sunk the " + ships[x].getShipName() + "!";
                }
                return "XX";
            }
        }
        return spot;
    }

    public boolean checkForWin() {
        for (int x = 0; x < sunkenShips.length; x++) {
            if (sunkenShips[x] == null) {
                return false;
            }
        }
        System.out.println("We got to this point");
        return true;
    }
}