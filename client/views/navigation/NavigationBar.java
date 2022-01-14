package views.navigation;

import java.awt.Dimension;

import javax.swing.JPanel;

import views.Window;
import config.GraphicConsts;
import config.Page;

public class NavigationBar extends JPanel {
    
    private NavigationActionListener navigationActionListener;

    private final Page[] navbarPages = {
        Page.PLAY,
        Page.SETTINGS,
        Page.ABOUT,
        Page.LOGIN,
        Page.QUIT
    };

    
    public NavigationBar (Window window) {

        this.setBackground(GraphicConsts.NAVBAR_COLOUR);
        this.setPreferredSize(
            new Dimension(GraphicConsts.NAVBAR_WIDTH, GraphicConsts.WINDOW_HEIGHT)
        );
        this.setBounds(0, 0,  GraphicConsts.NAVBAR_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setLayout(null);

        navigationActionListener = new NavigationActionListener(window);

        for (int i = 0; i < navbarPages.length; i++) {
            int x = 0;
            int y = i * GraphicConsts.NAVBAR_BUTTON_HEIGHT;
            Page currentPage = navbarPages[i];

            NavigationLink button = new NavigationLink(x, y, currentPage);
            button.addActionListener(navigationActionListener);

            this.add(button);

            // Default page
            if (currentPage == Page.PLAY) {
                button.doClick();
            }
        }
    }
}
