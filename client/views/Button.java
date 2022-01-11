// package views;

// import java.awt.Color;
// import java.awt.Graphics;

// import javax.swing.JButton;
// import javax.swing.border.EmptyBorder;
// import javax.swing.SwingConstants;

// import config.GraphicConsts;
// import config.Page;

// public class Button extends JButton {

//     public Button(int x, int y, String text) {
//         super(text);
//         super.setContentAreaFilled(false);

//         this.setFocusable(true);
//         this.setBounds(x, y, GraphicConsts.NAVBAR_WIDTH, GraphicConsts.NAVBAR_BUTTON_HEIGHT);
//         this.setForeground(Color.WHITE);
//         this.setHorizontalAlignment(SwingConstants.LEFT);

//         // Change this to constants
//         this.setBorder(GraphicConsts.NAVBAR_BUTTON_MARGIN);
//         this.setBackground(GraphicConsts.NAVBAR_COLOUR);
//     }

//     @Override
//     protected void paintComponent(Graphics g) {

//         // Change appearance when hovered/pressed
//         if (getModel().isPressed() || isActive) {
//             g.setColor(GraphicConsts.NAVBAR_BUTTON_HOVER_COLOUR.brighter());
//             setBorder(GraphicConsts.NAVBAR_BUTTON_HOVER_MARGIN);

//         } else if (getModel().isRollover()) {
//             g.setColor(GraphicConsts.NAVBAR_BUTTON_HOVER_COLOUR);
//             setBorder(GraphicConsts.NAVBAR_BUTTON_HOVER_MARGIN);

//         } else {
//             g.setColor(getBackground());
//             setBorder(GraphicConsts.NAVBAR_BUTTON_MARGIN);
//         }
//         g.fillRect(0, 0, getWidth(), getHeight());
//         super.paintComponent(g);
//     }
// }