package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.UserInterface;
import logicai.Game;
import config.Page;
import views.chess.MouseEventListener;
import views.navigation.NavigationBar;
import views.pages.*;

import java.io.IOException;

public class Window extends JFrame {

    private NavigationBar navigationBar;
    private Page currentPage;

    private JPanel content;

    // Different pages
    public Play playPanel;
    public Game gamePanel;
    public Login loginPanel;


    public Window () throws IOException {
        
        // Initialize panels
        playPanel = new Play();
        gamePanel = new Game();
        loginPanel = new Login();

        navigationBar = new NavigationBar(this);



        // Default page
        this.changePage(Page.PLAY);

        // this.setSize(GraphicConsts.WINDOW_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setTitle(UserInterface.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        this.getContentPane().add(navigationBar, BorderLayout.WEST);
        this.getContentPane().add(content);

        this.setVisible(true);

        this.pack();
    }

    // change to Boolean probably
    public void changePage(Page page) throws IOException {

        if (currentPage == page) return;
        currentPage = page;

        if (content != null) {
            this.remove(content);
        }
        // Change all pages into permanet variables to be reused, instead of ocnstrcut new
        if (currentPage == Page.PLAY) {
            content = playPanel;

        } else if (currentPage == Page.GAME) {
            // content = gamePanel;

        } else if (currentPage == Page.SETTINGS) {
            content = new Board(); // testing

        } else if (currentPage == Page.ABOUT) {
            Game game = new Game();
            content = new GamePanel(game); // just putting here for no reason
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