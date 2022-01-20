package views.pages;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import chesslogic.ChessGame;
import config.GameState;
import views.chess.ChessBoardPanel;
import views.chess.GamePanelButton;
import views.chess.GameResultOverlay;
import views.chess.MessagePanel;
import views.chess.MovesPanel;
import config.UserInterface;
import views.components.ContentPanel;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // Chess game
    public ChessGame chessGame;

    // subpanel chess game
    public ChessBoardPanel boardPanel;
    public MovesPanel movesPanel;
    public MessagePanel messagePanel;

    // public GameResultOverlay gameResultOverlay;

    public final GamePanelButton resign;

    private int playerColour;
    private GameState gameState;


    public AbstractGamePanel() {
        
        // Adds components to panel
        initialize();

        resign = new GamePanelButton("Resign");
        resign.setBounds(660, 240, 80, 60);
        resign.addActionListener(this);
        this.add(resign);

        // gameResultOverlay = new GameResultOverlay();

        // Same as chess board, use constants later lol
        // gameResultOverlay.setBounds(120, 120, 480, 480);
    }

    public abstract void processMove(String tile1, String tile2, String promotion);

    private void initialize() {

        chessGame = new ChessGame();

        // Use constants

        // Chess game
        boardPanel = new ChessBoardPanel(chessGame, this);
        boardPanel.setBounds(120, 120, 480, 480);
        this.add(boardPanel);

        // Moves panel
        movesPanel = new MovesPanel();
        movesPanel.setBounds(660, 120, 240, 120);
        this.add(movesPanel, BorderLayout.CENTER);

        // Message panel
        messagePanel = new MessagePanel();
        messagePanel.setBounds(660, 300, 240, 330);
        this.add(messagePanel);
    }

    public void undoMove() {
        this.boardPanel.undoMove();
        this.movesPanel.removeMove();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
        if ((gameState != GameState.WAITING) && (gameState != GameState.ONGOING)) {
            boardPanel.gameResultOverlay.setVisible(true);
        } else {
            boardPanel.gameResultOverlay.setVisible(false);
        }
        boardPanel.revalidate();
    }

    public int getPlayerColour() {
        return this.playerColour;
    }

    public void setPlayerColour(int colour) {
        this.playerColour = colour;
        this.boardPanel.setPlayerColour(colour);
    }

    public void resetPanel() {
        this.remove(boardPanel);
        this.remove(movesPanel);
        this.remove(messagePanel);

        initialize();
        this.revalidate();
    }
}