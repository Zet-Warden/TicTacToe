import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MyController implements MouseListener, ActionListener {

    private final MyGUI gui;
    private final MyGame game;
    private final Timer winAnimationTimer;
    private final Timer drawAnimationTimer;
    private final Timer aiNotify;
    private int   animationCtr;

    private Clip music;
    private Clip moveFx;
    private Clip endSound;
    private Clip noSound;


    public MyController(MyGUI gui, MyGame game) {
        this.gui = gui;
        this.game = game;
        animationCtr = 0;
        setSound();
        noSound.start();
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
        winAnimationTimer = new Timer(500, this);
        drawAnimationTimer = new Timer(500, this);
        aiNotify = new Timer(100, this);
        gui.setMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == winAnimationTimer || e.getSource() == drawAnimationTimer) {
            if(animationCtr <= 3) {
                if (e.getSource() == winAnimationTimer)
                    gui.getBoard().setIsWinAnimation(animationCtr % 2 == 0);
                else
                    gui.getBoard().setIsDrawAnimation(animationCtr % 2 == 0);
            }
            animationCtr++;
            if(animationCtr > 5) {
                winAnimationTimer.stop();
                drawAnimationTimer.stop();
                animationCtr = 0;

                /*long start = System.currentTimeMillis();
                long end = 0;
                while(end - start != 300) {
                    end = System.currentTimeMillis();
                }*/
                music.start();

            }
        }

        if(e.getSource() == aiNotify) {
            if(game.getCurrentPlayer().equals("AI") && !game.hasWinner() && !game.isDraw()) {
                MiniMaxAI ai = new MiniMaxAI(game.getAI());

                char[][] boardCopy = new char[3][3];
                for(int i = 0; i < game.getBoard().length; i++)
                    for(int j = 0; j < game.getBoard()[i].length; j++)
                        boardCopy[i][j] = game.getBoard()[i][j];

                System.out.println("Evaluation: " + ai.miniMax(boardCopy, 0, true));
                /*loadMoveFx();
                moveFx.start();*/
                game.move(ai.getBestPosition()[0], ai.getBestPosition()[1]);
                checkWin();
                checkDraw();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == gui.getBoard() && !game.hasWinner() && !game.isDraw() ) {
            if(game.getCurrentPlayer().equals("Human")) {
                loadMoveFx();
                //moveFx.setMicrosecondPosition(10000);
                moveFx.start();
                game.move(gui.getBoard().toRowIndex(e.getY()), gui.getBoard().toColIndex(e.getX()));
                checkWin();
                checkDraw();

                //might need to check first if game is already finished
                aiNotify.start();
            }
        }
        else if(e.getSource() == gui.getBoard()) {
            reset();
        }

        if(e.getSource() == gui.getSoundButton()) {
            if(gui.getSoundButton().getIcon() == gui.getMuteIcon()) {
                gui.getSoundButton().setIcon(gui.getUnmuteIcon());
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src\\music.wav").getAbsoluteFile());
                    music = AudioSystem.getClip();
                    music.open(audioInputStream);
                } catch (Exception ex) {
                    System.out.println("Music file for Game: Not Detected");
                }
                music.start();

            }
            else {
                gui.getSoundButton().setIcon(gui.getMuteIcon());
                music.close();
            }
        }
    }

    public void checkWin() {
        int score;
        if(game.hasWinner()) {
            gui.getBoard().isEndGame(true);
            if(game.getHuman() == game.getBoard()[game.getWinner()[0][0]][game.getWinner()[0][1]]) {
                score = Integer.parseInt(gui.getHumanScore().getText());
                score++;
                gui.getHumanScore().setText(score + "");
            }
            else if(game.getAI() == game.getBoard()[game.getWinner()[0][0]][game.getWinner()[0][1]]) {
                score = Integer.parseInt(gui.getComputerScore().getText());
                score++;
                gui.getComputerScore().setText(score + "");
            }
            gui.getBoard().setWinSection(game.getWinner());
            winAnimationTimer.start();
            endSound.stop();
            endSound.setFramePosition(0);
            endSound.start();
            music.stop();
        }
    }

    public void checkDraw() {
        int score;
        if(!game.hasWinner() && game.isDraw()) {
            gui.getBoard().isEndGame(true);
            score = Integer.parseInt(gui.getTieScore().getText());
            score++;
            gui.getTieScore().setText(score + "");
            drawAnimationTimer.start();
            endSound.stop();
            endSound.setFramePosition(0);
            endSound.start();
            music.stop();
        }
    }

    public void reset() {
        game.resetBoard();
        gui.getHumanLabel().setText("PLAYER(" + game.getHuman() + ")");
        gui.getComputerLabel().setText("COMPUTER(" + game.getAI() + ")");
        gui.getBoard().isEndGame(false);
        gui.getBoard().setWinSection(null);
        winAnimationTimer.stop();
        drawAnimationTimer.stop();
        gui.getBoard().setIsWinAnimation(false);
        gui.getBoard().setIsDrawAnimation(false);
        endSound.stop();
        music.start();

        animationCtr = 0;
    }


    public void loadMoveFx() {
        String moveFxName ="src\\bloop.wav"; //sound when a marker is placed on the board

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(moveFxName).getAbsoluteFile());
            moveFx = AudioSystem.getClip();
            moveFx.open(audioInputStream);
        } catch(Exception e) {
            System.out.println("Music file for MoveFX: Not Detected");
        }
    }

    public void setSound() {
        //String moveFxName ="src\\bloop.wav"; //sound when a marker is placed on the board
        String noSoundName = "src\\noSound.wav";
        String endSoundName = "src\\end.wav"; //ending sound whether win/lose
        String musicName = "src\\music.wav";
        try {
            /*AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(moveFxName).getAbsoluteFile());
            moveFx = AudioSystem.getClip();
            moveFx.open(audioInputStream);*/

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(endSoundName).getAbsoluteFile());
            endSound = AudioSystem.getClip();
            endSound.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File(noSoundName).getAbsoluteFile());
            noSound = AudioSystem.getClip();
            noSound.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File(musicName).getAbsoluteFile());
            music = AudioSystem.getClip();
            music.open(audioInputStream);
            //music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Music file for Game: Not Detected");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
