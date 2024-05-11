package emailer;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kaitlynhuynh
 */
public class GraphicsButton extends JButton {
    private String symbol;

    public GraphicsButton(String text, String symbol) {
        super();
        this.symbol = symbol;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSymbol(g, symbol);
    }

    private void drawSymbol(Graphics g, String symbolType) {
        Graphics2D g2d = (Graphics2D) g;
        int symbolSize = Math.min(getWidth(), getHeight()) / 3;
        int padding = 5;
        int symbolX = getWidth() - symbolSize - padding; // Draw on the right side of the button
        int symbolY = (getHeight() - symbolSize) / 2; // Center vertically

        switch (symbolType) {
            case "Healthcare":
                drawPlusSign(g2d, symbolX, symbolY, symbolSize);
                break;
            case "Security":
                drawShield(g2d, symbolX, symbolY, symbolSize);
                break;
            case "Education":
                drawApple(g2d, symbolX, symbolY, symbolSize);
                break;
        }
    }

    private void drawPlusSign(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(Color.RED);
        int thickness = size / 4;
        g2d.fillRect(x + (size - thickness) / 2, y, thickness, size); // Vertical bar
        g2d.fillRect(x, y + (size - thickness) / 2, size, thickness); // Horizontal bar
    }

    private void drawShield(Graphics2D g2d, int x, int y, int size) {
    // Draw the yellow shield body
    g2d.setColor(Color.YELLOW);
    int[] xPoints = {x + size / 2, x, x + size};
    int[] yPoints = {y, y + size, y + size};
    g2d.fillPolygon(xPoints, yPoints, 3);

    // Draw a black exclamation mark in the center of the shield
    g2d.setColor(Color.BLACK);
    int exclamationWidth = size / 10;
    int exclamationHeight = size / 3;
    int exclamationX = x + size / 2 - exclamationWidth / 2;
    int exclamationY = y + size / 4;

    // Draw the body of the exclamation mark
    g2d.fillRect(exclamationX, exclamationY, exclamationWidth, exclamationHeight);

    // Draw the dot of the exclamation mark
    int dotSize = exclamationWidth;
    int dotX = x + size / 2 - dotSize / 2;
    int dotY = y + (3 * size / 4);
    g2d.fillOval(dotX, dotY, dotSize, dotSize);
    
    }

    private void drawApple(Graphics2D g2d, int x, int y, int size) {
    // Draw the red apple body
    g2d.setColor(Color.RED);
    g2d.fillOval(x, y, size, size);

    // Draw the brown stem
    g2d.setColor(new Color(139, 69, 19)); // A dark brown color
    int stemWidth = size / 10;
    int stemHeight = size / 3;
    g2d.fillRect(x + size / 2 - stemWidth / 2, y - stemHeight / 2, stemWidth, stemHeight);

    // Draw a bigger green leaf
    g2d.setColor(Color.GREEN.darker()); // Use a darker green for the leaf
    g2d.fillOval(x + size / 2 - size / 8, y - size / 6, size / 4, size / 4);
    
    }
}