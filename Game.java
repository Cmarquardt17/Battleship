public class Game {
    private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    UserInput prompt = new UserInput();
    Board board = new Board();
    String[][] boardS = new String[10][10];
    boolean gameOver = false;
    boolean goodShot = false;

    public void startGame() {
        board.populateBoard(boardS);
        board.printBoard(boardS);
        while (gameOver != true) {
            String shot = prompt.getUserInput();
            goodShot = checkParam(shot);
            System.out.println(goodShot);
            if (goodShot == true) {
                String[][] outcome = board.shotCheck(boardS, shot);
                boardS = outcome;
                board.printBoard(boardS);
                gameOver = board.checkForWin();
                if (gameOver) {
                    System.out.println("Congrats you have finished the game!!");
                    break;
                }
            } else {
                System.out.println("This shot does not work");
            }

        }
    }

    public boolean checkParam(String shot) {
        boolean check = false;
        for (int x = 0; x < letters.length; x++) {
            for (int y = 1; y < 10; y++) {
                if (shot.compareTo(letters[x] + Integer.toString(y)) == 0) {
                    return true;
                } else {
                    check = false;
                }
            }

        }
        return check;
    }
}