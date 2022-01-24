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
import java.awt.Rectangle;

public class GameResultOverlay extends JPanel implements ActionListener {

    private final Rectangle GAME_MESSAGE_BOUNDS = new Rectangle(45, 170, 390, 60);
    private final Rectangle PLAY_AGAIN_BOUNDS = new Rectangle(45, 230, 390, 60);

    private final int OVERLAY_X = 45;
    private final int OVERLAY_Y = 170;
    private final int OVERLAY_WIDTH = 390;
    private final int OVERLAY_HEIGHT = 120;
    private final int OVERLAY_ARC = 20;
    private final Color OVERLAY_COLOUR = new Color(0, 0, 0, 127);


    public JLabel message;
    public final CustomButton playAgain;

    private AbstractGamePanel gamePanel;

    public GameResultOverlay(AbstractGamePanel gamePanel) {
        setOpaque(false);
        setLayout(null);

        this.gamePanel = gamePanel;

        // Message
        message = new JLabel("", SwingConstants.CENTER);
        message.setForeground(Color.WHITE);
        message.setFont(UserInterface.orkney30);
        message.setBounds(GAME_MESSAGE_BOUNDS);
        message.setHorizontalAlignment(JLabel.CENTER);
        this.add(message);

        // Play Again
        playAgain = new CustomButton("");
        playAgain.setBounds(PLAY_AGAIN_BOUNDS);
        playAgain.setFont(UserInterface.orkney18);
        playAgain.setBackground(UserInterface.TRANSPARENT);
        playAgain.setHoverColor(UserInterface.TRANSPARENT);
        playAgain.setPressedColor(UserInterface.TRANSPARENT);
        playAgain.setBorder(UserInterface.EMPTY_BORDER);
        playAgain.setForeground(Color.WHITE);
        playAgain.addActionListener(this);
        this.add(playAgain);
    }

    public void setMessage(String message) {
        this.message.setText(message);
        this.revalidate();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(OVERLAY_COLOUR);
        g2d.fillRoundRect(OVERLAY_X, OVERLAY_Y, OVERLAY_WIDTH, OVERLAY_HEIGHT, OVERLAY_ARC, OVERLAY_ARC);

        if (gamePanel.isPlayingAgain()) {
            playAgain.setText("Waiting for Opponent");
            
        } else {
            playAgain.setText("Play Again");

            if (playAgain.getModel().isRollover() || playAgain.getModel().isPressed()) {
                playAgain.setFont(UserInterface.orkney24);
            } else {
                playAgain.setFont(UserInterface.orkney18);
            }
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