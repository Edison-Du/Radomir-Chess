package views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.Consts;
import config.Page;
import views.navigation.NavigationBar;

public class Window extends JFrame {

    JPanel navigationBar;
    JPanel content;

    Page currentPage;

    public Window () {

        navigationBar = new NavigationBar(this);


        content = new Home();
        // this.changePage(Page.PAGE_1);


        this.setSize(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);
        this.setTitle(Consts.WINDOW_TITLE);
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

        // Change navbar page

        // System.out.println("Changing page");

        this.remove(content);

        if (currentPage == Page.PAGE_1) {
            content = new Home();

        } else if (currentPage == Page.PAGE_2) {
            // System.out.println("W");
            content = new Play();

        } else if (currentPage == Page.PAGE_3) {

            content = new Login();


        } else if (currentPage == Page.PAGE_4) {
            content = new Register();
            
        } else if (currentPage == Page.PAGE_5) {
            
        } else if (currentPage == Page.PAGE_6) {

        }
        
        this.add(content);

        content.revalidate();
        // this.validate();
        // this.revalidate();
        this.repaint();
        
    }

}