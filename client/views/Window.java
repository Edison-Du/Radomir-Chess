package views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.GraphicConsts;
import config.Page;
import views.navigation.NavigationBar;
import views.pages.*;

public class Window extends JFrame {

    NavigationBar navigationBar;
    JPanel content;

    Page currentPage;

    public Window () {

        navigationBar = new NavigationBar(this);
        
        // Default page
        this.changePage(Page.PLAY);

        this.setSize(GraphicConsts.WINDOW_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setTitle(GraphicConsts.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        this.add(navigationBar);
        this.add(content);
    }

    // change to Boolean probably
    public void changePage(Page page) {
        // This might nt even need to be used

        if (currentPage == page) return;
        currentPage = page;

        if (content != null) {
            this.remove(content);
        }
        // This is terrible
        if (currentPage == Page.PLAY) {
            content = new Play();
        } else if (currentPage == Page.SETTINGS) {
            content = new Home(); // Just testing request
        } else if (currentPage == Page.ABOUT) {
            content = new Game(); // just putting here for no reason
        } else if (currentPage == Page.LOGIN) {
            content = new Login();
        } else if (currentPage == Page.QUIT) {
            System.exit(0);
        } 
        content.revalidate();
        this.add(content);
        this.repaint();
    }
}