package views.navigation;

import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import views.Window;
import config.UserInterface;
import config.Page;
import config.PathsConsts;

public class NavigationBar extends JPanel {
    
    private NavigationActionListener navigationActionListener;

    private final Page[] navbarPages = {
        Page.PLAY,
        Page.SETTINGS,
        Page.ABOUT,
        Page.LOGIN,
        Page.QUIT
    };

    private final NavigationLink[] links;
    private final Image headerImage = getHeaderImage();
    private final int loginPage = 3;


    private JLabel playersOnlineLabel;
    private JLabel usernameLabel;
    private String username;
    
    public NavigationBar (Window window) {

        this.setBackground(UserInterface.NAVBAR_COLOUR);
        this.setPreferredSize(
            new Dimension(UserInterface.NAVBAR_WIDTH, UserInterface.WINDOW_HEIGHT)
        );
        this.setBounds(0, 0,  UserInterface.NAVBAR_WIDTH, UserInterface.WINDOW_HEIGHT);
        this.setLayout(null);


        navigationActionListener = new NavigationActionListener(window);

        links = new NavigationLink[navbarPages.length];

        for (int i = 0; i < navbarPages.length; i++) {
            int x = 0;
            int y = i * UserInterface.NAVBAR_BUTTON_HEIGHT + UserInterface.NAVBAR_WIDTH / 2 - 15;
            Page currentPage = navbarPages[i];

            links[i] = new NavigationLink(x, y, currentPage);
            links[i].addActionListener(navigationActionListener);

            this.add(links[i]);

        }

        this.usernameLabel = new JLabel();
        this.usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.usernameLabel.setBounds(30, UserInterface.WINDOW_HEIGHT - 47, 200, 25);
        this.usernameLabel.setBorder(UserInterface.FONT_OFFSET_BORDER);
        this.usernameLabel.setFont(UserInterface.orkney18);
        this.add(usernameLabel);

        //change constants
        this.playersOnlineLabel = new JLabel();
        this.playersOnlineLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.playersOnlineLabel.setBounds(30, UserInterface.WINDOW_HEIGHT - 123, 200, 25);
        this.playersOnlineLabel.setBorder(UserInterface.FONT_OFFSET_BORDER);
        this.playersOnlineLabel.setFont(UserInterface.orkney18);
        this.add(playersOnlineLabel);



        // Default page
        links[0].doClick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(headerImage, 0, 0, this);
        g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
        g.fillRect(0, UserInterface.WINDOW_HEIGHT - 70, UserInterface.NAVBAR_WIDTH, 70);
        this.revalidate();
        this.repaint();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn) {
            links[0].doClick();
            links[loginPage].changePage(Page.LOGOUT);
        } else {
            // this.setUsername(UserInterface.GUEST);
            links[loginPage].changePage(Page.LOGIN);
        }
    }

    public static Image getHeaderImage() {
        try {
            return ImageIO.read(new File(PathsConsts.NAVBAR_HEADER)).getScaledInstance(UserInterface.NAVBAR_WIDTH, UserInterface.NAVBAR_WIDTH / 2 - 15, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
        this.usernameLabel.setText(newUsername);
    }

    public void setPlayersOnline(int numPlayers) {
        if (numPlayers == 1) {
            this.playersOnlineLabel.setText(numPlayers + " player online");
        } else {
            this.playersOnlineLabel.setText(numPlayers + " players online");
        }
    }

    public String getUsername() {
        return this.username;
    }
}