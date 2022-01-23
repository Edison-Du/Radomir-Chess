package views.chess;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

import config.UserInterface;

public class OpponentProposalPanel extends JPanel {

    private JLabel proposalText;
    
    public final GamePanelButton acceptButton;
    public final GamePanelButton declineButton;

    public OpponentProposalPanel(ActionListener listener) {

        setLayout(null);
        setBackground(UserInterface.FRAME_COLOUR);

        proposalText = new JLabel();
        proposalText.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        proposalText.setBounds(0, 0, 150, 30);
        this.add(proposalText);
        
        acceptButton = new GamePanelButton("Yes");
        acceptButton.setBounds(0, 40, 75, 45);
        acceptButton.addActionListener(listener);
        this.add(acceptButton);

        declineButton = new GamePanelButton("No");
        declineButton.setBounds(90, 40, 75, 45);
        declineButton.addActionListener(listener);
        this.add(declineButton);
    }

    public void setProposalText(String text) {
        this.proposalText.setText(text);
        this.revalidate();
    }
}