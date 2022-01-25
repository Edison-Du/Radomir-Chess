package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import config.UserInterface;
import config.Page;
import config.PathConsts;
import config.MessageTypes;
import views.navigation.NavigationBar;
import views.pages.*;
import network.ServerConnection;
import network.Message;

/**
 * [Window.java]
 * The JFrame of the user's application, with a 
 * navigation bar and different content pages
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @author Jeffrey Xu
 * @author Peter Gu
 * @version 1.0 Jan 24, 2022
 */
public class Window extends JFrame {

    public NavigationBar navigationBar;
    public JPanel content;
    private Page currentPage;

    // Different pages
    public HomePage homePagePanel;
    public JoinGame joinGamePanel;
    public GameSetup gameSetupPanel;
    public BrowseGamesPage browseGamesPanel;
    public BotPanel playBotPanel;
    public BotGameSetup botGameSetupPanel;
    public MultiplayerPanel gamePanel;
    public Settings settingsPanel;
    public About aboutPanel;
    public Login loginPanel;

    private boolean inGame = false;
    private boolean inBotGame = false;
    private boolean loggedIn = false;

    private ImageIcon img;

    /**
     * Window
     * Initializes all fonts, images, page panels, and
     * starts a thread to repaint the frame
     */
    public Window ()  {
        
        // Initialize fonts and images
        UserInterface.loadFonts();
        UserInterface.readAllPieceImages();

        // Initialize panels
        homePagePanel = new HomePage(this);
        joinGamePanel = new JoinGame(this);
        gameSetupPanel = new GameSetup(this);
        botGameSetupPanel = new BotGameSetup(this);
        browseGamesPanel = new BrowseGamesPage(this);
        gamePanel = new MultiplayerPanel();
        settingsPanel = new Settings(this);
        aboutPanel = new About();
        loginPanel = new Login();

        // Navigation bar
        navigationBar = new NavigationBar(this);

        // Bot game page
        playBotPanel = new BotPanel(this);

        // Set the default page
        this.changePage(Page.PLAY);

        // JFrame settings
        this.setTitle(UserInterface.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(navigationBar, BorderLayout.WEST);
        this.getContentPane().add(content);
        this.setVisible(true);
        this.pack();

        // Add icon
        img = new ImageIcon(PathConsts.CHESS_ICON);
        this.setIconImage(img.getImage());

        // Repainting thread
        Thread frameUpdateThread = new Thread(
            new Runnable() {
                public void run() {
                    updateFrame();
                }
            }
        );
        frameUpdateThread.start();
    }

    /**
     * updateFrame
     * Repaints the JFrame on a set interval
     */
    private void updateFrame() {
        try {
            while (true) {
                this.repaint();
                Thread.sleep(UserInterface.UPDATE_RATE);
            }

        } catch (Exception e) {
            System.out.println("Window update thread interrupted.");
            e.printStackTrace();
        }
    }

    /**
     * changePage
     * Changes the page being displayed by the window
     * @param page the page to be displayed by the window
     */
    public void changePage(Page page) {

        if (currentPage == page) {
            return;
        }
        
        currentPage = page;

        // Remove the current page
        if (content != null) {
            this.remove(content);
        }
        
        // Change the content panel to the new page
        if (currentPage == Page.PLAY) {

            // If a game is in session, automatically redirect to the game/bot game page when clicking play
            if (inGame) {
                content = gamePanel;
                currentPage = Page.GAME;

            } else if (inBotGame) {
                content = playBotPanel;
                currentPage = Page.PLAY_BOT;

            } else {
                content = homePagePanel;
            }
    
        // In between pages for getting into a game
        } else if (currentPage == Page.JOIN_GAME) {
            joinGamePanel.removeError();
            content = joinGamePanel;
        
        } else if (currentPage == Page.GAME_SETUP) {
            gameSetupPanel.removeError();
            content = gameSetupPanel;
        
        } else if (currentPage == Page.BROWSE_GAMES) {
            content = browseGamesPanel;

        } else if (currentPage == Page.BOT_GAME_SETUP) {
            content = botGameSetupPanel;

        // Game pages (bot page and multiplayer page)
        } else if (currentPage == Page.PLAY_BOT) {
            content = playBotPanel;

        } else if (currentPage == Page.GAME) {
            content = gamePanel;

        // Settings page to adjust UI appearance
        } else if (currentPage == Page.SETTINGS) {
            content = settingsPanel;

        // About page
        } else if (currentPage == Page.ABOUT) {
            content = aboutPanel;
            
        // Authentication pages
        } else if (currentPage == Page.LOGIN) {
            loginPanel.removeError();
            content = loginPanel;

        } else if (currentPage == Page.LOGOUT) {
            Message message = new Message(MessageTypes.LOGOUT);
            ServerConnection.sendMessage(message);

        // Quit the program
        } else if (currentPage == Page.QUIT) {
            System.exit(0);
        } 
        
        // Add the new page
        content.revalidate();
        this.add(content);
    }

    /**
     * setLoggedIn
     * Sets whether or not the user is logged in
     * @param isLoggedIn whether or not the user is logged in
     */
    public void setLoggedIn(boolean isLoggedIn) {
        this.loggedIn = isLoggedIn;
        this.navigationBar.setLoggedIn(isLoggedIn);
    }

    /**
     * isLoggedIn
     * Checks whether or not the user is logged in
     * @return whether or not the user is logged in
     */
    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    /**
     * setInGame
     * Sets whether or not the user is in a live chess game
     * @param inGame whether or not the user is in a live chess game
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * setInBotGame
     * Sets whether or not the user is in a bot game
     * @param inBotGame whether or not the user is in a bot game
     */
    public void setInBotGame(boolean inBotGame) {
        this.inBotGame = inBotGame;
    }
    
    /**
     * setCurrentSettings
     * Changes the look of the application as specified by the user
     * @param userPreferences the user's preferences for the appearance
     */
    public void setCurrentSettings(int[] userPreferences) {
        UserInterface.changeBoard(userPreferences[0]);
        UserInterface.changePieceSet(userPreferences[1]);
        UserInterface.changeHighlights(userPreferences[3]);
        settingsPanel.updateAfterLogin(userPreferences);
    }
}