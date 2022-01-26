package views.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import chesslogic.ChessConsts;
import chesslogic.ChessGame;
import config.UserInterface;
import config.GameState;
import config.MessageTypes;
import views.chess.CapturedPiecesPanel;
import views.chess.ChessBoardPanel;
import views.chess.GamePanelButton;
import views.chess.LobbyInfoPanel;
import views.chess.MessagePanel;
import views.chess.MovesPanel;
import views.chess.OpponentProposalPanel;
import views.chess.PlayerLabel;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomButton;

/**
 * [AbstractGamePanel.java]
 * Abstract game panel for the other game panels to extend
 * 
 * @author Edison Du
 * @author Peter Gu
 * @author Alex Zhu
 * @version 1.0 Jan 24, 2022
 */
abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // UI Constants
    private final Rectangle MOVES_PANEL_BOUNDS = new Rectangle(660, 120, 240, 120);
    private final Rectangle CHAT_PANEL_BOUNDS = new Rectangle(660, 300, 240, 330);
    private final Rectangle BOARD_PANEL_BOUNDS = new Rectangle(660, 30, 240, 60);
    private final Rectangle OPPONENT_LABEL_BOUNDS = new Rectangle(
        UserInterface.GAME_BOARD_X,
        UserInterface.GAME_BOARD_Y - 80,
        UserInterface.GAME_BOARD_LENGTH,
        60);
    private final Rectangle PLAYER_LABEL_BOUNDS = new Rectangle(
        UserInterface.GAME_BOARD_X,
        UserInterface.GAME_BOARD_Y + UserInterface.GAME_BOARD_LENGTH + 10,
        UserInterface.GAME_BOARD_LENGTH,
        60);
    private final Rectangle OPPONENT_CAPTURED_PIECES_BOUNDS = new Rectangle(120, 80,480,30);
    private final Rectangle PLAYER_CAPTURED_PIECES_BOUNDS = new Rectangle(120,650,480,30);
    private final Rectangle TAKEBACK_BOUNDS = new Rectangle(660, 239, 80, 62);
    private final Rectangle DRAW_BUTTON_BOUNDS = new Rectangle(740, 239, 80, 62);
    private final Rectangle RESIGN_BOUNDS = new Rectangle(820, 239, 80, 62);
    private final Rectangle PROPOSAL_BOUNDS = new Rectangle(435, 20, 165, 200);
    private final Rectangle LEAVE_BUTTON_BOUNDS = new Rectangle(660, 630, 240, 30);

    // Panels
    public final ChessBoardPanel boardPanel;
    public final MovesPanel movesPanel;
    public final MessagePanel messagePanel;
    public final LobbyInfoPanel lobbyInfoPanel;

    // Labels for player and opponent
    public final PlayerLabel playerLabel;
    public final PlayerLabel opponentLabel;

    // Buttons
    public final GamePanelButton drawButton;
    public final GamePanelButton resignButton;
    public final GamePanelButton takebackButton;
    public final CustomButton leaveLobby;
    public final CapturedPiecesPanel capturedPiecesPanelWhite;
    public final CapturedPiecesPanel capturedPiecesPanelBlack;
    public final OpponentProposalPanel opponentProposalPanel;

    // Chess game
    private boolean playAgain;
    private boolean opponentPlayAgain;
    private int playerColour;
    private GameState gameState;
    private String activeProposal;

    public ChessGame chessGame;

    /**
     * AbstractGamePanel
     * Creates the game panel with a chess game, a chat box,
     * a move history and appropriate player labels
     */
    public AbstractGamePanel() {  
        // Chess game and board
        this.chessGame = new ChessGame();
        this.boardPanel = new ChessBoardPanel(chessGame, this);
        boardPanel.setBounds(
            UserInterface.GAME_BOARD_X, 
            UserInterface.GAME_BOARD_Y, 
            UserInterface.GAME_BOARD_LENGTH, 
            UserInterface.GAME_BOARD_LENGTH
        );
        this.add(boardPanel);

        // Captured pieces
        this.capturedPiecesPanelWhite = new CapturedPiecesPanel(chessGame, 0);
        this.add(capturedPiecesPanelWhite);

        this.capturedPiecesPanelBlack = new CapturedPiecesPanel(chessGame, 1);
        this.add(capturedPiecesPanelBlack);

        // Moves
        this.movesPanel = new MovesPanel();
        movesPanel.setBounds(MOVES_PANEL_BOUNDS);
        this.add(movesPanel, BorderLayout.CENTER);

        // Chat
        this.messagePanel = new MessagePanel();
        messagePanel.setBounds(CHAT_PANEL_BOUNDS);
        this.add(messagePanel);

        // Lobby Info
        this.lobbyInfoPanel = new LobbyInfoPanel();
        lobbyInfoPanel.setBounds(BOARD_PANEL_BOUNDS);
        lobbyInfoPanel.setForeground(UserInterface.NAVBAR_COLOUR);
        lobbyInfoPanel.setBackground(Color.WHITE);
        this.add(lobbyInfoPanel);

        // Opponent label
        this.opponentLabel = new PlayerLabel();
        opponentLabel.setBounds(OPPONENT_LABEL_BOUNDS);
        this.add(opponentLabel);

        // Player label
        this.playerLabel = new PlayerLabel();
        playerLabel.setBounds(PLAYER_LABEL_BOUNDS);
        this.add(playerLabel);

        // Takeback
        this.takebackButton = new GamePanelButton("Takeback");
        takebackButton.setBounds(TAKEBACK_BOUNDS);
        takebackButton.addActionListener(this);
        this.add(takebackButton);

        // Draw
        this.drawButton = new GamePanelButton("Draw");
        drawButton.setBounds(DRAW_BUTTON_BOUNDS);
        drawButton.addActionListener(this);
        this.add(drawButton);

        // Resign
        this.resignButton = new GamePanelButton("Resign");
        resignButton.setBounds(RESIGN_BOUNDS);
        resignButton.addActionListener(this);
        this.add(resignButton);

        // Leave Lobby
        this.leaveLobby = new CustomButton("Leave Game");
        leaveLobby.setFont(UserInterface.orkney18);
        leaveLobby.setBorder(UserInterface.FONT_OFFSET_BORDER);
        leaveLobby.setBounds(LEAVE_BUTTON_BOUNDS);
        leaveLobby.setRound(true);
        leaveLobby.setBorderRadius(UserInterface.GAME_INFO_BORDER_RADIUS);
        leaveLobby.setForeground(UserInterface.NAVBAR_COLOUR);
        leaveLobby.setBackground(Color.WHITE);
        leaveLobby.setHoverColor(UserInterface.CHAT_MESSAGE_COLOUR);
        leaveLobby.setPressedColor(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);

        // Proposal Panel
        this.opponentProposalPanel = new OpponentProposalPanel(this);
        opponentProposalPanel.setBounds(PROPOSAL_BOUNDS);
    }

    /**
     * undoMove
     * Undo a move from the chess game and remove it from the moves panel
     */
    public void undoMove() {
        this.boardPanel.undoMove();
        this.movesPanel.removeMove();
    }

    /**
     * getGameState
     * Getter for the state of the game
     * @return the state of the game
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * setGameState
     * Sets the state of the game
     * @param state the state of the game
     */
    public void setGameState(GameState state) {

        // The game is no longer in the waiting stage, lock the lobby so others cannot join
        if (gameState == GameState.WAITING && state != GameState.WAITING) {
            ServerConnection.sendMessage(new Message(MessageTypes.LOCK_LOBBY));

        // Game is in waiting stage, open the lobby and allow others to join
        } else if (gameState != GameState.WAITING && state == GameState.WAITING) {
            ServerConnection.sendMessage(new Message(MessageTypes.UNLOCK_LOBBY));
            opponentLabel.setText("Waiting for opponent . . .");
        }

        // Show game over screen if the game has ended
        if ((state != GameState.WAITING) && (state != GameState.ONGOING)) {
            boardPanel.setOverlayVisible(true);
            removeProposal();

        } else {
            playAgain = false;
            opponentPlayAgain = false;
            boardPanel.setOverlayVisible(false);
        }

        this.gameState = state;
        boardPanel.revalidate();
    }

    /**
     * getActiveProposal
     * Getter for the type of proposal active (draw/takeback)
     * @return the type of proposal active
     */
    public String getActiveProposal() {
        return this.activeProposal;
    }

    /**
     * setActiveProposal
     * Sets the type of proposal active
     * @param proposal the type of proposal active
     */
    public void setActiveProposal(String proposal) {
        this.activeProposal = proposal;
    }

    /**
     * removeProposal
     * Remove the proposal button if takeback or draw was rejected
     */
    public void removeProposal() {
        this.remove(opponentProposalPanel);
        setActiveProposal(null);
        this.revalidate();
    }

    /**
     * getPlayerColour
     * Gets the colour the player is playing as
     */
    public int getPlayerColour() {
        return this.playerColour;
    }

    /**
     * setPlayerColour
     * Sets the player colour and rearrange the board accordingly
     * @param colour the player colour
     */
    public void setPlayerColour(int colour) {
        this.playerColour = colour;

        if (playerColour == ChessConsts.WHITE)  {
            capturedPiecesPanelWhite.setBounds(OPPONENT_CAPTURED_PIECES_BOUNDS);
            capturedPiecesPanelBlack.setBounds(PLAYER_CAPTURED_PIECES_BOUNDS);
        } else {
            capturedPiecesPanelWhite.setBounds(PLAYER_CAPTURED_PIECES_BOUNDS);
            capturedPiecesPanelBlack.setBounds(OPPONENT_CAPTURED_PIECES_BOUNDS);
        }
        this.revalidate();
    }

    /**
     * resetGame
     * Reset the chess game, runs before any chess game starts
     */
    public void resetGame() {
        chessGame = new ChessGame();
        boardPanel.setChessGame(chessGame);
        movesPanel.clearMoves();
        capturedPiecesPanelBlack.setChessGame(chessGame);
        capturedPiecesPanelWhite.setChessGame(chessGame);
        this.revalidate();
    }

    /**
     * resetChat
     * Clear the chat
     */
    public void resetChat() {
        messagePanel.clearMessages();
        this.revalidate();
    }

    /**
     * isPlayingAgain
     * @return boolean whether the player is playing again
     */
    public boolean isPlayingAgain() {
        return playAgain;
    }

    /**
     * opponentPlayingAgain
     * Checks whether or not the opponent has chosen to play again
     * @return whether or not the opponent is playing again
     */
    public boolean opponentPlayingAgain() {
        return opponentPlayAgain;
    }

    /**
     * setPlayAgain
     * Set boolean if this player wants to play again
     * If both players agree to rematch, create a new rematch game
     * @param opponentPlayAgain whether this user wants to play again
     */
    public void setPlayAgain(boolean playAgain) {
        this.playAgain = playAgain;
        if (playAgain && opponentPlayAgain) {
            setGameState(GameState.ONGOING);
            swapColours();
            resetGame();
        }
    }

    /**
     * setOpponentPlayAgain
     * Set boolean if opponent wants to play again
     * If both players agree to rematch, create a new rematch game
     * @param opponentPlayAgain whether the opponent wants to play again
     */
    public void setOpponentPlayAgain(boolean opponentPlayAgain) {
        this.opponentPlayAgain = opponentPlayAgain;
        if (playAgain && opponentPlayAgain) {
            setGameState(GameState.ONGOING);
            swapColours();
            resetGame();
        }
    }

    /**
     * swapColours
     * Swap colours of the players
     */
    public void swapColours() {
        setPlayerColour((playerColour + 1) % 2);

        // Let server know we swapped colours
        Message updateColour = new Message(MessageTypes.PLAYER_COLOUR);
        updateColour.addParam(Integer.toString(playerColour));
        ServerConnection.sendMessage(updateColour);
    }

    // Abstract methods for handling the game ending
    public abstract void handleGameEnded();

    // Abstract method for processing player moves
    public abstract void processMove(String tile1, String tile2, String promotion);
}