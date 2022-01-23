package views.navigation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import config.UserInterface;
import config.Page;

public class NavigationLink extends JButton {

    private Page reference;
    private boolean isActive;

    public NavigationLink(int x, int y, Page page) {
        super(page.name());
        super.setContentAreaFilled(false);

        this.setFocusable(false);
        this.setBounds(x, y, UserInterface.NAVBAR_WIDTH, UserInterface.NAVBAR_BUTTON_HEIGHT);
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(SwingConstants.LEFT);

        // Change this to constants
        this.setBorder(UserInterface.NAVBAR_BUTTON_MARGIN);
        this.setBackground(UserInterface.NAVBAR_COLOUR);

        this.isActive = false;
        this.reference = page;
    }

    public Page getReference() {
        return reference;
    }

    public void toggleActive() {
        isActive = !isActive;
    }

    public void changePage(Page page) {
        this.reference = page;
        this.setText(page.name());
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setFont(UserInterface.orkney18);

        // Change appearance when hovered/pressed
        if (getModel().isPressed() || isActive) {
            g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
            setBorder(UserInterface.NAVBAR_BUTTON_HOVER_MARGIN);

        } else if (getModel().isRollover()) {
            g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR);
            setBorder(UserInterface.NAVBAR_BUTTON_HOVER_MARGIN);

        } else {
            g.setColor(getBackground());
            setBorder(UserInterface.NAVBAR_BUTTON_MARGIN);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}