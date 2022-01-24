package views.chess;

import javax.swing.JPanel;

import config.UserInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class LobbyInfoPanel extends JPanel {

    private final int TITLE_X = 15;
    private final int TITLE_Y = 43;
    private final int TYPE_X = 120;
    private final int TYPE_Y = 45;

    private String lobbyTitle;
    private String lobbyType;

    public LobbyInfoPanel() {
        lobbyTitle = "";
        lobbyType = "";
    }
    
    public void setlobbyTitle(String title) {
        lobbyTitle = title;
    }

    public void setlobbyType(String type) {
        lobbyType = type;
    }

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