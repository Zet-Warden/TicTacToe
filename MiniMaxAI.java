public class MiniMaxAI extends AI {

    public MiniMaxAI() {
        super();
    }

    /**
     * Performs the minimax algorithm to get the optimal board state given a board configuration
     * Creates a tree that represents the different possible board states
     * Changes the value of bestPosition variable to get the row and col index of the determined optimal board state
     * @param board the current board configuration
     * @param depth the depth of recursion from the tree
     * @param isMaximizing the current player (maximizing player - ai; minimizing player - opponent)
     * @return the static evaluation of the game state
     */
    public double miniMax(char[][] board, int depth, boolean isMaximizing) {
        if(hasWinner(board)) {
            if(ai == getWinner(board))
                return 1;
            else if (opponent == getWinner(board))
                return -1;
        }
        else if (isDraw(board))
            return 0;

       if(isMaximizing) {
           double maxEval = Double.NEGATIVE_INFINITY;
           int[][] availableSlots = getAvailableSlots(board);
           for (int[] availableSlot : availableSlots) {
               board[availableSlot[0]][availableSlot[1]] = ai;
               double eval = miniMax(board, depth + 1, false);
               board[availableSlot[0]][availableSlot[1]] = 0;
               if (eval > maxEval) {
                   maxEval = eval;
                   if (depth == 0) {
                       bestPosition[0] = availableSlot[0];
                       bestPosition[1] = availableSlot[1];
                   }
               }
           }
           return maxEval;
       }

       else {
           double minEval = Double.POSITIVE_INFINITY;
           int[][] availableSlots = getAvailableSlots(board);
           for (int[] availableSlot : availableSlots) {
               board[availableSlot[0]][availableSlot[1]] = opponent;
               double eval = miniMax(board, depth + 1, true);
               board[availableSlot[0]][availableSlot[1]] = 0;
               if (eval < minEval) {
                   minEval = eval;
               }
           }
           return minEval;
       }
    }
}
