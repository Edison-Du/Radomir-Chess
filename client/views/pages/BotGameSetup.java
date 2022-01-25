package views.pages;


import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import config.UserInterface;
import views.Window;
import views.components.ContentPanel;
import views.components.PanelButton;
import config.Page;

/**
 * [BotGameSetup.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class BotGameSetup extends ContentPanel implements ActionListener {

    private final int INSTRUCTION_LABEL_X = UserInterface.CONTENT_WIDTH / 2 - 150;
    private final int BUTTON_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int INSTRUCTION_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 110;
    private final int INSTRUCTION_LABEL_WIDTH = 300;
    private final int INSTRUCTION_LABEL_HEIGHT = 50;
    private final int EASY_BUTTON_Y = 340;
    private final int MEDIUM_BUTTON_Y = 430;
    private final int HARD_BUTTON_Y = 520;
    private final int CREATE_ERROR_Y = INSTRUCTION_LABEL_Y + 30;
    private final int EASY_DEPTH = 1;
    private final int MEDIUM_DEPTH = 3;
    private final int HARD_DEPTH = 5;
    private final String EASY_TEXT = "Easy";
    private final String MEDIUM_TEXT = "Medium";
    private final String HARD_TEXT = "Hard";

    private Window window;
    private JLabel instructionsLabel = new JLabel();
    private JLabel createErrorLabel = new JLabel("Failed to create lobby");
    private PanelButton createLowDepthGameBtn;
    private PanelButton createMediumDepthGameBtn;
    private PanelButton createHighDepthGameBtn;
    private PanelButton backButton;

    public BotGameSetup(Window window) {
        this.window = window;
        this.setLayout(null);

        instructionsLabel.setFont(UserInterface.orkney30);
        instructionsLabel.setText("Choose Bot Strength");
        instructionsLabel.setForeground(UserInterface.TEXT_COLOUR);
        instructionsLabel.setBounds(INSTRUCTION_LABEL_X, INSTRUCTION_LABEL_Y, INSTRUCTION_LABEL_WIDTH, INSTRUCTION_LABEL_HEIGHT);
        this.add(instructionsLabel);

        createLowDepthGameBtn = new PanelButton(
            EASY_TEXT,
            BUTTON_X,
            EASY_BUTTON_Y
        );
        createLowDepthGameBtn.addActionListener(this);
        this.add(createLowDepthGameBtn);

        createMediumDepthGameBtn = new PanelButton(
            MEDIUM_TEXT,
            BUTTON_X,
            MEDIUM_BUTTON_Y
        );
        createMediumDepthGameBtn.addActionListener(this);
        this.add(createMediumDepthGameBtn);

        createHighDepthGameBtn = new PanelButton(
            HARD_TEXT,
            BUTTON_X,
            HARD_BUTTON_Y
        );
        createHighDepthGameBtn.addActionListener(this);
        this.add(createHighDepthGameBtn);

        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);

        // Error
        createErrorLabel.setFont(UserInterface.orkney18);
        createErrorLabel.setForeground(UserInterface.ERROR_COLOUR);
        createErrorLabel.setBounds(INSTRUCTION_LABEL_X, CREATE_ERROR_Y, INSTRUCTION_LABEL_WIDTH, INSTRUCTION_LABEL_HEIGHT);

    }

    /**
     * actionPerformed
     * Action Listener for the buttons on the bot game setup page
     * @param e the action that occurs (mouse click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            window.changePage(Page.PLAY);

        } else if (e.getSource() == createLowDepthGameBtn) {
            window.setInBotGame(true);
            window.playBotPanel.setDepth(EASY_DEPTH);
            window.changePage(Page.PLAY_BOT);            

        } else if (e.getSource() == createMediumDepthGameBtn) {
            window.setInBotGame(true);
            window.playBotPanel.setDepth(MEDIUM_DEPTH);
            window.changePage(Page.PLAY_BOT);     

        } else if (e.getSource() == createHighDepthGameBtn) {
            window.setInBotGame(true);
            window.playBotPanel.setDepth(HARD_DEPTH);
            window.changePage(Page.PLAY_BOT);     

        }
    }
}