package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

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
    private int[] lobbyNums;
    private int[] lobbyCodes;
    private String[] hostClients;
    private String[] hostColours;
    private DefaultListModel<String> allLobbies = new DefaultListModel<>();

    public BrowseGames() {
        // titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        // titleLabel.setText("asdf");
        // titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        // titleLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 50, UserInterface.WINDOW_HEIGHT / 2 + 20, 210, 30);
        // this.add(titleLabel);

        lobbyList = new JList<>(allLobbies);
        lobbyList.setBounds(0, 0, UserInterface.WINDOW_WIDTH, UserInterface.WINDOW_HEIGHT);
        lobbyList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == lobbyList) {
                    if (e.getClickCount() == 2) {
                        int lobbyNumber = lobbyList.locationToIndex(e.getPoint()) + 1;
                        System.out.println("Clicked lobby #: " + lobbyNumber);
                    }
                }
            }
        });
        this.add(lobbyList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.revalidate();
        this.repaint();
    }

    public void setLobbyList(String lobbies) {
        allLobbies.clear();
        if (!lobbies.equals("")) {
            this.lobbies = lobbies.split("], ");
            int numLobbies = this.lobbies.length;
            lobbyNums = new int[numLobbies];;
            lobbyCodes = new int[numLobbies];
            hostClients = new String[numLobbies];
            hostColours = new String[numLobbies];
            for (String lobby : this.lobbies) {
                if (lobby.indexOf("]") != -1) {
                    lobby = lobby.substring(1, lobby.length() - 1);
                } else {
                    lobby = lobby.substring(1, lobby.length());
                }
                allLobbies.addElement("Lobby #" + lobby);
            }
        }
    }
}
