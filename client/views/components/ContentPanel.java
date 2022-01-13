package views.components;

import javax.swing.JPanel;

import config.GraphicConsts;

import java.awt.Color;

public class ContentPanel extends JPanel {
    public ContentPanel() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setLayout(null);
    }
}
