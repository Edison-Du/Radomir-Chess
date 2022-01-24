package views.pages;

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
import views.components.PanelButton;

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

    private PanelButton backButton;
    private Window window;

    public BrowseGames(Window window) {

        this.window = window;
    
        lobbyListPanel = new JPanel();
        lobbyListPanel.setBounds(320, 0, UserInterface.CONTENT_WIDTH - 320, UserInterface.WINDOW_HEIGHT);
        lobbyListPanel.setLayout(new BoxLayout(lobbyListPanel, BoxLayout.X_AXIS));
        
        lobbyList = new JList<>(allLobbies);
        lobbyList.setBackground(UserInterface.FRAME_COLOUR);
        lobbyList.setForeground(UserInterface.TEXT_COLOUR);
        lobbyList.setFont(UserInterface.orkney18);
        lobbyList.setFixedCellHeight(40);
        lobbyList.setBorder(new EmptyBorder(5, 5, 5, 5));
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
        joinLabel.setFont(UserInterface.orkney36);
        joinLabel.setBounds(20, UserInterface.WINDOW_HEIGHT / 2 - 240, 400, 400);
        this.add(joinLabel);

        joinButton = new PanelButton(
            "Join",
            20,
            400 
        );
        joinButton.addActionListener(this);
        this.add(joinButton);

        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);
    }

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

    public ListCellRenderer<? super String> getRenderer() {
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

    public void setLobbyList(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
        joinLabel.setText("Join Lobby: ");
        joinGameCode = "";
        lobbyList.setSelectedIndex(-1);
        allLobbies.clear();
        for (Lobby lobby : lobbies) {
            allLobbies.addElement(lobby.getDisplayLobbyInfo());
        }
    }
}