package views.chess;

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
import config.PathConsts;
import config.UserInterface;
import sounds.SoundEffect;
import views.components.ContentPanel;
import views.pages.AbstractGamePanel;

/**
 * [ChessBoardPanel.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class ChessBoardPanel extends ContentPanel {

    ChessGame game;
    final int tileSize = 60;

    BufferedImage heldPieceImage;
    BufferedImage woodBoard, iceBoard;


    private ChessGameMouseListener chessGameMouseListener;
    private AbstractGamePanel gamePanel;

    public GameResultOverlay gameResultOverlay;

    public ChessBoardPanel(ChessGame game, AbstractGamePanel gamePanel) {
        this.game = game;
        this.gamePanel = gamePanel;

        chessGameMouseListener = new ChessGameMouseListener(game, gamePanel);
        addMouseListener(chessGameMouseListener);
        addMouseMotionListener(chessGameMouseListener);

        try {
            woodBoard = ImageIO.read(new File(PathConsts.WOOD_THEME));
            iceBoard = ImageIO.read(new File(PathConsts.ICE_THEME));
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

    public void setChessGame(ChessGame game) {
        this.game = game;
        this.chessGameMouseListener.game = game;
    }
    
    public void makeOpponentMove(String t1, String t2, String p) {
        String move = game.toAlgebraic(t1, t2, p);

        gamePanel.movesPanel.addMove(move);

        SoundEffect.playSound(t1, t2, p, game);

        this.game.move(t1, t2, p);
    }

    public void undoMove() {
        this.game.undo();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );

        // Draw board image if theme is selected
        if (UserInterface.activeTheme == UserInterface.WOOD_BOARD) {
            g.drawImage(woodBoard, 0, 0, this);
        } else if (UserInterface.activeTheme == UserInterface.ICE_BOARD) {
            g.drawImage(iceBoard, 0, 0, this);
        }

        drawBoard(g);

        // Draw possible moves for piece
        if (chessGameMouseListener.getSelectedPiece() != null && game.getCurrentPos().getToMove() == gamePanel.getPlayerColour() && UserInterface.highlightToggle) {
            drawPossibleMoves(g, game.getCurrentPos().getTile(chessGameMouseListener.t1));
        }

        heldPieceImage = chessGameMouseListener.getHeldPieceImage();
        if(heldPieceImage != null) {
            g.drawImage(heldPieceImage, chessGameMouseListener.getMouseX()-tileSize/2, chessGameMouseListener.getMouseY()-tileSize/2, this);
        }

        if(chessGameMouseListener.isPromoting) {
            BufferedImage promotionPlatter;
            try {
                promotionPlatter = ImageIO.read(new File(PathConsts.PROMOTION_PLATTER));
                g.drawImage(promotionPlatter, 110, 200, this);
            } catch (IOException e) {
                System.out.println("Could not load promotion platter");
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
                int xPos = (7 * gamePanel.getPlayerColour() + (1 - 2 * gamePanel.getPlayerColour()) * x) * tileSize;
                int yPos = (7 * (1 - gamePanel.getPlayerColour()) + (2 * gamePanel.getPlayerColour() - 1) * y) * tileSize;
                
                // Image/checkerboard code
                if (!UserInterface.isImageTheme) {
                    if (x % 2 - y % 2 == 0) {
                        g.setColor(UserInterface.darkerTile);
                    } else {
                        g.setColor(UserInterface.lighterTile);
                    }
                    g.fillRect(xPos, yPos, tileSize, tileSize);
                }

                // write tile notation
                if(x == 7) {
                    if((y+gamePanel.getPlayerColour()) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Integer.toString(y + 1), 1, yPos + 12);
                }
                if(y == 7) {
                    if((x+gamePanel.getPlayerColour()) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Character.toString((char)(x + 'a')), xPos + 52, 479);
                }

                // change based on gamePanel.getPlayerColour()
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
        synchronized(possibleMoves) {
            for (String i : possibleMoves) {
                // Determine coordinates for each colour
                if (gamePanel.getPlayerColour() == Constants.WHITE) {
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

}