package views.navigation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;

import views.Window;

/**
 * [NavigationActionListener.java]
 * Listens to link clicks and changes the page
 * 
 * @author Edison Du
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class NavigationActionListener implements ActionListener{

    private Window window;
    private NavigationLink currentButton;

    /**
     * NavigationActionListener
     * Construts a navigation listener that changes pages for the specified window
     * @param window the window that's page is being changed
     */
    public NavigationActionListener (Window window) {
        this.window = window;
        this.currentButton = null;
    }

    /**
     * actionPerformed
     * Listen to link clicks
     * @param e the click that occurred
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() instanceof NavigationLink) {

            NavigationLink button = (NavigationLink) e.getSource();

            // Send message to server to save changes the user has made in their settings
            if (UserInterface.changeMade) {
                int[] settings = UserInterface.getCurrentSettings();
                Message message = new Message(MessageTypes.UPDATE_PREFERENCES);

                message.addParam(window.navigationBar.getUsername());
                for (int i = 0; i < UserInterface.NUM_SETTINGS; i++) {
                    message.addParam(Integer.toString(settings[i]));
                }
                ServerConnection.sendMessage(message);

                UserInterface.changeMade = false;
            }

            // Toggles off the link the user was previously on
            if (currentButton != null) {
                currentButton.toggleActive();
            }

            // Toggle on the new link
            currentButton = button;
            currentButton.toggleActive();

            window.changePage(currentButton.getReference());
        }
    }
}