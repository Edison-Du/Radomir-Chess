package views.navigation;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Insets;

import views.Window;
import config.Consts;
import config.Page;

public class NavigationBar extends JPanel {
    
    private Page[] pages = {
        Page.PAGE_1,
        Page.PAGE_2,
        Page.PAGE_3,
        Page.PAGE_4,
        Page.PAGE_5,
        Page.PAGE_6
    };

    private Page currentPage;

    private NavigationActionListener navigationActionListener;
    
    public NavigationBar (Window window) {
        this.setBackground(Consts.NAVBAR_COLOUR);
        this.setBounds(0, 0,  Consts.NAVBAR_WIDTH, Consts.WINDOW_HEIGHT);
        this.setLayout(null);

        navigationActionListener = new NavigationActionListener(window);

        // Add buttons
        for (int i = 0; i < 6; i++) {
            JButton button = new NavigationLink(0, i*Consts.NAVBAR_BUTTON_HEIGHT, "Page " + i, pages[i]);
            button.addActionListener(navigationActionListener);
            this.add(button);
        }
    }

    // Possible put this in another file
    public void changePage () {
        
    }
}
