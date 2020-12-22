/**
 * Serves as the framework for different levels of AI
 * Has different methods that may be reused by different AI classes
 */
public abstract class AI {
    protected final int[] bestPosition; //the best position the AI wants to mark; this should be accessed to determine the game.move() parameters
    protected char ai;
    protected char opponent;

    public AI() {
       bestPosition = new int[2];
    }

    /**
     * @param board the given board configuration
     * @return  2D array representing the available slots on the board
     *          int[i][] represents the available position
     *          int[i][0] the row index
     *          int[i][1] the column index
     */
    public int[][] getAvailableSlots(char[][] board) {
        int[][] temp = new int[9][2];
        int availableCtr = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    temp[availableCtr][0] = i;
                    temp[availableCtr][1] = j;
                    availableCtr++;
                }
            }
        }
        int[][] available = new int[availableCtr][2];
        for (int i = 0; i < availableCtr; i++)
            System.arraycopy(temp[i], 0, available[i], 0, 2);

        return available;
    }

    /**
     * @param board the given board configuration
     * @return true if there is already a winner
     */
    public boolean hasWinner(char[][] board) {
        return getWinner(board) != 0;
    }

    /**
     * Gets the winning marker
     * @param board the given board configuration
     * @return the winning marker
     */
    public char getWinner(char[][] board) {
        //check for rows and columns
        for(int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0)
                return board[i][0];
            else if(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 0)
                return board[0][i];
        }

        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 0)
            return board[0][0];
        else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 0)
            return board[0][2];

        return 0;
    }

    /**
     * @param board the given board configuration
     * @return true if draw
     */
    public boolean isDraw(char[][] board) {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Return the position ai want to mark
     * @return an int[] representing the row and column index (int[0] - row; int[1] - column)
     */
    public int[] getBestPosition() {
        return  bestPosition;
    }

    /**
     * Sets the markers for AI to determine its and its opponents markers
     * @param ai marker of ai
     * @param opponent marker of opponent
     */
    public void setMarker(char ai, char opponent) {
        this.ai = ai;
        this.opponent = opponent;
    }
}
