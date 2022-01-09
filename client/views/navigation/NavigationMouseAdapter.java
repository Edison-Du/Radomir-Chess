package views.navigation;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import config.Consts;

public class NavigationMouseAdapter extends MouseAdapter {

    private JButton button;

    public NavigationMouseAdapter(JButton button) {
        this.button = button;
    }

    public void mouseEntered(MouseEvent e) {
        button.setBackground(Consts.BUTTON_HOVER_COLOUR);
        button.setMargin(new Insets(0, 0, 0, 20));
    }

    public void mouseExited(MouseEvent e) {
        button.setBackground(Consts.NAVBAR_COLOUR);
    }

    public void mousePressed(MouseEvent e) {
        button.setBackground(Consts.BUTTON_HOVER_COLOUR);
    }
}
