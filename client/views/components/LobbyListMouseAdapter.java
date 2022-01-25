package views.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import views.pages.BrowseGamesPage;

/**
 * [LobbyListMouseAdapter.java]
 * Mouse adapter for clicking on specific lobbies in the lobby list
 * 
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public class LobbyListMouseAdapter extends MouseAdapter {

    private BrowseGamesPage browseGamesPage;

    /**
     * LobbyListMouseAdapter
     * @param browsePage the page containing the lobby list
     */
    public LobbyListMouseAdapter(BrowseGamesPage browsePage) {
        this.browseGamesPage = browsePage;
    }

    /**
     * mouseReleased
     * Selects the lobby when the mouse is released
     * @param e the mouse release event
     */
    public void mouseReleased (MouseEvent e) {
        browseGamesPage.selectLobby();
    }
}
