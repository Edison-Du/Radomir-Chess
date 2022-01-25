package views.components;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * [CustomButton.java]
 * A customizable JButton
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class CustomButton extends JButton {

    private Color hoverColor, pressedColor;
    private boolean isRound;
    private int borderRadius;

    /**
     * CustomButton
     * Sets intitial button settings
     * @param text the text shown on the button
     */
    public CustomButton (String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusable(false);
        isRound = false;
    }

    /**
     * setHoverColor
     * Sets the colour of the button when hovered
     * @param color the hover colour
     */
    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    /**
     * getHoverColor
     * Gets the colour of the button when hovered
     * @return Color the current hover color
     */
    public Color getHoverColor() {
        return this.hoverColor;
    }

    /**
     * setPressedColor
     * Sets the colour of the button when pressed
     * @param color the pressed colour
     */
    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }

    /**
     * getPressedColor
     * Gets the colour of the button when pressed
     * @return the pressed colour
     */
    public Color getPressedColor() {
        return this.pressedColor;
    }

    /**
     * setRound
     * Sets whether or not the button is round
     * @param isRound whether or not the button is round
     */
    public void setRound(boolean isRound) {
        this.isRound = isRound;
    }

    /**
     * setBorderRadius
     * Sets the radius of the button's corners
     * @param radius sets the radius of the button's corners
     */
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
    }

    /**
     * paintComponent
     * Draws the custom button differently depending on if its hovered/pressed
     * as well as if it's rounded or not
     * @param g the graphics object to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        // Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Change appearance when hovered/pressed
        if (getModel().isPressed()) {
            g.setColor(this.pressedColor);
        } else if (getModel().isRollover()) {
            g.setColor(this.hoverColor);
        } else {
            g.setColor(getBackground());
        }

        // Draw rounded rectangle if specified
        if (isRound) {
            g.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        } else {
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}