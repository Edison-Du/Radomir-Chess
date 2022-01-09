package views.navigation;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import config.Consts;
import config.Page;

public class NavigationLink extends JButton {

    private Page reference;

    public NavigationLink (int x, int y, String text, Page page) {

        Border line = new LineBorder(Consts.NAVBAR_COLOUR);
        Border margin = new EmptyBorder(0, 0, 0, 0);
        Border compound = new CompoundBorder(line, margin);

        this.setBounds(x, y, Consts.NAVBAR_WIDTH, Consts.NAVBAR_BUTTON_HEIGHT);
        this.setBackground(Consts.NAVBAR_COLOUR);
        this.setForeground(Color.WHITE);

        // mov etext
        this.setHorizontalAlignment(SwingConstants.LEFT);
        // use constants
        // this.setMargin(new Insets(0, 0, 0, 100));

        this.setBorder(compound);
        // this.setText(text);
        // this.setText("<html><b>Edit</b></html>");
        this.setText("<html><div style=\"color:rgb(125,125,125); margin: 0 0 0 50px;\">Your mother</div></html>");

        this.setFocusable(false);
        // this.setContentAreaFilled(false);

        this.addMouseListener(
            new NavigationMouseAdapter(this)
        );


        this.reference = page;
    }

    public Page getReference() {
        return this.reference;
    }
}