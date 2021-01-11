public class MyGame {

    private char[][] board;
    private int numGames;
    private char human;
    private char ai; //this is AI
    private String currentPlayer;

    public MyGame() {
        board = new char[3][3];
        numGames = 1;
        currentPlayer = "Human";

        human = 'X';
        ai = 'O';
    }

    /**
     * Places the markers on the board
     * @param row row index
     * @param col column index
     */
    public void move(int row, int col) {
        if((row >= 0 && row <= 2) && (col >= 0 && col <= 2) && board[row][col] == 0 && !hasWinner() && !isDraw()){
            if (currentPlayer.equals("Human")) {
                board[row][col] = human;
                currentPlayer = "AI";
            } else if (currentPlayer.equals("AI")){
                board[row][col] = ai;
                currentPlayer = "Human";
            }
        }
        else {
            System.out.println("Invalid Move: " + row + " " + col);
        }
    }

    /**
     * @return true if getWinner() returns an int[][] corresponding to the winning section
     */
    public boolean hasWinner() {
        return getWinner() != null;
    }

    /**
     * @return the winning section of the board
     */
    public int[][] getWinner() {
        //check for rows and columns
        for(int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0)
                return new int[][] {{i, 0}, {i, 1}, {i, 2}};
            else if(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 0)
                return new int[][] {{0, i}, {1, i}, {2, i}};
        }

        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 0)
            return new int[][] {{0, 0}, {1, 1}, {2, 2}};
        else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 0)
            return new int[][] {{0, 2}, {1, 1}, {2, 0}};

        return null;
    }

    /**
     * @return true if draw
     */
    public boolean isDraw() {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Resets the board
     * Switches player
     */
    public void resetBoard() {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                board[i][j] = 0;
        }
        numGames++;

        if(numGames % 2 != 0) {
            human = 'X';
            ai = 'O';
            currentPlayer = "Human";    //player one first to move
        }
        else {
            human = 'O';
            ai = 'X';
            currentPlayer = "AI"; //player two first to move
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public char getHuman() {
        return human;
    }

    public char getAI() {
        return  ai;
    }
    public char[][] getBoard() {
        return board;
    }
}
