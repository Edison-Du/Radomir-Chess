package views.pages;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;

import config.UserInterface;
import views.Window;
import views.components.ContentPanel;
import views.components.PlayMenuButton;
import config.Page;

/**
 * [HomePage.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class HomePage extends ContentPanel implements ActionListener {
    
    private Window window;

    private final int TITLE_SIZE = 128;
    private final JLabel gameTitle = new JLabel();

    private PlayMenuButton joinGameBtn;
    private PlayMenuButton createGameBtn;
    private PlayMenuButton browseGameBtn;
    private PlayMenuButton playBotBtn;

    private PlayMenuButton[] buttons;

    private String[] buttonText = {
        "Join Game",
        "Create Game",
        "Browse Games",
        "Play Bot"
    };

    public HomePage(Window window) {
        this.window = window;
        this.setLayout(null);

        gameTitle.setText(UserInterface.WINDOW_TITLE);
        gameTitle.setBounds(0, 0, UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT / 2);
        gameTitle.setFont(UserInterface.orkney.deriveFont(Font.PLAIN, TITLE_SIZE));
        gameTitle.setForeground(UserInterface.TEXT_COLOUR);
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(gameTitle);

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
            this.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameBtn) {
            window.changePage(Page.JOIN_GAME);

        } else if (e.getSource() == createGameBtn) {
            window.changePage(Page.GAME_SETUP);

        } else if (e.getSource() == browseGameBtn) {
            window.changePage(Page.BROWSE_GAMES);

        } else if (e.getSource() == playBotBtn) {
            window.changePage(Page.BOT_GAME_SETUP);
        }
    }
}