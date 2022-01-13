package views.components;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

public class CustomButton extends JButton {

    private Color hoverColor, pressedColor;
    private boolean isRound;

    public CustomButton (String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusable(false);
        isRound = false;
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public Color getHoverColor() {
        return this.hoverColor;
    }

    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }

    public Color getPressedColor() {
        return this.pressedColor;
    }

    public void setRound(boolean isRound) {
        this.isRound = isRound;
    }

    @Override
    protected void paintComponent(Graphics g) {

        // Change appearance when hovered/pressed
        if (getModel().isPressed()) {
            g.setColor(this.pressedColor);

        } else if (getModel().isRollover()) {
            g.setColor(this.hoverColor);

        } else {
            g.setColor(getBackground());
        }
        if (isRound) {
            // use constants
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        } else {
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

            // g.fillRect(0, 0, getWidth(), getHeight());

        }
        super.paintComponent(g);
    }
}
