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
    
    private PlayMenuButton joinGameBtn;
    private PlayMenuButton createGameBtn;
    private PlayMenuButton browseGameBtn;
    private PlayMenuButton playBotBtn;

    private PlayMenuButton[] buttons;

    private String[] buttonText = {
        "Join Game",
        "Create Game",
        "Browse Games",
        "Play Bot"
    };

    public Play() {

        joinGameBtn = new PlayMenuButton(buttonText[0], GraphicConsts.MENU_BUTTON_MARGIN, GraphicConsts.MENU_BUTTON_MARGIN);
        createGameBtn = new PlayMenuButton(
            buttonText[1], 
            GraphicConsts.MENU_BUTTON_MARGIN * 2 + GraphicConsts.MENU_BUTTON_WIDTH,
            GraphicConsts.MENU_BUTTON_MARGIN
        );
        browseGameBtn = new PlayMenuButton(
            buttonText[2], 
            GraphicConsts.MENU_BUTTON_MARGIN,
            GraphicConsts.MENU_BUTTON_MARGIN * 2 + GraphicConsts.MENU_BUTTON_HEIGHT
        );
        playBotBtn = new PlayMenuButton(
            buttonText[3], 
            GraphicConsts.MENU_BUTTON_MARGIN * 2 + GraphicConsts.MENU_BUTTON_WIDTH,
            GraphicConsts.MENU_BUTTON_MARGIN * 2 + GraphicConsts.MENU_BUTTON_HEIGHT
        );

        buttons = new PlayMenuButton[]{
            joinGameBtn,
            createGameBtn,
            browseGameBtn,
            playBotBtn,
        };

        for (PlayMenuButton button : buttons) {
            button.addActionListener(this);
            this.add(button);
        }

            // this.add(joinGameBtn);
            // this.add(createGameBtn);
            // this.add(browseGameBtn);
            // this.add(playBotBtn);

        // buttons[i].setBackground(GraphicConsts.MENU_BUTTON_COLOUR);
            // buttons[i].setBounds(
            //     GraphicConsts.MENU_BUTTON_MARGIN,
            //     GraphicConsts.MENU_BUTTON_Y_OFFSET + GraphicConsts.MENU_BUTTON_GAP * i,
            //     GraphicConsts.MENU_BUTTON_WIDTH,
            //     GraphicConsts.MENU_BUTTON_HEIGHT
            // );
            // buttons[i].setHoverColor(GraphicConsts.MENU_BUTTON_HIGHLIGHT);
            // buttons[i].setPressedColor(GraphicConsts.MENU_BUTTON_HIGHLIGHT.brighter());

            // buttons[i].setRound(true);
            // buttons[i].setBorder(new EmptyBorder(0,0,0,0));
            // buttons[i].addActionListener(this);
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