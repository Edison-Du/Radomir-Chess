package views.pages;

import java.awt.event.ActionListener;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;

import config.UserInterface;
import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import config.Page;

public class Play extends ContentPanel implements ActionListener {
    
    private Window window;

    private final JLabel gameTitle = new JLabel();

    private PlayMenuButton joinGameBtn;
    private PlayMenuButton createGameBtn;
    private PlayMenuButton browseGameBtn;
    private PlayMenuButton playBotBtn;

    private PlayMenuButton[] buttons;
    private String joinLobbyCode;

    private String[] buttonText = {
        "Join Game",
        "Create Game",
        "Browse Games",
        "Play Bot"
    };

    public Play(Window window) {
        this.window = window;
        this.setLayout(null);

        //CHANGE INTO CONSTANTS
        gameTitle.setText("RADOMIR CHESS");
        gameTitle.setBounds(100, 0, UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT / 2);
        gameTitle.setFont(new Font("Serif", Font.PLAIN, 100));
        gameTitle.setForeground(UserInterface.TEXT_COLOUR);
        this.add(gameTitle);
        this.revalidate();

        joinGameBtn = new PlayMenuButton(
            buttonText[0], 
            UserInterface.MENU_BUTTON_MARGIN, 
            UserInterface.MENU_BUTTON_MARGIN + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        createGameBtn = new PlayMenuButton(
            buttonText[1], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        browseGameBtn = new PlayMenuButton(
            buttonText[2], 
            UserInterface.MENU_BUTTON_MARGIN,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT + UserInterface.MENU_BUTTON_Y_OFFSET
        );
        playBotBtn = new PlayMenuButton(
            buttonText[3], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT + UserInterface.MENU_BUTTON_Y_OFFSET
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
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameBtn) {
            window.changePage(Page.JOIN_GAME);

        } else if (e.getSource() == createGameBtn) {
            Message createLobby = new Message(MessageTypes.CREATE_GAME);
            ServerConnection.sendMessage(createLobby);
            window.changePage(Page.GAME);

        } else if (e.getSource() == browseGameBtn) {
            Message browseGames = new Message(MessageTypes.BROWSE_GAMES);
            ServerConnection.sendMessage(browseGames);
            window.changePage(Page.BROWSE_GAMES);

        } else if (e.getSource() == playBotBtn) {
            
            window.changePage(Page.PLAY_BOT);
        }
    }

    public String getjoinLobbyCode() {
        return this.joinLobbyCode;
    }

    // public void displayUserName(){
    //     System.out.println(window.loginPanel.getUsername());
    //     username.setForeground(UserInterface.TEXT_COLOUR);
    //     username.setText(window.loginPanel.getUsername());
    //     username.setBounds(0, 0, 200, 25);
    //     this.add(username);
    // }
}