package views.navigation;

import java.awt.event.ActionListener;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;

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

            // Send message to server to save changes made in settings
            if (UserInterface.changeMade) {
                UserInterface.changeMade = false;
                int[] settings = UserInterface.getCurrentSettings();
                Message m = new Message(MessageTypes.UPDATE_PREFERENCES);
                m.addParam(window.navigationBar.getUsername());
                for (int i = 0; i < UserInterface.NUM_SETTINGS; i++) {
                    m.addParam(Integer.toString(settings[i]));
                }
                ServerConnection.sendMessage(m);
            }

            if (currentButton != null) {
                currentButton.toggleActive();
            }
            currentButton = button;

            currentButton.toggleActive();

            window.changePage(currentButton.getReference());
        }
    }
}