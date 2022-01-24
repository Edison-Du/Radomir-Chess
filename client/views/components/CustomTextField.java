package views.components;

import javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

public class CustomTextField extends JTextField {
    
    private String placeholder;
    private Color placeholderColour;
    private int placeholderY;
    // private

    public CustomTextField() {
        this.placeholder = "";   
    }

    public CustomTextField(String placeholder) {
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
        if (placeholder.length() == 0 || getText().length() > 0) {
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