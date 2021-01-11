import java.util.*;

public class RandomAI extends AI {
    public RandomAI () {
        super();
    }

    /**
     * This method randomly chooses a position in the board.
     * @param board the given board configuration
     */
    public void algorithm (char[][] board) {
        // get all the available slots first
        int[][] available = super.getAvailableSlots(board);

        // generate a random number for the AI's next move
        Random random = new Random();
        int num = random.nextInt(available.length);

        // set bestPosition to the coordinates of the random position
        bestPosition[0] = available[num][0];
        bestPosition[1] = available[num][1];
    }
}