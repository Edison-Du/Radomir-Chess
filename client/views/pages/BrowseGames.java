package views.pages;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
// import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import config.MessageTypes;
import config.UserInterface;
import network.Lobby;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

public class BrowseGames extends ContentPanel implements ActionListener {
    // Constants
    // private final JLabel titleLabel = new JLabel();

    private JList<String> lobbyList;
    private JScrollPane pane;
    private DefaultListModel<String> allLobbies = new DefaultListModel<>();
    private JButton joinButton = new JButton();
    private JLabel joinLabel = new JLabel();

    private ArrayList<Lobby> lobbies = new ArrayList<>();

    private static int lobbyNumber;
    private static String joinGameCode;

    public BrowseGames() {
        // titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        // titleLabel.setText("asdf");
        // titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        // titleLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 50, UserInterface.WINDOW_HEIGHT / 2 + 20, 210, 30);
        // this.add(titleLabel);
        
        lobbyList = new JList<>(allLobbies);
        pane = new JScrollPane(lobbyList);
        this.add(pane);
        lobbyList.setBounds(0, 0, UserInterface.WINDOW_WIDTH / 2, UserInterface.WINDOW_HEIGHT);
        lobbyList.addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e) {
                if (lobbyList.getSelectedIndex() != -1) {
                    lobbyNumber = lobbyList.getSelectedIndex();
                    joinGameCode = lobbies.get(lobbyNumber).getLobbyCode();
                    joinLabel.setText("Join Lobby: " + joinGameCode);
                }
            }
        });
        this.add(lobbyList);

        joinLabel.setText("Join Lobby: ");
        joinLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        joinLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 + 195, UserInterface.WINDOW_HEIGHT / 2 - 280, 400, 400);
        this.add(joinLabel);

        joinButton.setBounds(UserInterface.CONTENT_WIDTH / 2 + 250, UserInterface.WINDOW_HEIGHT / 2, 150, 25);
        joinButton.addActionListener(this);
        joinButton.setText("JOIN");
        joinButton.setFocusable(false);
        this.add(joinButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinButton) {
            Message message = new Message(MessageTypes.JOIN_GAME);
            message.addParam(joinGameCode);
            ServerConnection.sendMessage(message);
        }
        this.revalidate();
        this.repaint();
    }

    public void setLobbyList(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
        allLobbies.clear();
        joinLabel.setText("Join Lobby: ");
        lobbyList.setSelectedIndex(-1);
        joinGameCode = "";
        for (Lobby lobby : lobbies) {
            allLobbies.addElement(lobby.getDisplayLobbyInfo());
        }
    }
}
