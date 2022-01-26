package views.chess;

import javax.swing.JPanel;

import config.UserInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * [LobbyInfoPanel.java]
 * Displays relevant information to a lobby, such as the lobby type and title
 *
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class LobbyInfoPanel extends JPanel {

    private final int TITLE_X = 15;
    private final int TITLE_Y = 43;
    private final int TYPE_X = 130;
    private final int TYPE_Y = 35;

    private String lobbyTitle;
    private String lobbyType;

    /**
     * LobbyInfoPanel
     * Initializes lobby info
     */
    public LobbyInfoPanel() {
        lobbyTitle = "";
        lobbyType = "";
    }

    /**
     * setLobbyTitle
     * Sets the lobby title
     * @param title the lobby title
     */
    public void setlobbyTitle(String title) {
        lobbyTitle = title;
    }

    /**
     * setLobbyType
     * Sets the lobby type
     * @param type the lobby type
     */
    public void setlobbyType(String type) {
        lobbyType = type;
    }

    /**
     * paintComponent
     * Draws the lobby information panel
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), UserInterface.GAME_INFO_BORDER_RADIUS, UserInterface.GAME_INFO_BORDER_RADIUS);

        if (lobbyTitle.length() > 0) {
            g2d.setColor(getForeground());
            g2d.setFont(UserInterface.orkney36);
            g2d.drawString(lobbyTitle, TITLE_X, TITLE_Y);

            // This is nested so that it doesn't show when there's no title
            if (lobbyType.length() > 0) {
                g2d.setFont(UserInterface.orkney18);
                g2d.drawString(lobbyType, TYPE_X, TYPE_Y);
            }
        }
    }
}