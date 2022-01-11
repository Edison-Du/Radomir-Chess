package views;

import javax.swing.JPanel;
import config.GraphicConsts;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Home extends JPanel {
    private final int TITLE_X = 200;
    private final int TITLE_Y = 200;
    private final int TITLE_FONT_SIZE = 50;
    
    public Home() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(22, 22, 22));
        g.setFont(new Font("TimesRoman", Font.PLAIN, TITLE_FONT_SIZE));
        g.drawString(GraphicConsts.WINDOW_TITLE, TITLE_X, TITLE_Y);
    }
}