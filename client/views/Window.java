package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

// import chesslogic.ChessGame;
import config.UserInterface;
import java.awt.Color;
import config.Page;
import views.components.ContentPanel;
import views.navigation.NavigationBar;
import views.pages.*;

import config.MessageTypes;
import network.ServerConnection;
import network.Message;

public class Window extends JFrame {

    public NavigationBar navigationBar;
    private Page currentPage;
    private JPanel content;
    
    private boolean inGame = false;
    private boolean inBotGame = false;
    private boolean loggedIn = false;

    // Different pages
    public HomePage homePagePanel;
    public JoinGame joinGamePanel;
    public GameSetup gameSetupPanel;
    public BrowseGames browseGamesPanel;
    public BotPanel playBotPanel;
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
        browseGamesPanel = new BrowseGames(this);
        gamePanel = new MultiplayerPanel();
        settingsPanel = new Settings(this);
        aboutPanel = new About();
        loginPanel = new Login();

        // Navigation bar
        navigationBar = new NavigationBar(this);

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
            content = joinGamePanel;
        
        } else if (currentPage == Page.GAME_SETUP) {
            content = gameSetupPanel;
        
        } else if (currentPage == Page.BROWSE_GAMES) {
            content = browseGamesPanel;
        
        } else if (currentPage == Page.PLAY_BOT) {
            setInBotGame(true);
            if (playBotPanel == null) {
                playBotPanel = new BotPanel(this);
            }
            content = playBotPanel;

        } else if (currentPage == Page.GAME) {
            content = gamePanel;

        } else if (currentPage == Page.SETTINGS) {
            content = settingsPanel;

        } else if (currentPage == Page.ABOUT) {
            content = aboutPanel;
            
        } else if (currentPage == Page.LOGIN) {
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