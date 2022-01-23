package views.chess;

import javax.swing.JLabel;
import javax.swing.JPanel;

import config.UserInterface;

public class PlayerLabelPanel extends JPanel {
    private JLabel labelText;

    public PlayerLabelPanel() {

        this.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        this.setBackground(UserInterface.FRAME_COLOUR);

        labelText = new JLabel();
        labelText.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        labelText.setBackground(UserInterface.FRAME_COLOUR);
        this.add(labelText);
    }

    public void setText(String text) {
        System.out.println(text);
        this.labelText.setText(text);
        this.revalidate();
    }
}