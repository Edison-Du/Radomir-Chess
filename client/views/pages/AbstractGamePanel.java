package views.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import javax.swing.JLabel;

import chesslogic.ChessGame;
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
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomButton;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    private final Rectangle MOVES_PANEL_BOUNDS = new Rectangle(660, 120, 240, 120);
    private final Rectangle CHAT_PANEL_BOUNDS = new Rectangle(660, 300, 240, 330);
    private final Rectangle BOARD_PANEL_BOUNDS = new Rectangle(660, 30, 240, 60);

    // Chess game
    public ChessGame chessGame;

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

    private boolean playAgain;
    private boolean opponentPlayAgain;

    public final CapturedPiecesPanel capturedPiecesPanelWhite;
    public final CapturedPiecesPanel capturedPiecesPanelBlack;

    private Rectangle opponentCapturedPiecesBounds;
    private Rectangle playerCapturedPiecesBounds;

    private int playerColour;
    private GameState gameState;

    public final OpponentProposalPanel opponentProposalPanel;
    private String activeProposal;

    public JLabel hostName, opponentName;

    public AbstractGamePanel() {
        
        // Chess game and board
        chessGame = new ChessGame();
        boardPanel = new ChessBoardPanel(chessGame, this);
        boardPanel.setBounds(
            UserInterface.GAME_BOARD_X, 
            UserInterface.GAME_BOARD_Y, 
            UserInterface.GAME_BOARD_LENGTH, 
            UserInterface.GAME_BOARD_LENGTH
        );
        this.add(boardPanel);

        // Captured Pieces
        opponentCapturedPiecesBounds = new Rectangle(120, 60,480,30);
        playerCapturedPiecesBounds   = new Rectangle(120,600,480,30);


        capturedPiecesPanelWhite = new CapturedPiecesPanel(chessGame, 0);
        this.add(capturedPiecesPanelWhite);

        capturedPiecesPanelBlack = new CapturedPiecesPanel(chessGame, 1);
        this.add(capturedPiecesPanelBlack);

        // Moves
        movesPanel = new MovesPanel();
        movesPanel.setBounds(MOVES_PANEL_BOUNDS);
        this.add(movesPanel, BorderLayout.CENTER);

        // Chat
        messagePanel = new MessagePanel();
        messagePanel.setBounds(CHAT_PANEL_BOUNDS);
        this.add(messagePanel);

        // Lobby Info
        lobbyInfoPanel = new LobbyInfoPanel();
        lobbyInfoPanel.setBounds(BOARD_PANEL_BOUNDS);
        lobbyInfoPanel.setForeground(UserInterface.NAVBAR_COLOUR);
        lobbyInfoPanel.setBackground(Color.WHITE);
        this.add(lobbyInfoPanel);

        // Opponent label
        opponentLabel = new PlayerLabel();
        opponentLabel.setBounds(
            UserInterface.GAME_BOARD_X,
            UserInterface.OPPONENT_LABEL_Y,
            UserInterface.GAME_BOARD_LENGTH,
            UserInterface.PLAYERS_LABEL_HEIGHT
        );
        this.add(opponentLabel);

        // Player label
        playerLabel = new PlayerLabel();
        playerLabel.setBounds(
            UserInterface.GAME_BOARD_X,
            UserInterface.PLAYER_LABEL_Y,
            UserInterface.PLAYERS_LABEL_WIDTH,
            UserInterface.PLAYERS_LABEL_HEIGHT
        );
        this.add(playerLabel);

        // Takeback
        takebackButton = new GamePanelButton("Takeback");
        takebackButton.setBounds(
            UserInterface.TAKEBACK_BUTTON_X,
            UserInterface.GAME_BUTTON_Y,
            UserInterface.GAME_BUTTON_WIDTH,
            UserInterface.GAME_BUTTON_HEIGHT);
        takebackButton.addActionListener(this);
        this.add(takebackButton);

        // Draw
        drawButton = new GamePanelButton("Draw");
        drawButton.setBounds(
            UserInterface.DRAW_BUTTON_X,
            UserInterface.GAME_BUTTON_Y,
            UserInterface.GAME_BUTTON_WIDTH,
            UserInterface.GAME_BUTTON_HEIGHT);
        drawButton.addActionListener(this);
        this.add(drawButton);

        // Resign
        resignButton = new GamePanelButton("Resign");
        resignButton.setBounds(
            UserInterface.RESIGN_BUTTON_X,
            UserInterface.GAME_BUTTON_Y,
            UserInterface.GAME_BUTTON_WIDTH,
            UserInterface.GAME_BUTTON_HEIGHT);
        resignButton.addActionListener(this);
        this.add(resignButton);

        // Leave Lobby
        leaveLobby = new CustomButton("Leave Game");
        leaveLobby.setFont(UserInterface.orkney18);
        leaveLobby.setBorder(UserInterface.FONT_OFFSET_BORDER);
        leaveLobby.setBounds(660, 630, 240, 30);
        leaveLobby.setRound(true);
        leaveLobby.setBorderRadius(UserInterface.GAME_INFO_BORDER_RADIUS);
        leaveLobby.setForeground(UserInterface.NAVBAR_COLOUR);
        leaveLobby.setBackground(Color.WHITE);
        leaveLobby.setHoverColor(UserInterface.CHAT_MESSAGE_COLOUR);
        leaveLobby.setPressedColor(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);


        // Proposal Panel
        opponentProposalPanel = new OpponentProposalPanel(this);
        opponentProposalPanel.setBounds(435, 20, 165, 200);
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

        // Reverse above statement (leaving game into waiting room)
        } else if (gameState != GameState.WAITING && state == GameState.WAITING) {
            ServerConnection.sendMessage(new Message(MessageTypes.UNLOCK_LOBBY));

            opponentLabel.setText("Waiting for opponent");
        }

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


    public String getActiveProposal() {
        return this.activeProposal;
    }

    public void setActiveProposal(String proposal) {
        this.activeProposal = proposal;
    }

    public void removeProposal() {
        this.remove(opponentProposalPanel);
        setActiveProposal(null);
        this.revalidate();
    }

    public int getPlayerColour() {
        return this.playerColour;
    }

    public void setPlayerColour(int colour) {
        this.playerColour = colour;

        if (playerColour == 0)  {
            capturedPiecesPanelWhite.setBounds(opponentCapturedPiecesBounds);
            capturedPiecesPanelBlack.setBounds(playerCapturedPiecesBounds);
        } else {
            capturedPiecesPanelWhite.setBounds(playerCapturedPiecesBounds);
            capturedPiecesPanelBlack.setBounds(opponentCapturedPiecesBounds);
        }
        this.revalidate();
    }

    public void resetGame() {
        chessGame = new ChessGame();
        boardPanel.setChessGame(chessGame);
        movesPanel.clearMoves();
        capturedPiecesPanelBlack.setChessGame(chessGame);
        capturedPiecesPanelWhite.setChessGame(chessGame);
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
            setGameState(GameState.ONGOING);
            swapColours();
            resetGame();
        }
    }

    public void setOpponentPlayAgain(boolean opponentPlayAgain) {
        this.opponentPlayAgain = opponentPlayAgain;
        if (playAgain && opponentPlayAgain) {
            setGameState(GameState.ONGOING);
            swapColours();
            resetGame();
        }
    }

    public void swapColours() {
        setPlayerColour((playerColour + 1) % 2);

        // Let server know we swapped colours
        Message updateColour = new Message(MessageTypes.PLAYER_COLOUR);
        updateColour.addParam(Integer.toString(playerColour));
        ServerConnection.sendMessage(updateColour);
    }

    public abstract void handleGameEnded();

}