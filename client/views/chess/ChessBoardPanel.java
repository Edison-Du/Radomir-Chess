package views.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
// import game.Pieces;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.HashSet;

import chesslogic.ChessGame;
import chesslogic.Tile;
import chesslogic.Constants;
import config.PathsConsts;
import config.UserInterface;
import views.components.ContentPanel;
import views.pages.AbstractGamePanel;

public class ChessBoardPanel extends ContentPanel {

    ChessGame game;
    final int tileSize = 60;

    BufferedImage heldPieceImage;
    BufferedImage woodBoard;

    public int playerColour;

    private ChessGameMouseListener chessGameMouseListener;
    private AbstractGamePanel gamePanel;

    public GameResultOverlay gameResultOverlay;

    public ChessBoardPanel(ChessGame game, AbstractGamePanel gamePanel) {
        this.game = game;
        this.gamePanel = gamePanel;

        chessGameMouseListener = new ChessGameMouseListener(game, playerColour, gamePanel);
        addMouseListener(chessGameMouseListener);
        addMouseMotionListener(chessGameMouseListener);

        try {
            woodBoard = ImageIO.read(new File(PathsConsts.WOOD_THEME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameResultOverlay = new GameResultOverlay(gamePanel);
        gameResultOverlay.setBounds(0, 0, getWidth(), getHeight());
    }

    public void setOverlayVisible(boolean visible) {
        if (visible) {
            this.add(gameResultOverlay);
        } else {
            this.remove(gameResultOverlay);
        }
        this.revalidate();
    }

    public void setPlayerColour(int colour) {
        this.playerColour = colour;
        chessGameMouseListener.setPlayerColour(colour);
    }

    public void setChessGame(ChessGame game) {
        this.game = game;
        this.chessGameMouseListener.game = game;
    }
    
    public void makeOpponentMove(String t1, String t2, String p) {
        String move = game.getCurrentPos().toAlgebraic(t1, t2, p);

        int posX = (t2.charAt(0) - 'a');
        int posY = (t2.charAt(1) - '0') - 1;

        gamePanel.movesPanel.addMove(move);

        // Add piece to captured pieces
        if (playerColour == 0) {
            gamePanel.capturedPiecesPanelWhite.addCapturedPiece(
                game.getCurrentPos().getTiles()[posX][posY].getPiece()
            );

        } else {
            gamePanel.capturedPiecesPanelBlack.addCapturedPiece(
                game.getCurrentPos().getTiles()[posX][posY].getPiece()
            );
        }


        this.game.move(t1, t2, p);
    }

    public void undoMove() {
        System.out.println(this.game.toString());
        this.game.undo();
        System.out.println(this.game.toString());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );

        // Draw wood board image
        if (UserInterface.activeTheme == UserInterface.WOOD_BOARD) {
            g.drawImage(woodBoard, 0, 0, this);
        }

        drawBoard(g);

        // Draw possible moves for piece
        if (chessGameMouseListener.getSelectedPiece() != null && game.getCurrentPos().getToMove() == playerColour && UserInterface.highlightToggle) {
            drawPossibleMoves(g, game.getCurrentPos().getTile(chessGameMouseListener.t1));
        }

        heldPieceImage = chessGameMouseListener.getHeldPieceImage();
        // System.out.println(heldPieceImage);
        if(heldPieceImage != null) {
            // System.out.println(mouseEventListener.getMouseX() + ", " + mouseEventListener.getMouseY());
            g.drawImage(heldPieceImage, chessGameMouseListener.getMouseX()-tileSize/2, chessGameMouseListener.getMouseY()-tileSize/2, this);
        }

        if(chessGameMouseListener.isPromoting) {
            BufferedImage promotionPlatter;
            try {
                promotionPlatter = ImageIO.read(new File(PathsConsts.PROMOTION_PLATTER));
                g.drawImage(promotionPlatter, 110, 200, this);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Draws the chess board and pieces
     * @param g
     */
    public void drawBoard(Graphics g) {
		Tile[][] checkerBoard = game.getCurrentPos().getTiles();
        
        // traverse entire maze and draw coloured square for each symbol in maze
        for (int x = 0; x < checkerBoard.length; x++) {
            for (int y = 0; y < checkerBoard[0].length; y++) {

                // flip board
                int xPos = (7 * playerColour + (1 - 2 * playerColour) * x) * tileSize;
                int yPos = (7 * (1 - playerColour) + (2 * playerColour - 1) * y) * tileSize;
                
                // checkerboard code
                if (UserInterface.activeTheme != 2) {
                    if (x % 2 - y % 2 == 0) {
                        g.setColor(UserInterface.darkerTile);
                    } else {
                        g.setColor(UserInterface.lighterTile);
                    }
                    g.fillRect(xPos, yPos, tileSize, tileSize);
                }

                // write tile notation
                if(x == 7) {
                    if((y+playerColour) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Integer.toString(y + 1), 1, yPos + 12);
                }
                if(y == 7) {
                    if((x+playerColour) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Character.toString((char)(x + 'a')), xPos + 52, 479);
                }

                // change based on playerColour
                if(checkerBoard[x][y].getPiece() != null) {
                    if (checkerBoard[x][y].getPiece() != chessGameMouseListener.getSelectedPiece()) {
                        g.drawImage(checkerBoard[x][y].getPiece().getImage(), xPos, yPos, this);
                    } else if (UserInterface.highlightToggle) {
                        // Highlight selected piece
                        g.setColor(UserInterface.activeHighlightColour); 
                        g.fillRect(xPos, yPos, tileSize, tileSize);
                    }
                }
            }
        }
	}

    /**
     * Graphically displays relevant information for when user clicks on a piece
     * @param g
     * @param selectedPiecePos
     */
    public void drawPossibleMoves(Graphics g, Tile selectedPiecePos) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(UserInterface.HIGHLIGHT_LINE_THICKNESS);

        HashSet<String> possibleMoves = game.getCurrentPos().legalMoves(selectedPiecePos);

        int xPos = 0;
        int yPos = 0;
        g.setColor(UserInterface.activeHighlightColour); 
        for (String i : possibleMoves) {
            // Determine coordinates for each colour
            if (playerColour == Constants.WHITE) {
                xPos = (i.charAt(0) - 'a') * tileSize;
                yPos = (8 - Character.getNumericValue(i.charAt(1))) * tileSize;
            } else  {
                xPos = (7 - i.charAt(0) + 'a') * tileSize;
                yPos = Character.getNumericValue(i.charAt(1) - 1) * tileSize;
            }
            // Draw box around capturable pieces and dot for empty squares
            if (game.getCurrentPos().getTile(i).getPiece() != null) {
                g.drawOval(xPos + 2, yPos + 2, 56, 56);
            } else {
                g.fillOval(xPos + 23, yPos + 23, 14, 14);
            }
        }
    }
}