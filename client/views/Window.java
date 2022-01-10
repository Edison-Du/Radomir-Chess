package views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.GraphicConsts;
import config.Page;
import views.navigation.NavigationBar;

public class Window extends JFrame {

    NavigationBar navigationBar;
    JPanel content;

    Page currentPage;

    public Window () {

        navigationBar = new NavigationBar(this);
        
        this.changePage(Page.PAGE_1);

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
        if (currentPage == Page.PAGE_1) {
            content = new Home();
        } else if (currentPage == Page.PAGE_2) {
            content = new Play();
        } else if (currentPage == Page.PAGE_3) {
            content = new Login();
        } else if (currentPage == Page.PAGE_4) {
            content = new Register();
        } else if (currentPage == Page.PAGE_5) {
        } else if (currentPage == Page.PAGE_6) {
        }

        content.revalidate();
        this.add(content);
        this.repaint();
    }
}