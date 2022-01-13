package views.components;

import javax.swing.JPanel;

import config.GraphicConsts;

import java.awt.Color;
import java.awt.Dimension;

public class ContentPanel extends JPanel {
    public ContentPanel() {
        this.setBackground(GraphicConsts.FRAME_COLOUR);
        this.setPreferredSize(
            new Dimension(GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT)
        );
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setLayout(null);
    }
}
