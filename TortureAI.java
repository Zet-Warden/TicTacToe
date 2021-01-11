import java.util.*;

public class TortureAI extends AI {

    private int turn;

    public TortureAI () {
        super();
        turn = 0;
    }

    /**
     * This method logically chooses a position in the board.
     * @param board the tic tac toe board
     */
    public void algorithm (char[][] board) {
        // get all the available slots first
        int[][] available = super.getAvailableSlots(board);

        int best = -1;

        // if AI is 2nd player
        if (ai == 'O' && turn < 4)  {

            turn = 1;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++)
                    if (board[i][j] != 0) {
                        turn++;
                    }
            }

            // 4th turn, opponent may have a possible trap
            if (turn == 4) {
                if ((board[0][0] == opponent && board[2][2] == opponent) ||
                        (board[2][0] == opponent && board[0][2] == opponent)) {
                    for (int i=0; i<available.length && best == -1; i++) {
                        if ((available[i][0] == 0 && available[i][1] == 1) ||
                                (available[i][0] == 2 && available[i][1] == 1))
                            best = i;
                    }
                }
            }
        }

        // check if AI can win in 1 move
        for (int i = 0; i<available.length && best == -1; i++) {
            board[available[i][0]][available[i][1]] = ai;

            if (hasWinner(board))
                if (ai == getWinner(board))
                    best = i;

            board[available[i][0]][available[i][1]] = 0;
        }

        // check if player can win in 1 move, if so, block their move
        for (int i = 0; i<available.length && best == -1; i++) {
            board[available[i][0]][available[i][1]] = opponent;

            if (hasWinner(board))
                if (opponent == getWinner(board))
                    best = i;

            board[available[i][0]][available[i][1]] = 0;
        }

        // if there is no winning/losing move for this turn
        if (best == -1) {
            // check if middle box is taken
            for (int i = 0; i<available.length; i++) {
                if (available[i][0] == 1 && available[i][1] == 1) {
                    best = i;
                    break;
                }
            }


            if (best == -1) {
                ArrayList<Integer> corners = new ArrayList<>();

                for (int i = 0; i<available.length; i++) {
                    if ((available[i][0] == 0 && available[i][1] == 0) || // top left corner
                            (available[i][0] == 0 && available[i][1] == 2) || // top right corner
                            (available[i][0] == 2 && available[i][1] == 0) || // bottom left corner
                            (available[i][0] == 2 && available[i][1] == 2)) // bottom right corner
                        corners.add(i);
                }

                // get a random corner if there is
                Random random = new Random();
                if (corners.size() != 0) {
                    int num = random.nextInt(corners.size());
                    best = corners.get(num);
                } else { // else, get a random spot
                    best = random.nextInt(available.length);
                }

            }
        }

        // set bestPosition
        bestPosition[0] = available[best][0];
        bestPosition[1] = available[best][1];
    }
}