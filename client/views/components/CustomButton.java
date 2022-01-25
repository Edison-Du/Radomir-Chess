package views.components;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * [CustomButton.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class CustomButton extends JButton {

    private Color hoverColor, pressedColor;
    private boolean isRound;
    private int borderRadius;

    // Custom button constructor
    public CustomButton (String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusable(false);
        isRound = false;
    }

    /**
     * setHoverColor
     * @param color colour to set hover colour to
     */
    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    /**
     * getHoverColor
     * @return Color the current hover color
     */
    public Color getHoverColor() {
        return this.hoverColor;
    }

    /**
     * setPressedColor
     * @param color set the pressed color
     */
    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }

    /**
     * getPressedColor
     * @return Color the current pressed color
     */
    public Color getPressedColor() {
        return this.pressedColor;
    }

    /**
     * setRound
     * @param isRound sets the isRound boolean
     */
    public void setRound(boolean isRound) {
        this.isRound = isRound;
    }

    /**
     * setBorderRadius
     * @param radius sets the radius of the border
     */
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );

        // Change appearance when hovered/pressed
        if (getModel().isPressed()) {
            g.setColor(this.pressedColor);
        } else if (getModel().isRollover()) {
            g.setColor(this.hoverColor);
        } else {
            g.setColor(getBackground());
        }

        if (isRound) {
            g.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        } else {
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}