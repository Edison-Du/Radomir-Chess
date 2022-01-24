package views.components;

import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CustomPasswordField extends JPasswordField{
    private String placeholder;
    private Color placeholderColour;
    private int placeholderY;
    // private

    public CustomPasswordField() {
        this.placeholder = "";   
    }

    public CustomPasswordField(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholderColour(Color colour) {
        placeholderColour = colour;
    }

    public void setPlaceholderY(int y) {
        this.placeholderY = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (placeholder.length() == 0 || getPassword().length > 0) {
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(placeholderColour);
        g2d.drawString(
            placeholder, 
            getInsets().left, 
            placeholderY);
    }
}
