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

        // Default page
        links[0].doClick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(radomirLogo, UserInterface.NAVBAR_WIDTH / 2 - 70, UserInterface.WINDOW_HEIGHT - 260, this);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        if (isLoggedIn) {
            links[0].doClick();
            links[loginPage].changePage(Page.LOGOUT);
            this.add(usernameLabel);
        } else {
            links[loginPage].changePage(Page.LOGIN);
            this.remove(usernameLabel);
        }
    }

    public static Image getLogoImage() {
        try {
            return ImageIO.read(new File("views/navigation/radomirchess.png")).getScaledInstance(140, 155, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            System.out.println("File not found");
        }
        return null;
    }

    public void setUsername(String newUsername){
        this.usernameLabel = new JLabel();
        this.usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.usernameLabel.setText(newUsername);
        this.usernameLabel.setBounds(UserInterface.NAVBAR_WIDTH / 2 - 70, UserInterface.WINDOW_HEIGHT - 50, 100, 25);
        System.out.println("Bro");
    }
}
