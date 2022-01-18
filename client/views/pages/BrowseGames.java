package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import config.UserInterface;
import views.components.ContentPanel;

public class BrowseGames extends ContentPanel implements ActionListener {
    // Constants
    private final JLabel titleLabel = new JLabel();
    private JList<String> lobbyList;

    private String[] lobbies;
    private DefaultListModel<String> allLobbies = new DefaultListModel<>();

    public BrowseGames() {
        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("sussy wussy");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 50, UserInterface.WINDOW_HEIGHT / 2 + 20, 210, 30);
        this.add(titleLabel);

        lobbyList = new JList<>(allLobbies);
        lobbyList.setBounds(0, 0, UserInterface.WINDOW_WIDTH / 2, UserInterface.WINDOW_HEIGHT);
        this.add(lobbyList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.revalidate();
        this.repaint();
    }

    public void setLobbyList(String lobbies) {
        allLobbies.clear();
        this.lobbies = lobbies.split(", ");
        for (String lobby : this.lobbies) {
            allLobbies.addElement(lobby);
        }
    }
}
