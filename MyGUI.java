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

    private JButton settingsButton;
    private JButton soundButton;
    private ImageIcon unmuteIcon;
    private ImageIcon muteIcon;

    private JPanel optionPanel;
    private JRadioButton randomAI;
    private JRadioButton tortureAI;
    private JRadioButton minimaxAI;
    private ButtonGroup buttonGroup;

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
            System.out.println("Font File Not Detected");
        }
        montserrat  = montserrat.deriveFont(Font.BOLD, 22);
        this.board = board;
        initScreen();
        optionDialog();

        repaint();
        revalidate();
    }

    public void initScreen() {
        GridBagConstraints gbc = new GridBagConstraints();
        humanLabel = new JLabel("PLAYER(X)");
        humanLabel.setFont(montserrat);
        humanLabel.setForeground(Color.WHITE);
        humanLabel.setPreferredSize(new Dimension(175, 22));
        humanLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tieLabel = new JLabel("TIE");
        tieLabel.setFont(montserrat);
        tieLabel.setForeground(Color.WHITE);
        tieLabel.setPreferredSize(new Dimension(175, 22));
        tieLabel.setHorizontalAlignment(SwingConstants.CENTER);

        computerLabel = new JLabel("COMPUTER(O)");
        computerLabel.setFont(montserrat);
        computerLabel.setForeground(Color.WHITE);
        computerLabel.setPreferredSize(new Dimension(175, 22));
        computerLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
        settingsButton = new JButton(settingsIcon);
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

        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(board, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(soundButton, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(settingsButton, gbc);

        //gbc.fill =GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.PAGE_START;
        //gbc.insets = new Insets(5, 40, 0, 20);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(humanLabel, gbc);

        gbc.gridy = 4;
        add(humanScore, gbc);

        //gbc.insets = new Insets(5, 75, 0, 0);
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(tieLabel, gbc);

        gbc.gridy = 4;
        add(tieScore, gbc);

        //gbc.insets = new Insets(5, 85, 0, 0);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(computerLabel, gbc);

        gbc.gridy = 4;
        add(computerScore, gbc);
    }

    public void optionDialog() {
        optionPanel = new JPanel();

        randomAI = new JRadioButton("Level 0 AI");
        tortureAI = new JRadioButton("Level 1 AI");
        minimaxAI = new JRadioButton("Level 2 AI");

        randomAI.setSelected(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(randomAI);
        buttonGroup.add(tortureAI);
        buttonGroup.add(minimaxAI);

        optionPanel.add(randomAI);
        optionPanel.add(tortureAI);
        optionPanel.add(minimaxAI);
    }

    public void showOptionDialog() {
        JOptionPane.showMessageDialog(null, optionPanel, "Choose your AI", JOptionPane.PLAIN_MESSAGE);
    }

    public void setActionListener(ActionListener listener) {
        settingsButton.addActionListener(listener);
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

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public JRadioButton getRandomAI() {
        return randomAI;
    }

    public JRadioButton getTortureAI() {
        return tortureAI;
    }

    public JRadioButton getMinimaxAI() {
        return minimaxAI;
    }
}
