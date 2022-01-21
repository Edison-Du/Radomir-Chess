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
        Page.LOGIN,
        Page.SETTINGS,
        Page.ABOUT,
        Page.QUIT
    };

    private final NavigationLink[] links;
    private final Image radomirLogo = getLogoImage();
    private final int loginPage = 3;
    private JLabel usernameLabel;
    
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
            int y = i * UserInterface.NAVBAR_BUTTON_HEIGHT;
            Page currentPage = navbarPages[i];

            links[i] = new NavigationLink(x, y, currentPage);
            links[i].addActionListener(navigationActionListener);

            this.add(links[i]);

        }

        this.usernameLabel = new JLabel();
        this.usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.usernameLabel.setBounds(UserInterface.NAVBAR_WIDTH / 2 - 70, UserInterface.WINDOW_HEIGHT - 45, 200, 25);
        this.usernameLabel.setFont(UserInterface.USERNAME_FONT);
        this.usernameLabel.setText(UserInterface.GUEST);

        this.add(usernameLabel);

        // Default page
        links[0].doClick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(radomirLogo, UserInterface.NAVBAR_WIDTH / 2 - 70, UserInterface.WINDOW_HEIGHT / 2 + 50, this);
        g.setColor(UserInterface.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
        g.fillRect(0, UserInterface.WINDOW_HEIGHT-70, UserInterface.NAVBAR_WIDTH, 70);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn) {
            links[0].doClick();
            links[loginPage].changePage(Page.LOGOUT);
        } else {
            this.setUsername(UserInterface.GUEST);
            links[loginPage].changePage(Page.LOGIN);
        }
    }

    public static Image getLogoImage() {
        try {
            return ImageIO.read(new File(PathsConsts.CHESS_LOGO)).getScaledInstance(140, 175, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }

    public void setUsername(String newUsername){
        this.usernameLabel.setText(newUsername);
    }
}
