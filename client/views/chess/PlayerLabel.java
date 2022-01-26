package views.chess;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import config.UserInterface;

/**
 * [PlayerLabel.java]
 * Labels that display player names on the game page 
 *
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class PlayerLabel extends JLabel {

    /**
     * PlayerLabel
     * Sets the panel settings
     */
    public PlayerLabel() {
        setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        setBackground(UserInterface.FRAME_COLOUR);
        setFont(UserInterface.orkney30);
        setHorizontalAlignment(SwingConstants.LEFT);
    }
}