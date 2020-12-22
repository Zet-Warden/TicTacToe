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
    private int musicCurrentFrame;
    private Clip moveFx;
    private Clip endSound;

    private final MiniMaxAI miniMaxAI = new MiniMaxAI();

    public MyController(MyGUI gui, MyGame game) {
        this.gui = gui;
        this.game = game;
        animationCtr = 0;
        setSound();
        music.loop(Clip.LOOP_CONTINUOUSLY);
        music.start();


        winAnimationTimer = new Timer(500, this);
        drawAnimationTimer = new Timer(500, this);
        aiNotify = new Timer(10, this);
        aiNotify.setRepeats(false);

        gui.setMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Starts the win/draw animation
        if(e.getSource() == winAnimationTimer || e.getSource() == drawAnimationTimer) {
            if(animationCtr <= 3) { //3 is the length of the music (delay(500ms) * 4(zero indexed) = 2000ms which is the length of the end music)
                if (e.getSource() == winAnimationTimer)
                    gui.getBoard().setIsWinAnimation(animationCtr % 2 == 0);
                else
                    gui.getBoard().setIsDrawAnimation(animationCtr % 2 == 0);
            }
            animationCtr++;
            //stops the animation timer
            if(animationCtr > 5) { //5 instead of 3 to add a short delay before music starts again
                winAnimationTimer.stop();
                drawAnimationTimer.stop();
                animationCtr = 0;
                music.start();
            }
        }

        //Notifies that it is Ais turn
        if(e.getSource() == aiNotify) {
            if(!game.hasWinner() && !game.isDraw()) {
                miniMaxAI.setMarker(game.getAI(), game.getHuman());
                char[][] boardCopy = new char[3][3];
                for(int i = 0; i < game.getBoard().length; i++)
                    for(int j = 0; j < game.getBoard()[i].length; j++)
                        boardCopy[i][j] = game.getBoard()[i][j];

                System.out.println("Evaluation: " + miniMaxAI.miniMax(boardCopy, 0, true));
                game.move(miniMaxAI.getBestPosition()[0], miniMaxAI.getBestPosition()[1]);
                checkWin();
                checkDraw();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Checks when player clicks on the board and applies action when it is his/her turn
        if(e.getSource() == gui.getBoard() && !game.hasWinner() && !game.isDraw() ) {
            if(game.getCurrentPlayer().equals("Human")) {
                loadMoveFx();
                moveFx.start();
                game.move(gui.getBoard().toRowIndex(e.getY()), gui.getBoard().toColIndex(e.getX()));
                checkWin();
                checkDraw();

                //notify ai after moving
                aiNotify.start();
            }
        }
        //Resets the board when clicked and the game is over
        else if(e.getSource() == gui.getBoard()) {
            reset();
        }

        //Checks if user wants to mute/unmute the background music
        if(e.getSource() == gui.getSoundButton()) {
            if(gui.getSoundButton().getIcon() == gui.getMuteIcon()) { //unmute music
                gui.getSoundButton().setIcon(gui.getUnmuteIcon());
                BooleanControl muteControl = (BooleanControl) music.getControl(BooleanControl.Type.MUTE);
                muteControl.setValue(false);
                music.setFramePosition(musicCurrentFrame);
                music.start();
            }
            else { //mute music
                gui.getSoundButton().setIcon(gui.getMuteIcon());
                BooleanControl muteControl = (BooleanControl) music.getControl(BooleanControl.Type.MUTE);
                muteControl.setValue(true);
                music.setFramePosition(music.getFramePosition() + 1000);
                musicCurrentFrame = music.getFramePosition() + 1000;
                music.stop();
            }
        }
    }

    /**
     * Changes win score when game ends in a win
     * Applies appropriate gui actions such as starting animation and music
     */
    public void checkWin() {
        int score;
        if(game.hasWinner()) {
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

    /**
     * Changes tie score when game ends in a draw
     * Applies appropriate gui actions such as starting animation and music
     */
    public void checkDraw() {
        int score;
        if(!game.hasWinner() && game.isDraw()) {
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

    /**
     * Resets the board and other necessary components such as animationCtr, winSection
     * Tells AI if it is its turn
     * Changes marker labels for Player and Computer
     * Applies appropriate gui actions such as stopping animation and end music; starts background music
     */
    public void reset() {
        game.resetBoard();
        if(game.getCurrentPlayer().equals("AI"))
            aiNotify.start();

        gui.getHumanLabel().setText("PLAYER(" + game.getHuman() + ")");
        gui.getComputerLabel().setText("COMPUTER(" + game.getAI() + ")");
        gui.getBoard().setWinSection(null);
        winAnimationTimer.stop();
        drawAnimationTimer.stop();
        gui.getBoard().setIsWinAnimation(false);
        gui.getBoard().setIsDrawAnimation(false);
        endSound.stop();
        music.start();

        animationCtr = 0;
    }


    /**
     * Loads the sound when a marker is placed on the board
     */
    public void loadMoveFx() {
        String moveFxName ="src\\bloop.wav"; //sound when a marker is placed on the board

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(moveFxName).getAbsoluteFile());
            moveFx = AudioSystem.getClip();
            moveFx.open(audioInputStream);
            FloatControl gainControl = (FloatControl) moveFx.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-8.0f); // Reduce volume by 10 decibels.
        } catch(Exception e) {
            System.out.println("Music file for MoveFX: Not Detected");
        }
    }

    /**
     * Sets the sound for game over music as well as background music
     */
    public void setSound() {
        String endSoundName = "src\\end.wav"; //ending sound whether win/lose
        String musicName = "src\\music.wav"; //background music

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(endSoundName).getAbsoluteFile());
            endSound = AudioSystem.getClip();
            endSound.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File(musicName).getAbsoluteFile());
            music = AudioSystem.getClip();
            music.open(audioInputStream);
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-8.0f); // Reduce volume by 10 decibels.
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
