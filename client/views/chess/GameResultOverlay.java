package views.chess;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class GameResultOverlay extends JPanel{

    private String message;

    public GameResultOverlay() {
        message = "";
    }

    public String getMessage() {
        return this.message;
    } 

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 25));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawString(message, getWidth()/3, getHeight()/3);
    }
}
