package views.components;

import java.awt.Color;
import config.UserInterface;

public class PanelButton extends CustomButton {

    public PanelButton (String text, int x, int y) {
        super(text);
        this.setBounds(x, y, UserInterface.PANEL_BUTTON_WIDTH, UserInterface.PANEL_BUTTON_HEIGHT);
        this.setFont(UserInterface.orkney24);
        this.setForeground(Color.WHITE);
        this.setBackground(UserInterface.MENU_BUTTON_COLOUR);
        this.setHoverColor(UserInterface.MENU_BUTTON_HIGHLIGHT);
        this.setPressedColor(UserInterface.MENU_BUTTON_HIGHLIGHT.brighter());
        this.setRound(true);
        this.setBorderRadius(UserInterface.MENU_BUTTON_RADIUS);
        this.setBorder(UserInterface.PANEL_BUTTON_BORDER);
    }
}
