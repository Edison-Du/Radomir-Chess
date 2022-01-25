package views.pages;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.Page;
import config.UserInterface;
import network.Lobby;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import views.components.LobbyListMouseAdapter;
import views.components.PanelButton;

/**
 * [BrowseGamesPage.java]
 * Displays a list of public game lobbies and updates every ten seconds
 * Users can join lobbies through this page
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public class BrowseGamesPage extends ContentPanel implements ActionListener {

    private final String JOIN_LOBBY_TEXT = "Join Lobby: ";
    private final Rectangle JOIN_LABEL_DIMENSIONS = new Rectangle(20, UserInterface.WINDOW_HEIGHT / 2 - 240, 400, 400);
    private final Rectangle LOBBY_LIST_BOUNDS = new Rectangle(320, 0, UserInterface.CONTENT_WIDTH - 320, UserInterface.WINDOW_HEIGHT);
    private final int JOIN_BUTTON_X = 20;
    private final int JOIN_BUTTON_Y = 400;
    private final EmptyBorder LOBBY_LIST_BORDER = new EmptyBorder(5, 5, 5, 5);
    private final int LOBBY_LIST_CELL_HEIGHT = 40;
    private final String BACK_BUTTON_TEXT = "Back";
    private final String JOIN_BUTTON_TEXT = "Join";

    private JList<String> lobbyList;
    private JScrollPane pane;
    private DefaultListModel<String> allLobbies = new DefaultListModel<>();
    private PanelButton joinButton;
    private JLabel joinLabel = new JLabel();
    private JPanel lobbyListPanel;
    private String joinGameCode;
    private ArrayList<Lobby> lobbies = new ArrayList<>();
    private int lobbyNumber;
    private PanelButton backButton;
    private Window window;

    /**
     * BrowseGamesPage
     * @param window 
     */
    public BrowseGamesPage(Window window) {

        this.window = window;
        LobbyListMouseAdapter lobbyListMouseAdapter = new LobbyListMouseAdapter(this);
    
        lobbyListPanel = new JPanel();
        lobbyListPanel.setBounds(LOBBY_LIST_BOUNDS);
        lobbyListPanel.setLayout(new BoxLayout(lobbyListPanel, BoxLayout.X_AXIS));
        lobbyListPanel.setBorder(null);
        
        lobbyList = new JList<>(allLobbies);
        lobbyList.setBackground(UserInterface.FRAME_COLOUR);
        lobbyList.setForeground(UserInterface.TEXT_COLOUR);
        lobbyList.setFont(UserInterface.orkney18);
        lobbyList.setFixedCellHeight(LOBBY_LIST_CELL_HEIGHT);
        lobbyList.setBorder(LOBBY_LIST_BORDER);
        lobbyList.setCellRenderer(getRenderer());
        lobbyList.addMouseListener(lobbyListMouseAdapter);

        pane = new JScrollPane(lobbyList);
        lobbyListPanel.add(pane);
        this.add(lobbyListPanel);

        joinLabel.setText(JOIN_LOBBY_TEXT);
        joinLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinLabel.setFont(UserInterface.orkney36);
        joinLabel.setBounds(JOIN_LABEL_DIMENSIONS);
        this.add(joinLabel);

        joinButton = new PanelButton(
            JOIN_BUTTON_TEXT,
            JOIN_BUTTON_X,
            JOIN_BUTTON_Y 
        );
        joinButton.addActionListener(this);
        this.add(joinButton);

        this.backButton = new PanelButton(BACK_BUTTON_TEXT, UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);
    }

    /**
     * ListCellRenderer
     * Cell Renderer for the lobby list
     */
    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, UserInterface.TEXT_COLOUR));
                listCellRendererComponent.setVerticalAlignment(SwingConstants.CENTER);
                return listCellRendererComponent;
            }
        };
    }

    /**
     * actionPerformed
     * Action Listener for the lobby list and buttons in the browse games page
     * @param e the action that occurs (mouse click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinButton) {
            Message message = new Message(MessageTypes.JOIN_GAME);
            message.addParam(joinGameCode);
            ServerConnection.sendMessage(message);
        } else if (e.getSource() == backButton) {
            window.changePage(Page.PLAY);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * selectLobby
     * Selects a lobby from the Jlist and updates the panel
     */
    public void selectLobby() {
        if (lobbyList.getSelectedIndex() != -1) {
            lobbyNumber = lobbyList.getSelectedIndex();
            joinGameCode = lobbies.get(lobbyNumber).getLobbyCode();
            joinLabel.setText(JOIN_LOBBY_TEXT + joinGameCode);
        }
    }

    /**
     * setLobbyList
     * Updates the list of active public lobbies and clears all selected items
     * Occurs when server sends updated information periodically
     * @param lobbies arraylist of all public lobbies
     */
    public void setLobbyList(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
        joinLabel.setText(JOIN_LOBBY_TEXT);
        joinGameCode = "";
        lobbyList.setSelectedIndex(-1);
        allLobbies.clear();
        for (Lobby lobby : lobbies) {
            allLobbies.addElement(lobby.getDisplayLobbyInfo());
        }
    }
}