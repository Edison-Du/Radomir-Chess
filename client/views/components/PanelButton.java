package views.components;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import config.UserInterface;

public class PanelButton extends CustomButton {
    private final int BUTTON_WIDTH = 280;
    private final EmptyBorder BUTTON_MARGIN = new EmptyBorder(10, 0, 0, 0);

    public PanelButton (String text, int x, int y) {
        super(text);
        //change
        this.setBounds(x, y, BUTTON_WIDTH, UserInterface.MENU_BUTTON_HEIGHT / 2);
        this.setFont(UserInterface.orkney24);

        this.setForeground(Color.WHITE);
        this.setBackground(UserInterface.MENU_BUTTON_COLOUR);
        this.setHoverColor(UserInterface.MENU_BUTTON_HIGHLIGHT);
        this.setPressedColor(UserInterface.MENU_BUTTON_HIGHLIGHT.brighter());

        this.setRound(true);
        this.setBorderRadius(UserInterface.MENU_BUTTON_RADIUS);
        this.setBorder(BUTTON_MARGIN);
    }
}
