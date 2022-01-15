package views.pages;

import java.awt.Font;

import javax.swing.JLabel;

import views.components.ContentPanel;

public class Game extends ContentPanel {

    private String lobbyCode = "";
    private JLabel codeLabel;

    public Game() {
        codeLabel = new JLabel(lobbyCode);
        // Temporary
        codeLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        codeLabel.setBounds(100, 0, 100, 100);
        this.add(codeLabel);
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        codeLabel.setText(lobbyCode);
    }
}