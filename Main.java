import java.awt.*;

public class Main {

    public static void main(String[] args) {
        MyGame game = new MyGame();

        MyBoard board = new MyBoard(game.getBoard());
        MyGUI gui = new MyGUI(board);
        new MyController(gui, game);
    }
}
