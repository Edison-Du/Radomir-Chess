package views.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import views.pages.BrowseGamesPage;

/**
 * [LobbyListMouseAdapter.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class LobbyListMouseAdapter extends MouseAdapter {

    private BrowseGamesPage browseGamesPage;

    public LobbyListMouseAdapter(BrowseGamesPage browsePage) {
        this.browseGamesPage = browsePage;
    }

    public void mouseReleased (MouseEvent e) {
        browseGamesPage.selectLobby();
    }
}
