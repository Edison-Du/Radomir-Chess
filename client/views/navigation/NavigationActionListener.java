package views.navigation;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import views.Window;

public class NavigationActionListener implements ActionListener{

    private Window window;
    private NavigationLink currentButton;

    public NavigationActionListener (Window window) {
        this.window = window;
        this.currentButton = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() instanceof NavigationLink) {
            NavigationLink button = (NavigationLink) e.getSource();

            if (currentButton != null) {
                currentButton.toggleActive();
            }
            currentButton = button;

            currentButton.toggleActive();
            try {
                window.changePage(currentButton.getReference());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}