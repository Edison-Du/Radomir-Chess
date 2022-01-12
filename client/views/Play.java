package views;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import config.GraphicConsts;
import network.Request;
import network.ServerConnection;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;

public class Play extends JPanel {
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JButton againstAI = new JButton();
    private final Rectangle buttonBounds = new Rectangle(100, 100, 100, 100);

    private final JButton pvp = new JButton();

    public Play() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);

        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText("Among us");
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("JGepheri Soo");
        this.add(profile);

        //Play Buttons
        againstAI.setBounds(buttonBounds);


        try {
            Request test = new Request("JEFFERY");
            test.addParam("xu");
            test.addParam("100 euclid, band leader, clarinet leader, 100 ap math, 100 chemistry, 100 * 3 iq,");
            
            ServerConnection.sendRequest(test);

        } catch (Exception e) {
            System.out.println("??");
        }
    }
}
