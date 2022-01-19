package views.pages;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import chesslogic.ChessGame;
import config.GameState;
import views.chess.GamePanelButton;
import views.components.ContentPanel;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // Chess game
    public ChessGame chessGame;

    // subpanel chess game
    public ChessBoardPanel boardPanel;
    public MovesPanel movesPanel;
    public MessagePanel messagePanel;

    public GamePanelButton resign;

    private GameState gameState;


    public AbstractGamePanel() {

        initialize();

        // // Leave lobby
        // leaveLobby = new JButton("Leave");
        // leaveLobby.setBounds(780, 630, 120, 30);
        // leaveLobby.addActionListener(this);
        // this.add(leaveLobby);
    }

    public abstract void processMove(String tile1, String tile2, String promotion);

    private void initialize() {
        chessGame = new ChessGame();

        // Chess game
        boardPanel = new ChessBoardPanel(chessGame, this); // sub-panel 1
        // Use constants
        boardPanel.setBounds(120, 120, 480, 480);
        this.add(boardPanel);

        // Moves panel
        movesPanel = new MovesPanel();
        movesPanel.setBounds(660, 120, 240, 120);
        this.add(movesPanel, BorderLayout.CENTER);

        // Message panel
        messagePanel = new MessagePanel();
        // Use constants
        messagePanel.setBounds(660, 300, 240, 330);
        this.add(messagePanel);


        // Buttons
        resign = new GamePanelButton("Resign");
        resign.setBounds(660, 240, 80, 60);
        this.add(resign);
    }

    public void undoMove() {
        this.boardPanel.undoMove();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public void resetPanel() {
        this.remove(boardPanel);
        this.remove(movesPanel);
        this.remove(messagePanel);

        initialize();
        this.revalidate();
    }
}