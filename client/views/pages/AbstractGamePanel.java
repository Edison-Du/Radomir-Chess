package views.pages;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import chesslogic.ChessGame;
import config.GameState;
import config.MessageTypes;
import views.chess.ChessBoardPanel;
import views.chess.GamePanelButton;
import views.chess.GameResultOverlay;
import views.chess.MessagePanel;
import views.chess.MovesPanel;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomButton;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // Chess game
    public ChessGame chessGame;

    // subpanel chess game
    public ChessBoardPanel boardPanel;
    public MovesPanel movesPanel;
    public MessagePanel messagePanel;

    private boolean playAgain;
    private boolean opponentPlayAgain;

    private int playerColour;
    private GameState gameState;

    public final GamePanelButton resign;
    public CustomButton undoButton;
    public CustomButton takebackButton;

    public JLabel hostName, enemyName;

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
        
        // Enter into game
        if (gameState == GameState.WAITING && state != GameState.WAITING) {
            ServerConnection.sendMessage(new Message(MessageTypes.LOCK_LOBBY));

        // Reverse above statement
        } else if (gameState != GameState.WAITING && state == GameState.WAITING) {
            ServerConnection.sendMessage(new Message(MessageTypes.UNLOCK_LOBBY));
        }

        if ((state != GameState.WAITING) && (state != GameState.ONGOING)) {
            boardPanel.setOverlayVisible(true);
        } else {
            playAgain = false;
            opponentPlayAgain = false;
            boardPanel.setOverlayVisible(false);
        }
        this.gameState = state;
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

    public boolean isPlayingAgain() {
        return playAgain;
    }

    public boolean opponentPlayingAgain() {
        return opponentPlayAgain;
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

    public abstract void handleGameEnded();
}