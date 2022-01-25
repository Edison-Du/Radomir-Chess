package views.navigation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

import java.io.File;

import views.Window;
import config.UserInterface;
import config.Page;
import config.PathConsts;

/**
 * [NavigationBar.java]
 * The side bar that allows users to switch between pages
 * 
 * @author Edison Du
 * @author Alex Zhu
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class NavigationBar extends JPanel {
    
    // Listens for page changes
    private NavigationActionListener navigationActionListener;

    // Pages on the side bar
    private final Page[] navbarPages = {
        Page.PLAY,
        Page.SETTINGS,
        Page.ABOUT,
        Page.LOGIN,
        Page.QUIT
    };

    private final NavigationLink[] LINKS;
    private final Image HEADER_IMAGE = getHeaderImage();
    private final int LOGIN_PAGE = 3;
    private final int NAVBAR_BUTTON_OFFSET = 113;

    // Additional appearance features
    private JLabel playersOnlineLabel;
    private boolean numPlayersOdd;
    private JLabel usernameLabel;
    private String username = "";
    
    public NavigationBar (Window window) {

        // Navigation bar JPanel settings
        this.setBackground(UserInterface.NAVBAR_COLOUR);
        this.setPreferredSize(
            new Dimension(UserInterface.NAVBAR_WIDTH, UserInterface.WINDOW_HEIGHT)
        );
        this.setBounds(0, 0,  UserInterface.NAVBAR_WIDTH, UserInterface.WINDOW_HEIGHT);
        this.setLayout(null);

        navigationActionListener = new NavigationActionListener(window);
        LINKS = new NavigationLink[navbarPages.length];

        // Add all the page links to the JPanel
        for (int i = 0; i < navbarPages.length; i++) {
            int y = i * UserInterface.NAVBAR_BUTTON_HEIGHT + NAVBAR_BUTTON_OFFSET;
            Page currentPage = navbarPages[i];

            LINKS[i] = new NavigationLink(0, y, currentPage);
            LINKS[i].addActionListener(navigationActionListener);

            this.add(LINKS[i]);
        }

        // Label for showing how many players are online
        this.playersOnlineLabel = new JLabel();
        this.playersOnlineLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.playersOnlineLabel.setBounds(
            UserInterface.NAVBAR_LABEL_X + UserInterface.PLAYERS_ONLINE_RADIUS * 2,
            UserInterface.PLAYERS_ONLINE_Y,
            UserInterface.NAVBAR_LABEL_WIDTH,
            UserInterface.NAVBAR_LABEL_HEIGHT
        );
        this.playersOnlineLabel.setBorder(UserInterface.FONT_OFFSET_BORDER);
        this.playersOnlineLabel.setFont(UserInterface.orkney18);
        this.add(playersOnlineLabel);
        this.numPlayersOdd = false;

        // Label for showing the user's username
        this.usernameLabel = new JLabel();
        this.usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.usernameLabel.setBounds(
            UserInterface.NAVBAR_LABEL_X,
            UserInterface.USERNAME_Y,
            UserInterface.NAVBAR_LABEL_WIDTH,
            UserInterface.NAVBAR_LABEL_HEIGHT
            );
        this.usernameLabel.setBorder(UserInterface.FONT_OFFSET_BORDER);
        this.usernameLabel.setFont(UserInterface.orkney18);
        this.add(usernameLabel);

        // Initially change the page to the default page
        LINKS[0].doClick();
    }

    /**
     * paintComponent
     * Draws the navigation bar
     * @param Graphics the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(HEADER_IMAGE, 0, 0, this);
        g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
        g.fillRect(0, UserInterface.WINDOW_HEIGHT - 70, UserInterface.NAVBAR_WIDTH, 70);

        g.setColor(UserInterface.PLAYERS_ONLINE_COLOR);
        if(numPlayersOdd) {
            g.drawOval(UserInterface.NAVBAR_LABEL_X, UserInterface.PLAYERS_ONLINE_Y + 5, UserInterface.PLAYERS_ONLINE_RADIUS, UserInterface.PLAYERS_ONLINE_RADIUS);
            g.fillArc(UserInterface.NAVBAR_LABEL_X, UserInterface.PLAYERS_ONLINE_Y + 5, UserInterface.PLAYERS_ONLINE_RADIUS, UserInterface.PLAYERS_ONLINE_RADIUS, 90, 180);
        } else {
            g.fillOval(UserInterface.NAVBAR_LABEL_X, UserInterface.PLAYERS_ONLINE_Y + 5, UserInterface.PLAYERS_ONLINE_RADIUS, UserInterface.PLAYERS_ONLINE_RADIUS);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * setLoggedIn
     * switches page to main/play page after log in
     * @param boolean boolean with login state
     */
    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn) {
            LINKS[0].doClick();
            LINKS[LOGIN_PAGE].changePage(Page.LOGOUT);
        } else {
            LINKS[LOGIN_PAGE].changePage(Page.LOGIN);
        }
    }

    /**
     * getHeaderImage
     * reads the navigation bar header image
     * @return Image image object with image of navbar image
     */
    public static Image getHeaderImage() {
        try {
            return ImageIO.read(new File(PathConsts.NAVBAR_HEADER));
        } catch(Exception e) {
            System.out.println("File not found");
        }
        return null;
    }

    /**
     * setUsername
     * sets client username (updates name displayed in bottom left corner)
     * @param String String with the username
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
        this.usernameLabel.setText(newUsername);
    }

    /**
     * setPlayersOnline
     * updates the number of players online and displays
     * @param int the number of players online
     */
    public void setPlayersOnline(int numPlayers) {
        if (numPlayers == 1) {
            this.playersOnlineLabel.setText(numPlayers + " player online");
        } else {
            this.playersOnlineLabel.setText(numPlayers + " players online");
        }

        numPlayersOdd = numPlayers % 2 == 1;
    }

    /**
     * getUsername
     * @return String client username
     */
    public String getUsername() {
        return this.username;
    }
}
