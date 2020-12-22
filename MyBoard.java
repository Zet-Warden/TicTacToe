import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class MyBoard extends JPanel {

    private final char[][] board;
    private int[][] winningSection;
    private boolean isWinAnimation;
    private boolean isDrawAnimation;

    public MyBoard(char[][] board) {
        setPreferredSize(new Dimension(500, 500));
        setBackground(new Color(0, 0, 20));
        this.board = board;
    }

    /**
     * Paints the board as well as it the markers
     * @param g graphics
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        float thickness = 8f; //thickness of line border
        g2.setStroke(new BasicStroke(thickness));
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        double width = getWidth();
        double height = getHeight();

        if(isDrawAnimation)
            g2.setColor(Color.GRAY);
        else
            g2.setColor(Color.WHITE);

        for(int i = 0; i < 2; i++) {
            g2.draw(new Line2D.Double(i + thickness, height / 3 * (i + 1), width - thickness, height / 3 * (i + 1))); //horizontal lines
            g2.draw(new Line2D.Double(width / 3 * (i + 1), i + thickness, width / 3 * (i + 1), height - thickness)); //vertical lines
        }

        thickness = 20f; //thickness of the markers
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.WHITE);

        //sections of the board
        double xSection = width / 3;
        double ySection = height / 3;

        double offset = (thickness + 30); //area offset
        double rectWidth =  xSection - 2*(offset); //width of the rectangle
        double rectHeight = ySection - 2*(offset); //height of the rectangle

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
        revalidate();
    }

    /**
     * Set winningSection to determine which row/column/diagonal is the winner
     * @param winSection the winning section
     */
    public void setWinSection(int[][] winSection) {
        winningSection = winSection;
    }

    /**
     * Checks if the given row and col is part of the winning section
     * @param row row index
     * @param col column index
     * @return true/false indicating if row and col is part of the winning section
     */
    public boolean isPartOfWinSection(int row, int col) {
        if(winningSection != null) {
            for (int[] ints : winningSection)
                if (ints[0] == row && ints[1] == col)
                    return true;
        }

        return false;
    }

    /**
     * Sets if win animation should be applied
     * @param b true/false indicating if animation should be applied
     */
    public void setIsWinAnimation(boolean b) {
        isWinAnimation = b;
    }

    /**
     * Sets if win animation should be applied
     * @param b true/false indicating if animation should be applied
     */
    public void setIsDrawAnimation(boolean b) {
        isDrawAnimation = b;
    }

    /**
     * Convers Y-mouse position to Row
     * @param posY Y-coordinate of mouse position
     * @return the resulting row index
     */
    public int toRowIndex(int posY) {
        return (posY / (getWidth() / 3));
    }

    /**
     * Convers X-mouse position to Row
     * @param posX X-coordinate of mouse position
     * @return the resulting column index
     */
    public int toColIndex(int posX) {
        return (posX / (getHeight() / 3));
    }
}
