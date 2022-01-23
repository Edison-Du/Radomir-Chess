package views.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.xml.namespace.QName;

import chesslogic.ChessGame;
import config.GameState;
import config.MessageTypes;
import views.chess.CapturedPiecesPanel;
import views.chess.ChessBoardPanel;
import views.chess.GamePanelButton;
import views.chess.GameResultOverlay;
import views.chess.LobbyInfoPanel;
import views.chess.MessagePanel;
import views.chess.MovesPanel;
import views.chess.OpponentProposalPanel;
import views.chess.PlayerLabelPanel;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomButton;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // Chess game
    public ChessGame chessGame;

    // Panels
    public final ChessBoardPanel boardPanel;
    public final MovesPanel movesPanel;
    public final MessagePanel messagePanel;

    public final LobbyInfoPanel lobbyInfoPanel;

    // Labels for player and opponent
    public final PlayerLabelPanel playerLabel;
    public final PlayerLabelPanel opponentLabel;

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
        movesPanel.setBounds(660, 120, 240, 120);
        this.add(movesPanel, BorderLayout.CENTER);

        // Chat
        messagePanel = new MessagePanel();
        messagePanel.setBounds(660, 300, 240, 330);
        this.add(messagePanel);

        // Lobby Info
        lobbyInfoPanel = new LobbyInfoPanel();
        lobbyInfoPanel.setBounds(660, 30, 240, 60);
        lobbyInfoPanel.setForeground(UserInterface.NAVBAR_COLOUR);
        lobbyInfoPanel.setBackground(Color.WHITE);
        this.add(lobbyInfoPanel);

        // Opponent label
        opponentLabel = new PlayerLabelPanel();
        opponentLabel.setBounds(
            //change
            UserInterface.GAME_BOARD_X,
            UserInterface.GAME_BOARD_Y - 40,
            UserInterface.GAME_BOARD_LENGTH/2,
            60
        );
        this.add(opponentLabel);

        // Player label
        playerLabel = new PlayerLabelPanel();
        playerLabel.setBounds(
            UserInterface.GAME_BOARD_X,
            UserInterface.GAME_BOARD_Y + UserInterface.GAME_BOARD_LENGTH + 10,
            UserInterface.GAME_BOARD_LENGTH/2,
            60
        );
        this.add(playerLabel);

        // Takeback
        takebackButton = new GamePanelButton("Takeback");
        takebackButton.setBounds(660, 240 - 1, 80, 60 + 2);
        takebackButton.addActionListener(this);
        this.add(takebackButton);

        // Draw
        drawButton = new GamePanelButton("Draw");
        drawButton.setBounds(740, 240 - 1, 80, 60 + 2);
        drawButton.addActionListener(this);
        this.add(drawButton);

        // Resign
        resignButton = new GamePanelButton("Resign");
        resignButton.setBounds(820, 240 - 1, 80, 60 + 2);
        resignButton.addActionListener(this);
        this.add(resignButton);

        // Leave Lobby
        leaveLobby = new CustomButton("Leave Game");
        leaveLobby.setBounds(660, 630, 240, 30);
        leaveLobby.setBorder(UserInterface.EMPTY_BORDER);
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