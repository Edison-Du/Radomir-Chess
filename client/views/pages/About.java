package views.pages;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import config.UserInterface;

public class About extends ContentPanel implements ActionListener {

    private final JLabel titleLabel = new JLabel("About");
    private final JLabel text = new JLabel();

    public About() {
        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        this.add(titleLabel);

        //remove
        text.setFont(UserInterface.orkney18);
        text.setForeground(UserInterface.TEXT_COLOUR);
        text.setText("chess created by suspicious people");
        text.setBounds(30, 120, UserInterface.CONTENT_WIDTH, 20);
        this.add(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}