package views.pages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import views.components.ContentPanel;
import views.components.CustomButton;

import config.UserInterface;
import config.MessageTypes;
import network.InvalidMessageException;
import network.Message;
import network.ServerConnection;
import views.Window;
import config.Page;

public class Play extends ContentPanel implements ActionListener {
    
    private Window window;

    private PlayMenuButton joinGameBtn;
    private PlayMenuButton createGameBtn;
    private PlayMenuButton browseGameBtn;
    private PlayMenuButton playBotBtn;

    private PlayMenuButton[] buttons;

    private final JLabel joinLobbyLabel= new JLabel();
    private final JTextField joinLobbyField = new JTextField();
    private String joinLobbyCode;

    private String[] buttonText = {
        "Join Game",
        "Create Game",
        "Browse Games",
        "Play Bot"
    };

    public Play(Window window) {
        this.window = window;

        joinGameBtn = new PlayMenuButton(buttonText[0], UserInterface.MENU_BUTTON_MARGIN, UserInterface.MENU_BUTTON_MARGIN);
        createGameBtn = new PlayMenuButton(
            buttonText[1], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN
        );
        joinGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(joinLobbyField.getText());
                joinLobbyCode = joinLobbyField.getText();
            }
        });

        browseGameBtn = new PlayMenuButton(
            buttonText[2], 
            UserInterface.MENU_BUTTON_MARGIN,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT
        );
        playBotBtn = new PlayMenuButton(
            buttonText[3], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT
        );

        buttons = new PlayMenuButton[]{
            joinGameBtn,
            createGameBtn,
            browseGameBtn,
            playBotBtn,
        };

        for (PlayMenuButton button : buttons) {
            button.addActionListener(this);
            button.setFont(UserInterface.PLAY_BUTTONS_FONT);
            this.add(button);
        }

        // joinLobbyLabel.setText("Join Lobby:");
        // joinLobbyLabel.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 300, 150, 25);
        // this.add(joinLobbyLabel);

        // joinLobbyField.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 320, 150, 25);
        // this.add(joinLobbyField);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameBtn) {
            // System.out.println("Join game");
            // try {
			// 	Message joinLobby = new Message(MessageTypes.JOIN_GAME);
            //     // joinLobby.addParam(this.getjoinLobbyCode());
            //     joinLobby.addParam("1000");
            //     ServerConnection.sendMessage(joinLobby);

            window.changePage(Page.JOIN_GAME);

            // Message response = ServerConnection.getMessage();
            // System.out.println(response.getText());

			// } catch (InvalidMessageException ex) {
			// 	ex.printStackTrace();
			// }
        } else if (e.getSource() == createGameBtn) {
            window.changePage(Page.CREATE_GAME);
            //createGame();

        } else if (e.getSource() == browseGameBtn) {
            // try {
			// 	Message browseGames = new Message(MessageTypes.BROWSE_GAMES);
            //     ServerConnection.sendMessage(browseGames);

            //     Message response = ServerConnection.getMessage();
            //     System.out.println(response.getText());
			// } catch (InvalidMessageException ex) {
			// 	ex.printStackTrace();
			// }
            window.changePage(Page.BROWSE_GAMES)
        } else if (e.getSource() == playBotBtn) {
            window.changePage(Page.PLAY_BOT);
        }
    }
    public String getjoinLobbyCode() {
        return this.joinLobbyCode;
    }

    public void createGame() {
        System.out.println("Create game");
        try {
            Message createLobby = new Message(MessageTypes.CREATE_GAME);
            ServerConnection.sendMessage(createLobby);

            // Message response = ServerConnection.getMessage();
            // System.out.println(response.getText());

        } catch (InvalidMessageException ex) {
            ex.printStackTrace();
        }
    }
}