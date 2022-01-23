package views.chess;

import config.UserInterface;
import views.components.CustomButton;

public class GamePanelButton extends CustomButton{
    public GamePanelButton(String text) {
        super(text);
        this.setBorder(UserInterface.GAME_CHAT_BORDER);
        this.setBackground(UserInterface.NAVBAR_COLOUR);
    }
}