package views.chess;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import config.GameState;
import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.CustomButton;
import views.pages.AbstractGamePanel;
import views.pages.MultiplayerPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics2D;

public class GameResultOverlay extends JPanel implements ActionListener {

    public JLabel message;
    public final CustomButton playAgain;

    private AbstractGamePanel gamePanel;

    public GameResultOverlay(AbstractGamePanel gamePanel) {
        // setBackground(new Color(0, 0, 0, 50));
        setOpaque(false);
        setLayout(null);

        this.gamePanel = gamePanel;

        // Message
        message = new JLabel("", SwingConstants.CENTER);
        message.setForeground(Color.WHITE);
        message.setBounds(45, 170, 390, 60);
        message.setHorizontalAlignment(JLabel.CENTER);
        this.add(message);

        // Play Again
        playAgain = new CustomButton("Play Again");
        playAgain.setBounds(45, 230, 390, 60);
        playAgain.setBackground(new Color(0, 0, 0, 0));
        playAgain.setHoverColor(new Color(0, 0, 0, 0));
        playAgain.setPressedColor(new Color(0, 0, 0, 0));
        playAgain.setBorder(UserInterface.EMPTY_BORDER);
        playAgain.setForeground(Color.WHITE);
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
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0, 127));
        g2d.fillRoundRect(45, 170, 390, 120, 20, 20);

        if (gamePanel.isPlayingAgain()) {
            playAgain.setText("Waiting for Opponent");
        } else {
            playAgain.setText("Play Again");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() == playAgain) {
            gamePanel.setPlayAgain(true);

            if (gamePanel instanceof MultiplayerPanel) {
                if (((MultiplayerPanel) gamePanel).isAlone()) {
                    gamePanel.setGameState(GameState.WAITING);
                    gamePanel.resetGame();
                    return;
                }
                ServerConnection.sendMessage(new Message(MessageTypes.PLAY_AGAIN));
            } 
        }
    }
}