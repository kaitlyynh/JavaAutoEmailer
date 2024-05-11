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
        int symbolSize = Math.min(getWidth(), getHeight()) / 3; // size of symbol 
        int padding = 5;
        int symbolX = getWidth() - symbolSize - padding; // place on the right side of the button
        int symbolY = (getHeight() - symbolSize) / 2; // center vertically

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
        // health care plus sign 
        g2d.setColor(Color.RED); 
        int thickness = size / 4;
        g2d.fillRect(x + (size - thickness) / 2, y, thickness, size); // vertical line 
        g2d.fillRect(x, y + (size - thickness) / 2, size, thickness); // horizontal line 
    }

    private void drawShield(Graphics2D g2d, int x, int y, int size) {
    // triangle (symbolizes a warning sign) (we tried to make a shield but the calculations were not working out) 
    g2d.setColor(Color.YELLOW); 
    int[] xPoints = {x + size / 2, x, x + size}; // top, left, right (triangle points) 
    int[] yPoints = {y, y + size, y + size};
    g2d.fillPolygon(xPoints, yPoints, 3);

    // a black exclamation mark in the center of the triangle
    g2d.setColor(Color.BLACK);
    int eWidth = size / 10;
    int eHeight = size / 3;
    int eX = x + size / 2 - eWidth / 2;
    int eY = y + size / 4;

    // the body of the exclamation mark
    g2d.fillRect(eX, eY, eWidth, eHeight);

    // the exclamation mark
    int dotSize = eWidth;
    int dotX = x + size / 2 - dotSize / 2;
    int dotY = y + (3 * size / 4);
    g2d.fillOval(dotX, dotY, dotSize, dotSize);
    
    }

    private void drawApple(Graphics2D g2d, int x, int y, int size) {
    g2d.setColor(Color.RED); // apple's body
    g2d.fillOval(x, y, size, size);

    // the brown stem
    g2d.setColor(new Color(139, 69, 19)); // dark brown color
    int stemWidth = size / 10;
    int stemHeight = size / 2;
    g2d.fillRect(x + size / 2 - stemWidth / 2, y - stemHeight / 2, stemWidth, stemHeight);

    // green leaf
    g2d.setColor(Color.GREEN.darker()); // a darker green for the leaf
    g2d.fillOval(x + size / 2 - size / 8, y - size / 6, size / 4, size / 4);
    
    }
}
