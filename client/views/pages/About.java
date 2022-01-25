package views.pages;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import config.UserInterface;

/**
 * [About.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class About extends ContentPanel implements ActionListener {

    private final JLabel titleLabel = new JLabel("About");
    private final JTextArea textArea;
    private final JScrollPane scrollPane;

    public About() {

        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        this.add(titleLabel);

        textArea = new JTextArea(
            "ICS4UE Summative: Radomir Chess\n\n" +

            "1. Overview\n\n" + 

            "Radomir Chess is our ambitious Lichess/Chess.com-like chess application that runs purely on Java Swing. It features networking for many simultaneous multiplayer lobbies, a sophisticated and efficient chess engine, and an excellent UI complete with a login system. Our chess application is named after our computer science teacher, who will be marking this summative project.\n\n" + 

            "We have packed Radomir Chess with features for a truly fun and functional chess application, but there are potential rooms for improvement. Mainly, implementing a chess clock could help create more timely matches, and an elo system could raise the stakes of the already exciting games.\n\n" + 

            "2. How to Run\n\n" + 

            "For local use:\n\n" + 
            "* run one instance of Application.java found in the server folder\n" + 
            "* run as many instance of Application.java found in the client server as desired\n" + 
            "* make sure that the IP address in client > config > Consts.java is 127.0.0.1\n\n" + 

            "For online use:\n" + 
            "* run as many instance of Application.java found in the client server as desired\n" + 
            "* make sure that the IP address in client > config > Consts.java is the IP address of the server being run on a VM (the IP can be found in file)\n" + 
            "* this will only work while the VM is being run, which cannot be guaranteed as this is a school project not meant for permanent hosting.\n\n" + 

            "3. How to Use\n\n" + 

            "The Swing interface is intuitive. Use the left navigation bar to navigate between pages. The landing page will allow you to enter a chess match.\n" + 
            "* To create a game (public or private), press Create Game. To join a public game, you can find it in the Browse Games page or type in a game code in the Join Game page. Private games can only be joined by entering the lobby code on the Join Game page. To play against our bot, click Play Bot.\n" + 
            "* In the chess match, drag the pieces to make move. On the right bar, you can chat with your opponent and offer a takeback, draw, or resign.\n" + 
            "* In Settings, you can customize your game experience from 18 custom chess board themes, 5 custom chess piece sets, and 11 possible move colours. You can also toggle on/off move sounds and possible moves.\n" + 
            "* The About Page tells you about Radomir Chess. You are here right now!\n" + 
            "* In Login, you can register or login an account, which will let you display a custom username and save your custom settings.\n" + 
            "* Quit closes the application (but why would you ever want to do that?). If you ever want to return to the landing page, just press Play on the left navigation bar.\n\n" + 

            "4. Credits\n\n" + 

            "This application was built by Team JPANEL, which incredibly, is an acronym for the team's developers Jeffrey X, Peter G, Alex Z, Nicholas C, Edison D, and Leo G.\n\n" + 

            "Thanks to Jeffrey for his expertise in UI implementation. Thanks to Peter for his work on the chess engine and the account system. Thanks to Alex for his networking and chess game UI/UX contributions. Thanks to Nicholas for his handling of game lobbies and UI design. Thanks to Edison for designing the overall client/server system, leading the project and lending a hand to everyone. And Thanks to Leo for his crafting of the chess engine and game logic.\n\n" + 

            "Credit to Lichess and Chess.com, from which much inspiration and assets for features and graphics were sourced.\n\n" + 

            "We hope that you enjoy Radomir Chess!"
        );
        textArea.setFont(UserInterface.orkney18);
        textArea.setForeground(UserInterface.TEXT_COLOUR);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);
        textArea.setBackground(UserInterface.FRAME_COLOUR);

        scrollPane = new JScrollPane(textArea); 
        scrollPane.setBounds(UserInterface.ABOUT_PADDING, UserInterface.ABOUT_Y_BOUND, UserInterface.ABOUT_WIDTH, UserInterface.ABOUT_HEIGHT);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}