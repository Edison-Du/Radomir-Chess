package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.UserInterface;
import logicai.ChessGame;
import config.Page;
import views.navigation.NavigationBar;
import views.pages.*;

import java.io.IOException;

public class Window extends JFrame {

    private NavigationBar navigationBar;
    private Page currentPage;
    private JPanel content;
    
    private boolean inGame = false;

    // Different pages
    public Play playPanel;
    public JoinGame joinGamePanel;
    public BrowseGames browseGamesPanel;
    public PlayBot playBotPanel;

    public Game gamePanel;
    public Login loginPanel;


    public Window () throws IOException {
        
        // Initialize panels
        playPanel = new Play(this);
        joinGamePanel = new JoinGame();
        browseGamesPanel = new BrowseGames();
        gamePanel = new Game();
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

    // change to Boolean probably
    public void changePage(Page page) {

        if (currentPage == page) return;
        currentPage = page;

        if (content != null) {
            this.remove(content);
        }
        // Change all pages into permanet variables to be reused, instead of ocnstrcut new
        if (currentPage == Page.PLAY) {
            if (inGame) {
                content = gamePanel;
                currentPage = Page.GAME;

            } else {
                content = playPanel;
            }
    
        } else if (currentPage == Page.JOIN_GAME) {
            content = joinGamePanel;
        
        } else if (currentPage == Page.BROWSE_GAMES) {
            content = browseGamesPanel;
        
        } else if (currentPage == Page.PLAY_BOT) {
            try {
                ChessGame game = new ChessGame();
                content = new PlayBot(game);
            } catch (Exception e){
                System.out.println("Cannot create chess game.");
                e.printStackTrace();
            }
        
        } else if (currentPage == Page.GAME) {
            content = gamePanel;
            inGame = true;

        } else if (currentPage == Page.SETTINGS) {
            // content = new Board(); // testing

        } else if (currentPage == Page.ABOUT) {
            try {
            ChessGame game = new ChessGame();
            content = new GamePanel(game);
            } catch (Exception e){
                System.out.println("Cannot create chess game.");
                e.printStackTrace();
            }
        } else if (currentPage == Page.LOGIN) {
            content = loginPanel;

        } else if (currentPage == Page.QUIT) {
            System.exit(0);
        } 
        content.revalidate();
        this.add(content);
        // this.repaint();
    }
}