public class Ship {
    private String name;
    private int lives;
    private String letterType;

    public Ship(String name, int lives, String letterType) {
        this.name = name;
        this.lives = lives;
        this.letterType = letterType;

    }

    public String[][] populateBoardWithShip(String[][] board, Ship ship) {
        int rowNum = (int) (Math.random() * 9);
        int colNum = (int) (Math.random() * 9);
        String[] letterShips = { "BB", "CC", "SS", "DD" };
        String[] revisedLetterShips = new String[3];

        for (int y = 0; y < letterShips.length - 1; y++) {
            if (letterShips[y] != ship.getShipLetter()) {
                revisedLetterShips[y] = letterShips[y];
            }

        }

        boolean test = false;

        while (test != true) {
            if (board.length - rowNum > ship.getShipLives()) {
                for (int x = 0; x < ship.getShipLives(); x++) {
                    for (int y = 0; y < 3; y++) {
                        if (board[rowNum + x][colNum] == revisedLetterShips[y]) {
                            break;
                        }
                    }
                    board[rowNum + x][colNum] = ship.getShipLetter();
                }
                test = true;
            }

            else if (board.length - colNum > ship.getShipLives()) {
                for (int x = 0; x < ship.getShipLives(); x++) {
                    for (int y = 0; y < 3; y++) {
                        if (board[rowNum][colNum + x] == revisedLetterShips[y]) {
                            break;
                        }
                    }
                    board[rowNum][colNum + x] = ship.getShipLetter();
                }
                test = true;
            }
        }
        return board;
    }

    public void negateShipLives(int lives){
        this.lives = lives -1;
    }

    public int getShipLives() {
        return this.lives;
    }

    public String getShipName() {
        return this.name;
    }

    public String getShipLetter() {
        return this.letterType;
    }
}