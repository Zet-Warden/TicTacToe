import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MyGUI extends JFrame {

    private MyBoard board;
    private JPanel  mainPanel;

    private JLabel  humanLabel;
    private JLabel  computerLabel;
    private JLabel  tieLabel;

    private JLabel  humanScore;
    private JLabel  computerScore;
    private JLabel  tieScore;


    private Font montserrat;

    JButton soundButton;
    ImageIcon unmuteIcon;
    ImageIcon muteIcon;

    public MyGUI(MyBoard board) {
        super("Tic-Tac-Toe");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        //setExtendedState(MAXIMIZED_BOTH);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        setVisible(true);

        getContentPane().setBackground(new Color(0, 0, 20));
        try {
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("src\\MyFont.otf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        montserrat  = montserrat.deriveFont(Font.BOLD, 22);

        this.board = board;
        initScreen();

        repaint();
        revalidate();
    }

    public void initScreen() {
        GridBagConstraints gbc = new GridBagConstraints();
        humanLabel = new JLabel("PLAYER(X)");
        humanLabel.setFont(montserrat);
        humanLabel.setForeground(Color.WHITE);

        tieLabel = new JLabel("TIE");
        tieLabel.setFont(montserrat);
        tieLabel.setForeground(Color.WHITE);

        computerLabel = new JLabel("COMPUTER(O)");
        computerLabel.setFont(montserrat);
        computerLabel.setForeground(Color.WHITE);

        humanScore = new JLabel("0");
        humanScore.setFont(montserrat);
        humanScore.setForeground(Color.WHITE);

        tieScore = new JLabel("0");
        tieScore.setFont(montserrat);
        tieScore.setForeground(Color.WHITE);

        computerScore = new JLabel("0");
        computerScore.setFont(montserrat);
        computerScore.setForeground(Color.WHITE);

        //ImageIcon unmuteIcon = new MyImage("unmute.png");
        unmuteIcon = new MyImage(getClass().getResource("unmute.png"));
        soundButton = new JButton(unmuteIcon);

        muteIcon = new MyImage(getClass().getResource("mute.png"));
        //soundButton.setRolloverIcon(muteIcon);
        soundButton.setBorderPainted(false);
        soundButton.setContentAreaFilled(false);
        soundButton.setFocusPainted(false);
        soundButton.setOpaque(false);

        ImageIcon settingsIcon = new MyImage(getClass().getResource("settings.png"));
        JButton settingsButton = new JButton(settingsIcon);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);

        /*humanLabel.setBackground(Color.pink);
        computerLabel.setBackground(Color.pink);
        tieLabel.setBackground(Color.pink);
        humanLabel.setOpaque(true);
        tieLabel.setOpaque(true);
        computerLabel.setOpaque(true);*/

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(soundButton, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(settingsButton, gbc);

        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(board, gbc);


        //gbc.fill =GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(5, 40, 0, 20);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(humanLabel, gbc);

        gbc.gridy = 4;
        add(humanScore, gbc);

        gbc.insets = new Insets(5, 75, 0, 0);
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(tieLabel, gbc);

        gbc.gridy = 4;
        add(tieScore, gbc);

        gbc.insets = new Insets(5, 85, 0, 0);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(computerLabel, gbc);

        gbc.gridy = 4;
        add(computerScore, gbc);
    }

    public void setMouseListener(MouseListener listener) {
        board.addMouseListener(listener);
        soundButton.addMouseListener(listener);
    }

    public MyBoard getBoard() {
        return board;
    }

    public JLabel getHumanLabel() {
        return humanLabel;
    }

    public JLabel getTieLabel() {
        return tieLabel;
    }

    public JLabel getComputerLabel() {
        return  computerLabel;
    }

    public JLabel getHumanScore() {
        return humanScore;
    }

    public JLabel getTieScore() {
        return tieScore;
    }

    public JLabel getComputerScore() {
        return computerScore;
    }

    public JButton getSoundButton() {
        return soundButton;
    }

    public ImageIcon getUnmuteIcon() {
        return unmuteIcon;
    }

    public ImageIcon getMuteIcon() {
        return muteIcon;
    }
}
