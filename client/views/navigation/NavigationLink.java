package views.navigation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import config.UserInterface;
import config.Page;

/**
 * [NavigationLink.java]
 * A link on the navigation bar that directs the
 * user to a certain page
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class NavigationLink extends JButton {

    private Page reference;
    private boolean isActive;

    /**
     * NavigationLink
     * Creates an individual link on the navigation bar
     * 
     * @param x the x position of the link
     * @param y the y position of the link
     * @param page the page the link directs to
     */
    public NavigationLink(int x, int y, Page page) {
        super(page.name());
        super.setContentAreaFilled(false);

        this.setFocusable(false);
        this.setBounds(x, y, UserInterface.NAVBAR_WIDTH, UserInterface.NAVBAR_BUTTON_HEIGHT);
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setBorder(UserInterface.NAVBAR_BUTTON_MARGIN);
        this.setBackground(UserInterface.NAVBAR_COLOUR);

        this.isActive = false;
        this.reference = page;
    }

    /**
     * getReference
     * Getter for the page this link directs to
     * @return the page this link directs to
     */
    public Page getReference() {
        return reference;
    }

    /**
     * toggleActive
     * Toggles whether or not the user is currently on this link
     */
    public void toggleActive() {
        isActive = !isActive;
    }

    /**
     * changePage
     * Changes the page that this link directs to
     */
    public void changePage(Page page) {
        this.reference = page;
        this.setText(page.name());
    }

    /**
     * paintComponent
     * Draws the navigation link differently if it is clicked/hovered
     * @param g the graphics object to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {

        // Change appearance when pressed
        if (getModel().isPressed() || isActive) {
            g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
            setBorder(UserInterface.NAVBAR_BUTTON_HOVER_MARGIN);

        // Change the appearance when hovered
        } else if (getModel().isRollover()) {
            g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR);
            setBorder(UserInterface.NAVBAR_BUTTON_HOVER_MARGIN);

        } else {
            g.setColor(getBackground());
            setBorder(UserInterface.NAVBAR_BUTTON_MARGIN);
        }

        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(UserInterface.orkney18);
        super.paintComponent(g);
    }
}