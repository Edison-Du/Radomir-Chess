package views.chess;

import javax.swing.JPanel;

import config.UserInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

public class LobbyInfoPanel extends JPanel {
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
            g2d.setFont(UserInterface.LOBBY_INFO);
            g2d.drawString(lobbyTitle, 15, g2d.getFontMetrics().getMaxAscent() + getInsets().top + 2);

            // This is nested so that it doesn't show when there's no title
            if (lobbyType.length() > 0) {
                g2d.setFont(UserInterface.SETTINGS_FONT);
                g2d.drawString(lobbyType, 120, g2d.getFontMetrics().getMaxAscent() + getInsets().top + 16);
            }
        }
    }
}
