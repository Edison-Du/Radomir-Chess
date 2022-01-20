package views.chess;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.components.CustomButton;
import views.pages.AbstractGamePanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameResultOverlay extends JPanel implements ActionListener {

    public JLabel message;
    public final CustomButton playAgain;

    private AbstractGamePanel gamePanel;

    public GameResultOverlay(AbstractGamePanel gamePanel) {
        setBackground(new Color(0, 0, 0, 50));
        setOpaque(false);
        setLayout(null);

        this.gamePanel = gamePanel;

        // Message
        message = new JLabel("");
        message.setForeground(Color.WHITE);
        message.setBounds(0, 100, 360, 100);
        message.setHorizontalAlignment(JLabel.CENTER);
        this.add(message);

        // Play Again
        playAgain = new CustomButton("Play Again");
        playAgain.setBounds(150, 200, 150, 100);
        playAgain.addActionListener(this);
        this.add(playAgain);
    }




    // public String getMessage() {
    //     return this.message;
    // } 

    public void setMessage(String message) {
        this.message.setText(message);
        this.revalidate();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() == playAgain) {
            gamePanel.setPlayAgain(true);
            ServerConnection.sendMessage(new Message(MessageTypes.PLAY_AGAIN));
        }
    }
}
