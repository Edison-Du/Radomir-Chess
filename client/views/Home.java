package views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import config.GraphicConsts;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Home extends JPanel {
    //Wait are these supposed to be final I'm not sure (dude on the website made them final but also didn't capitalise the variable)
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();
    
    public Home() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);

        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(GraphicConsts.WINDOW_TITLE);
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("Home");
        this.add(profile);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(22, 22, 22));
        g.fillRect(0, 0, 200, 200);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("paint component test...", 220, 220);
    }
}
