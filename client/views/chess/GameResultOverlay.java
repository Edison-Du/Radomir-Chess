package views.chess;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Graphics;
import java.awt.Color;

public class GameResultOverlay extends JPanel{

    // private Color backgroundColor = new Color(0, 0, 0, 0);

    private JLabel message;

    public GameResultOverlay() {
        setBackground(new Color(0, 0, 0, 50));
        setOpaque(false);
        setLayout(null);

        message = new JLabel("");
        message.setForeground(Color.WHITE);
        message.setBounds(0, 100, 360, 100);
        message.setHorizontalAlignment(JLabel.CENTER);
        this.add(message);
    }

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // public void setVisible(boolean visible) {
    //     if (visible) {
    //         setBackground(new Color(0, 0, 0, 50));
    //     } else {
    //         setBackground(new Color(0, 0, 0, 0));
    //     }
    //     revalidate();
    // }

    // public String getMessage() {
    //     return this.message;
    // } 

    public void setMessage(String message) {
        this.message.setText(message);
        this.revalidate();
    }
}
