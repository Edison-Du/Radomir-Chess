package views.chess;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Rectangle;

import config.UserInterface;

/**
 * [OpponentProposalPanel.java]
 * Panel for display takeback and draw proposals from the opponent 
 *
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class OpponentProposalPanel extends JPanel {

    private final Rectangle ACCEPT_BOUNDS = new Rectangle(0, 40, 75, 45);
    private final Rectangle DECLINE_BOUNDS = new Rectangle(90, 40, 75, 45);
    private final Rectangle PROPOSAL_BOUNDS = new Rectangle(0, 10, 150, 30);

    private final Color ACCEPT_COLOUR = new Color(57, 181, 74);
    private final Color DECLINE_COLOUR = new Color(237, 81, 36);

    private JLabel proposalText;
    
    public final GamePanelButton acceptButton;
    public final GamePanelButton declineButton;

    /**
    * OpponentProposalPanel
    * Creates opponent panel, adding buttons for accepting and declining
    * @param listener listener responding to button clicks
    */
    public OpponentProposalPanel(ActionListener listener) {

        setLayout(null);
        setBackground(UserInterface.FRAME_COLOUR);

        proposalText = new JLabel();
        proposalText.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        proposalText.setFont(UserInterface.orkney18);
        proposalText.setBounds(PROPOSAL_BOUNDS);
        this.add(proposalText);
        
        acceptButton = new GamePanelButton("Yes");
        acceptButton.setFont(UserInterface.orkney18);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setBounds(ACCEPT_BOUNDS);
        acceptButton.setBackground(ACCEPT_COLOUR);
        acceptButton.setHoverColor(ACCEPT_COLOUR.brighter());
        acceptButton.setPressedColor(ACCEPT_COLOUR.brighter());
        acceptButton.setRound(true);
        acceptButton.setBorderRadius(10);
        acceptButton.setBorder(UserInterface.FONT_OFFSET_BORDER);
        acceptButton.addActionListener(listener);
        this.add(acceptButton);

        declineButton = new GamePanelButton("No");
        declineButton.setFont(UserInterface.orkney18);
        declineButton.setForeground(Color.WHITE);
        declineButton.setBounds(DECLINE_BOUNDS);
        declineButton.setBackground(DECLINE_COLOUR);
        declineButton.setHoverColor(DECLINE_COLOUR.brighter());
        declineButton.setPressedColor(DECLINE_COLOUR.brighter());
        declineButton.setRound(true);
        declineButton.setBorderRadius(10);
        declineButton.setBorder(UserInterface.FONT_OFFSET_BORDER);
        declineButton.addActionListener(listener);
        this.add(declineButton);
    }

    /**
    * setProposalText
    * Sets the text message of the proposal
    * @param text the text message of the proposal
    */
    public void setProposalText(String text) {
        this.proposalText.setText(text);
        this.revalidate();
    }
}