package views.pages;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import config.GraphicConsts;
import views.components.CustomButton;

public class PlayMenuButton extends CustomButton {
    public PlayMenuButton (String text, int x, int y) {
        super(text);
        this.setBounds(x, y, GraphicConsts.MENU_BUTTON_WIDTH, GraphicConsts.MENU_BUTTON_HEIGHT);

        this.setForeground(Color.WHITE);
        this.setBackground(GraphicConsts.MENU_BUTTON_COLOUR);
        this.setHoverColor(GraphicConsts.MENU_BUTTON_HIGHLIGHT);
        this.setPressedColor(GraphicConsts.MENU_BUTTON_HIGHLIGHT.brighter());

        this.setRound(true);
        this.setBorderRadius(GraphicConsts.MENU_BUTTON_RADIUS);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
