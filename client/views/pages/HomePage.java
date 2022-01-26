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
 * Page displayed by default, with buttons linking to pages that
 * create/join games.
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public class HomePage extends ContentPanel implements ActionListener {
    

    private final int TITLE_SIZE = 128;
    private final String[] BUTTON_TEXT = {
        "Join Game",
        "Create Game",
        "Browse Games",
        "Play Bot"
    };

    // JComponents
    private JLabel gameTitle = new JLabel();
    private PlayMenuButton joinGameBtn;
    private PlayMenuButton createGameBtn;
    private PlayMenuButton browseGameBtn;
    private PlayMenuButton playBotBtn;
    private PlayMenuButton[] buttons;

    private Window window;

    /**
     * HomePage
     * Creates the home page panel with relevant buttons
     * @param window the window the page is on
     */
    public HomePage(Window window) {
        this.window = window;
        this.setLayout(null);

        // Page title
        gameTitle.setText(UserInterface.WINDOW_TITLE);
        gameTitle.setBounds(0, 0, UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT / 2);
        gameTitle.setFont(UserInterface.orkney.deriveFont(Font.PLAIN, TITLE_SIZE));
        gameTitle.setForeground(UserInterface.TEXT_COLOUR);
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(gameTitle);

        // Various buttons leading to pages for joining/creating games
        joinGameBtn = new PlayMenuButton(
            BUTTON_TEXT[0], 
            UserInterface.MENU_BUTTON_MARGIN, 
            UserInterface.MENU_BUTTON_MARGIN + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        createGameBtn = new PlayMenuButton(
            BUTTON_TEXT[1], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        browseGameBtn = new PlayMenuButton(
            BUTTON_TEXT[2], 
            UserInterface.MENU_BUTTON_MARGIN,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        playBotBtn = new PlayMenuButton(
            BUTTON_TEXT[3], 
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_WIDTH,
            UserInterface.MENU_BUTTON_MARGIN * 2 + UserInterface.MENU_BUTTON_HEIGHT + UserInterface.MENU_BUTTON_Y_OFFSET
        );

        buttons = new PlayMenuButton[]{
            joinGameBtn,
            createGameBtn,
            browseGameBtn,
            playBotBtn,
        };

        // Add listeners to each button
        for (PlayMenuButton button : buttons) {
            button.addActionListener(this);
            this.add(button);
        }
    }

    /**
     * actionPerformed
     * Detects which button has been clicked and redirect the user appropriately
     * @param e the event that occurred
     */
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