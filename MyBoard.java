import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class MyBoard extends JPanel {

    private final char[][] board;
    private boolean isEnd;
    private int[][] winningSection;
    private boolean isWinAnimation;
    private boolean isDrawAnimation;

    public MyBoard(char[][] board) {
        setPreferredSize(new Dimension(500, 500));
        //setBackground(Color.BLACK);
        setBackground(new Color(0, 0, 20));
        this.board = board;
        isEnd = false;
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        float thickness = 8f;
        g2.setStroke(new BasicStroke(thickness));
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        double width = getWidth();
        double height = getHeight();

        if (isDrawAnimation)
            g2.setColor(Color.GRAY);
        else
            g2.setColor(Color.WHITE);

        for(int i = 0; i < 2; i++) {
            g2.draw(new Line2D.Double(i + thickness, height / 3 * (i + 1), width - thickness, height / 3 * (i + 1))); //horizontal lines
            g2.draw(new Line2D.Double(width / 3 * (i + 1), i + thickness, width / 3 * (i + 1), height - thickness)); //horizontal lines
        }

        thickness = 20f;
        g2.setStroke(new BasicStroke(thickness));
        //int offset = 30;
        g2.setColor(Color.WHITE);
        double xSection = width / 3; //section of the board
        double ySection = height / 3;

        double offset = (thickness + 30); //upper left corner, x-coordinate
        //double yCorner = (thickness + offset) +  1 * ySection; //upper left corner, y-coordinate
        double rectWidth =  xSection - 2*(offset); //width of the rectangle
        double rectHeight = ySection - 2*(offset); //height of the rectangle

        if(isEnd)
            g2.setColor(Color.GRAY);
        else
            g2.setColor(Color.WHITE);

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(winningSection != null) {
                    if (isPartOfWinSection(i, j)) {
                        if (isWinAnimation)
                            g2.setColor(Color.GRAY);
                        else
                            g2.setColor(Color.WHITE);
                    }
                    else
                        g2.setColor(Color.GRAY);
                }

                //draw O or X
                if (board[i][j] == 'O')
                    g2.draw(new Ellipse2D.Double(offset + j * xSection, offset + i * ySection, rectWidth, rectHeight));
                else if (board[i][j] == 'X') {
                    Stroke stroke3 = new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                    g2.setStroke(stroke3);
                    g2.draw(new Line2D.Double(offset + j * xSection, offset + i * ySection, offset + j * xSection + rectWidth, offset + i * ySection + rectHeight));
                    g2.draw(new Line2D.Double(offset + j * xSection + rectWidth, offset + i * ySection, offset + j * xSection, offset + i * ySection + rectHeight));
                }
            }
        }
        repaint();
        repaint();
    }

    public void setWinSection(int[][] winSection) {
        winningSection = winSection;
    }

    public void isEndGame(boolean b) {
        isEnd = b;
    }
    public int toRowIndex(int posY) {
        return (posY / (getWidth() / 3));
    }

    public int toColIndex(int posX) {
        return (posX / (getHeight() / 3));
    }

    public boolean isPartOfWinSection(int row, int col) {

        for (int i = 0; i < winningSection.length; i++)
            if (winningSection[i][0] == row && winningSection[i][1] == col)
                return true;

        return false;
    }

    public void setIsWinAnimation(boolean b) {
        isWinAnimation = b;
    }

    public void setIsDrawAnimation(boolean b) {
        isDrawAnimation = b;
    }
}
