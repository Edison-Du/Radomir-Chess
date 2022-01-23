package views.pages;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import config.UserInterface;
import views.components.CustomButton;

public class PanelButton extends CustomButton {
    public PanelButton (String text, int x, int y) {
        super(text);
        //change
        this.setBounds(x, y, 300, UserInterface.MENU_BUTTON_HEIGHT);

        this.setForeground(Color.WHITE);
        this.setBackground(UserInterface.MENU_BUTTON_COLOUR);
        this.setHoverColor(UserInterface.MENU_BUTTON_HIGHLIGHT);
        this.setPressedColor(UserInterface.MENU_BUTTON_HIGHLIGHT.brighter());

        this.setRound(true);
        this.setBorderRadius(UserInterface.MENU_BUTTON_RADIUS);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
