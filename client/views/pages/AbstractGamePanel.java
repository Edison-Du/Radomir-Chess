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

    public final GamePanelButton resign;
    
    private boolean playAgain;
    private boolean opponentPlayAgain;

    private int playerColour;
    private GameState gameState;


    public AbstractGamePanel() {
        
        // Chess game and board
        chessGame = new ChessGame();
        boardPanel = new ChessBoardPanel(chessGame, this);
        boardPanel.setBounds(120, 120, 480, 480);
        this.add(boardPanel);

        // Moves
        movesPanel = new MovesPanel();
        movesPanel.setBounds(660, 120, 240, 120);
        this.add(movesPanel, BorderLayout.CENTER);

        // Chat
        messagePanel = new MessagePanel();
        messagePanel.setBounds(660, 300, 240, 330);
        this.add(messagePanel);


        resign = new GamePanelButton("Resign");
        resign.setBounds(660, 240, 80, 60);
        resign.addActionListener(this);
        this.add(resign);

        // gameResultOverlay = new GameResultOverlay();

        // Same as chess board, use constants later lol
        // gameResultOverlay.setBounds(120, 120, 480, 480);
    }

    public abstract void processMove(String tile1, String tile2, String promotion);

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
            // boardPanel.gameResultOverlay.setVisible(true);
            boardPanel.setOverlayVisible(true);
        } else {
            // boardPanel.gameResultOverlay.setVisible(false);
            playAgain = false;
            opponentPlayAgain = false;
            boardPanel.setOverlayVisible(false);
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

    public void resetGame() {

        chessGame = new ChessGame();

        boardPanel.setChessGame(chessGame);

        boardPanel.setPlayerColour(playerColour);

        movesPanel.clearMoves();

        this.revalidate();
    }

    public void resetChat() {
        messagePanel.clearMessages();
        this.revalidate();
    }



    public void setPlayAgain(boolean playAgain) {
        this.playAgain = playAgain;
        if (playAgain && opponentPlayAgain) {
            resetGame();
            setGameState(GameState.ONGOING);
        }
    }

    public void setOpponentPlayAgain(boolean opponentPlayAgain) {
        this.opponentPlayAgain = opponentPlayAgain;
        if (playAgain && opponentPlayAgain) {
            resetGame();
            setGameState(GameState.ONGOING);
        }
    }
}