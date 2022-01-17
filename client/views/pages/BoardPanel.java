package views.pages;

import javax.swing.JLabel;
import javax.swing.JButton;
// import javax.swing.JPanel;
// import config.UserInterface;
// import network.Message;
// import network.ServerConnection;
import views.chess.MouseEventListener;
import views.components.ContentPanel;

import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;

// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseListener;
// import java.awt.event.KeyAdapter;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
// import game.Pieces;

import logicai.ChessGame;
import logicai.Tile;

import java.awt.image.BufferedImage;

public class BoardPanel extends ContentPanel {
    // private final JLabel title = new JLabel();
    // private final JLabel profile = new JLabel();

    // private final JButton againstAI = new JButton();
    // private final Rectangle buttonBounds = new Rectangle(100, 100, 100, 100);

    // private final JButton pvp = new JButton();

    // //link to logic when done i just wanted to see how it looked
    // private final int BOARD_CORNER_WIDTH = UserInterface.CONTENT_WIDTH / 2 - 200;
    // private final int BOARD_CORNER_HEIGHT = UserInterface.WINDOW_HEIGHT / 2 - 200;
    // private final int GRID_LENGTH = 50;
    // private String[][] temp = {
    //     {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
    //     {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
    //     {"e", "e", "e", "e", "e", "e", "e", "e"},
    //     {"e", "e", "e", "e", "e", "e", "e", "e"},
    //     {"e", "e", "e", "e", "e", "e", "e", "e"},
    //     {"e", "e", "e", "e", "e", "e", "e", "e"},
    //     {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
    //     {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
    // };

    ChessGame game;
    final int tileSize = 60;

    private MouseEventListener mouseEventListener;
    BufferedImage heldPieceImage;

    public BoardPanel(ChessGame game) {
        this.game = game;

        this.setBounds(0, 0,tileSize * 8, tileSize * 8);

        mouseEventListener = new MouseEventListener(game);
        addMouseListener(mouseEventListener);
        addMouseMotionListener(mouseEventListener);
        
        /*
        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(UserInterface.WINDOW_TITLE);
        title.setSize(new Dimension(280, 80));
        title.setBounds(UserInterface.CONTENT_WIDTH / 2 - title.getWidth() / 2, 0, title.getWidth(), title.getHeight());
        // this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("JGepheri Soo");
        this.add(profile);

        //Play Buttons
        againstAI.setBounds(buttonBounds);

        // Pieces is obsolete at this point, delete it later uwu
        // new Pieces();
        */
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );
        drawBoard(g);
        heldPieceImage = mouseEventListener.getHeldPieceImage();
        // System.out.println(heldPieceImage);
        if(heldPieceImage != null) {
            // System.out.println(mouseEventListener.getMouseX() + ", " + mouseEventListener.getMouseY());
            g.drawImage(heldPieceImage, mouseEventListener.getMouseX()-tileSize/2, mouseEventListener.getMouseY()-tileSize/2, this);
        }
    }

    public void drawBoard(Graphics g) {
		Tile[][] checkerBoard = game.getCurrentPos().getTiles();

        // traverse entire maze and draw coloured square for each symbol in maze
        for (int x = 0; x < checkerBoard.length; x++) {
            for (int y = 0; y < checkerBoard[0].length; y++) {
                int xPos = x * tileSize;
                int yPos = (checkerBoard[0].length - y)*tileSize - 60;
                if (x%2-y%2==0) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(xPos, yPos, tileSize, tileSize);

                if(checkerBoard[x][y].getPiece() != null && checkerBoard[x][y].getPiece() != mouseEventListener.getSelectedPiece()) {
                    g.drawImage(checkerBoard[x][y].getPiece().getImage(), xPos, yPos, this);
                }
            }
        }
	}
}