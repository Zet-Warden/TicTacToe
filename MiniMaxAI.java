public class MiniMaxAI {

    private int[] bestPosition;
    private char ai;
    private char opponent;

    public MiniMaxAI(char marker) {
        bestPosition = new int[2];

        ai = marker;
        if(ai == 'X')
            opponent = 'O';
        else
            opponent = 'X';
    }

    public double miniMax(char[][] board, int depth, boolean isMaximizing) {
        if(hasWinner(board)) {
            if(ai == getWinner(board)) {
                //System.out.println("win");
                return 1;
            }
            else if (opponent == getWinner(board)) {
                //System.out.println("lose");
                return -1;
            }
        }
        else if (isDraw(board)) {
            //System.out.println("draw");
            return 0;
        }

       if(isMaximizing) {
           double maxEval = Double.NEGATIVE_INFINITY;
           int[][] availableSlots = getAvailableSlots(board);
           for(int i = 0; i < availableSlots.length; i++) {
               board[availableSlots[i][0]][availableSlots[i][1]] = ai;
               double eval = miniMax(board, depth + 1, false);
               board[availableSlots[i][0]][availableSlots[i][1]] = 0;
               if(eval > maxEval) {
                   maxEval = eval;
                   if(depth == 0) {
                       bestPosition[0] = availableSlots[i][0];
                       bestPosition[1] = availableSlots[i][1];
                   }
               }
           }
           return maxEval;
       }

       else {
           double minEval = Double.POSITIVE_INFINITY;
           int[][] availableSlots = getAvailableSlots(board);
           for(int i = 0; i < availableSlots.length; i++) {
               board[availableSlots[i][0]][availableSlots[i][1]] = opponent;
               double eval = miniMax(board, depth + 1, true);
               board[availableSlots[i][0]][availableSlots[i][1]] = 0;
               if(eval < minEval) {
                   minEval = eval;
               }
           }
           return minEval;
       }


    }

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
        for (int i = 0; i < availableCtr; i++) {
            available[i][0] = temp[i][0];
            available[i][1] = temp[i][1];
            //System.arraycopy(temp[i], 0, available[i], 0, 2);
        }
        return available;
    }

    public boolean hasWinner(char[][] board) {
        return getWinner(board) != 0;
    }

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

    public boolean isDraw(char[][] board) {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public int[] getBestPosition() {
        return  bestPosition;
    }
}
