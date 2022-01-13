package views.pages;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import views.components.ContentPanel;
import views.components.CustomButton;

import config.GraphicConsts;

public class Play extends ContentPanel implements ActionListener {
    
    // Constants
    
    private CustomButton joinGameBtn;
    private CustomButton createGameBtn;
    private CustomButton browseGameBtn;
    private CustomButton playBotBtn;

    private CustomButton[] buttons = {
        joinGameBtn,
        createGameBtn,
        browseGameBtn,
        playBotBtn,
    };

    private String[] buttonText = {
        "Join Game",
        "Create Game",
        "Browser Games",
        "Play Bot",
    };

    public Play() {

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new CustomButton(buttonText[i]);
            buttons[i].setBackground(GraphicConsts.NAVBAR_COLOUR);
            buttons[i].setBounds(
                GraphicConsts.MENU_BUTTON_MARGIN,
                GraphicConsts.MENU_BUTTON_HEIGHT * i,
                GraphicConsts.MENU_BUTTON_WIDTH,
                GraphicConsts.MENU_BUTTON_HEIGHT
            );
            buttons[i].setHoverColor(GraphicConsts.NAVBAR_BUTTON_HOVER_COLOUR);
            buttons[i].setPressedColor(GraphicConsts.NAVBAR_BUTTON_HOVER_COLOUR.brighter());

            //buttons[i].setRound(true);
            buttons[i].setBorder(new EmptyBorder(0,0,0,0));
            buttons[i].addActionListener(this);
            this.add(buttons[i]);
        }
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameBtn) {
            System.out.println("Join game");

        } else if (e.getSource() == createGameBtn) {
            System.out.println("Create game");

        } else if (e.getSource() == browseGameBtn) {

        } else if (e.getSource() == playBotBtn) {

        }
    }
}