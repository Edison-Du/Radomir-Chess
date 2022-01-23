package views.pages;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
// import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.UserInterface;
import network.Lobby;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

public class BrowseGames extends ContentPanel implements ActionListener {

    private JList<String> lobbyList;
    private JScrollPane pane;
    private DefaultListModel<String> allLobbies = new DefaultListModel<>();
    private PanelButton joinButton;
    private JLabel joinLabel = new JLabel();
    private JPanel lobbyListPanel;

    private ArrayList<Lobby> lobbies = new ArrayList<>();

    private static int lobbyNumber;
    private static String joinGameCode;

    public BrowseGames() {
    
        lobbyListPanel = new JPanel();
        lobbyListPanel.setBounds(0, 0, UserInterface.CONTENT_WIDTH / 2, UserInterface.WINDOW_HEIGHT);
        lobbyListPanel.setLayout(new BoxLayout(lobbyListPanel, BoxLayout.X_AXIS));
        lobbyList = new JList<>(allLobbies);
        lobbyList.setBackground(UserInterface.FRAME_COLOUR);
        lobbyList.setForeground(UserInterface.TEXT_COLOUR);
        lobbyList.setFont(new Font("Serif", Font.PLAIN, 20));
        lobbyList.setFixedCellHeight(50);
        lobbyList.setBorder(new EmptyBorder(10,10, 10, 10));
        lobbyList.setCellRenderer(getRenderer());
        lobbyList.addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e) {
                if (lobbyList.getSelectedIndex() != -1) {
                    lobbyNumber = lobbyList.getSelectedIndex();
                    joinGameCode = lobbies.get(lobbyNumber).getLobbyCode();
                    joinLabel.setText("Join Lobby: " + joinGameCode);
                }
            }
        });
        pane = new JScrollPane(lobbyList);  
        lobbyListPanel.add(pane);
        this.add(lobbyListPanel);

        joinLabel.setText("Join Lobby: ");
        joinLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        joinLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 + 125, UserInterface.WINDOW_HEIGHT / 2 - 240, 400, 400);
        this.add(joinLabel);

        joinButton = new PanelButton(
            "JOIN",
            615,
            400 
        );
        joinButton.addActionListener(this);
        joinButton.setFont(UserInterface.PLAY_BUTTONS_FONT);
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

    public ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UserInterface.TEXT_COLOUR));
                return listCellRendererComponent;
            }
        };
    }

    public void setLobbyList(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
        joinLabel.setText("Join Lobby: ");
        lobbyList.setSelectedIndex(-1);
        joinGameCode = "";
        allLobbies.clear();
        joinButton.doClick();
        for (Lobby lobby : lobbies) {
            allLobbies.addElement(lobby.getDisplayLobbyInfo());
        }
    }
}
