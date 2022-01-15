package views.pages;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import game.Pieces;

public class Board extends ContentPanel {
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JButton againstAI = new JButton();
    private final Rectangle buttonBounds = new Rectangle(100, 100, 100, 100);

    private final JButton pvp = new JButton();

    //link to logic when done i just wanted to see how it looked
    private final int BOARD_CORNER_WIDTH = UserInterface.CONTENT_WIDTH / 2 - 200;
    private final int BOARD_CORNER_HEIGHT = UserInterface.WINDOW_HEIGHT / 2 - 200;
    private final int GRID_LENGTH = 50;
    private String[][] temp = {
        {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
        {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
        {"e", "e", "e", "e", "e", "e", "e", "e"},
        {"e", "e", "e", "e", "e", "e", "e", "e"},
        {"e", "e", "e", "e", "e", "e", "e", "e"},
        {"e", "e", "e", "e", "e", "e", "e", "e"},
        {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
        {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
    };

    public Board() {
        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(UserInterface.WINDOW_TITLE);
        title.setSize(new Dimension(280, 80));
        title.setBounds(UserInterface.CONTENT_WIDTH / 2 - title.getWidth() / 2, 0, title.getWidth(), title.getHeight());
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("JGepheri Soo");
        this.add(profile);

        //Play Buttons
        againstAI.setBounds(buttonBounds);

        new Pieces();
        
        // this.addMouseListener(new Listener());
		// this.addMouseMotionListener(new Listener());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );


        drawBoard(g);
        drawPieces(g);
    }

    public void drawBoard(Graphics g) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					g.setColor(new Color(118, 150, 86));
				} else {
					g.setColor(new Color(238, 238, 210));
				}
				g.fillRect(j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, GRID_LENGTH, GRID_LENGTH);
			}
		}
	}

    public void drawPieces(Graphics g) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (temp[i][j].equals("br")) {
                    g.drawImage(Pieces.bRook, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("bn")) {
                    g.drawImage(Pieces.bKnight, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("bb")) {
                    g.drawImage(Pieces.bBishop, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("bq")) {
                    g.drawImage(Pieces.bQueen, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("bk")) {
                    g.drawImage(Pieces.bKing, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("bp")) {
                    g.drawImage(Pieces.bPawn, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wr")) {
                    g.drawImage(Pieces.wRook, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wn")) {
                    g.drawImage(Pieces.wKnight, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wb")) {
                    g.drawImage(Pieces.wBishop, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wq")) {
                    g.drawImage(Pieces.wQueen, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wk")) {
                    g.drawImage(Pieces.wKing, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                } else if (temp[i][j].equals("wp")) {
                    g.drawImage(Pieces.wPawn, j * GRID_LENGTH + BOARD_CORNER_WIDTH, i * GRID_LENGTH + BOARD_CORNER_HEIGHT, null);
                }
			}
		}
	}
}
