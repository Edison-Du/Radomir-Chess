package views;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.UserInterface;
import config.Page;
import config.PathConsts;
import views.navigation.NavigationBar;
import views.pages.*;

import config.MessageTypes;
import network.ServerConnection;
import network.Message;

/**
 * [Window.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class Window extends JFrame {

    public NavigationBar navigationBar;
    private Page currentPage;
    public JPanel content;
    
    private boolean inGame = false;
    private boolean inBotGame = false;
    private boolean loggedIn = false;

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

    public Window ()  {
        
        // Initialize fonts
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

        playBotPanel = new BotPanel(this);

        // Default page
        this.changePage(Page.PLAY);

        this.setTitle(UserInterface.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(navigationBar, BorderLayout.WEST);
        this.getContentPane().add(content);
        this.setVisible(true);
        this.pack();

        // Add icon
        ImageIcon img = new ImageIcon(PathConsts.CHESS_ICON);
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

    // Repainting
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

    public void setLoggedIn(boolean isLoggedIn) {
        this.loggedIn = isLoggedIn;
        this.navigationBar.setLoggedIn(isLoggedIn);
    }

    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    // change to Boolean probably
    public void changePage(Page page) {

        if (currentPage == page) return;
        currentPage = page;

        if (content != null) {
            this.remove(content);
        }
        // Change all pages into permanant variables to be reused, instead of reconstructing
        if (currentPage == Page.PLAY) {
            if (inGame) {
                content = gamePanel;
                currentPage = Page.GAME;

            } else if (inBotGame) {
                content = playBotPanel;
                currentPage = Page.PLAY_BOT;

            } else {
                content = homePagePanel;
            }
    
        } else if (currentPage == Page.JOIN_GAME) {
            joinGamePanel.removeError();
            content = joinGamePanel;
        
        } else if (currentPage == Page.GAME_SETUP) {
            gameSetupPanel.removeError();
            content = gameSetupPanel;
        
        } else if (currentPage == Page.BROWSE_GAMES) {
            content = browseGamesPanel;
        
        } else if (currentPage == Page.PLAY_BOT) {
            content = playBotPanel;

        } else if (currentPage == Page.BOT_GAME_SETUP) {
            content = botGameSetupPanel;

        } else if (currentPage == Page.GAME) {
            content = gamePanel;

        } else if (currentPage == Page.SETTINGS) {
            content = settingsPanel;

        } else if (currentPage == Page.ABOUT) {
            content = aboutPanel;
            
        } else if (currentPage == Page.LOGIN) {
            loginPanel.removeError();
            content = loginPanel;

        } else if (currentPage == Page.LOGOUT) {
            Message message = new Message(MessageTypes.LOGOUT);
            ServerConnection.sendMessage(message);

        } else if (currentPage == Page.QUIT) {
            System.exit(0);
        } 
        content.revalidate();
        this.add(content);
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setInBotGame(boolean inBotGame) {
        this.inBotGame = inBotGame;
    }
    
    /**
     * Updates all the settings preferences when a user logs in
     * @param userPreferences
     */
    public void setCurrentSettings(int[] userPreferences) {
        UserInterface.changeBoard(userPreferences[0]);
        UserInterface.changePieceSet(userPreferences[1]);
        UserInterface.changeHighlights(userPreferences[3]);
        settingsPanel.updateAfterLogin(userPreferences);
    }
}