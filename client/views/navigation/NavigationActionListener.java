package views.navigation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import config.Page;
import views.Window;

public class NavigationActionListener implements ActionListener{

    private Window window;

    public NavigationActionListener (Window window) {
        this.window = window;
    }

    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() instanceof NavigationLink) {
            NavigationLink button = (NavigationLink) e.getSource();
            window.changePage(button.getReference());
        }
    }
}