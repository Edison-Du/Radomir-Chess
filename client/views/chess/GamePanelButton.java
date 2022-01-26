package views.chess;

import javax.swing.BorderFactory;

import config.UserInterface;
import views.components.CustomButton;

/**
 * [GamePanelButton.java]
 * Button for the game panel
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class GamePanelButton extends CustomButton{
    public GamePanelButton(String text) {
        super(text);
        setBorder(BorderFactory.createCompoundBorder(
            UserInterface.GAME_CHAT_BORDER,
            UserInterface.FONT_OFFSET_BORDER));
        setFont(UserInterface.orkney14);
        setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        setHoverColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR);
        setPressedColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
        setBackground(UserInterface.NAVBAR_COLOUR);
    }
}