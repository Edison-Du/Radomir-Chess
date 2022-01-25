package views.components;

import javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

/**
 * [CustomTextField.java]
 * A customizable text field
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class CustomTextField extends JTextField {
    
    // Placeholder is text that appears if the user has not typed anything
    private String placeholder;
    private Color placeholderColour;
    private int placeholderY;

    /**
     * CustomTextField
     * Creates a text field with no placeholder
     */
    public CustomTextField() {
        this.placeholder = "";   
    }

    /**
     * CustomTextField
     * Creates a text field with a placeholder
     * @param placeholder
     */
    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * setPlaceholder
     * Sets the placeholder text to display
     * @param placeholder the placeholder text
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * getPlaceholder
     * Gets the placeholder text
     * @return the placeholder text
     */
    public String getPlaceholder() {
        return this.placeholder;
    }

    /**
     * setPlaceholderColour
     * Sets the colour of the placeholder text
     * @param colour the colour of the placeholder text
     */
    public void setPlaceholderColour(Color colour) {
        placeholderColour = colour;
    }

    /**
     * setPlaceholderY
     * Sets the y position of the placeholder text
     * @param y the y position of the placeholder text
     */
    public void setPlaceholderY(int y) {
        this.placeholderY = y;
    }

    /**
     * paintComponent
     * Draws placeholder text onto the text field
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if ( (placeholder.length() != 0) && (getText().length() == 0) ) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
            g2d.setColor(placeholderColour);
            g2d.drawString(placeholder, getInsets().left, placeholderY);
        }
    }
}