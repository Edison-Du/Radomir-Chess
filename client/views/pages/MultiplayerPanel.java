package views.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import config.GameState;
import config.MessageTypes;
import config.UserInterface;
import network.Lobby;
import network.Message;
import network.ServerConnection;
import views.chess.OpponentProposalPanel;
import views.components.CustomButton;

public class MultiplayerPanel extends AbstractGamePanel {

    private String lobbyCode = "";
    private String lobbyVisibility = "";
    private boolean isHost;

    private String currentClient, otherClient;
    private boolean isAlone;

    // Swing
    private JLabel lobbyLabel;
    private JLabel otherClientLabel;


    public MultiplayerPanel() {

        // CHESS GAME
        setGameState(GameState.WAITING);

        this.hostName = new JLabel();
        this.hostName.setForeground(UserInterface.TEXT_COLOUR);
        this.hostName.setBounds(100, 0, 400, 200);
        this.hostName.setFont(UserInterface.USERNAME_FONT);
        this.add(hostName);

        // opponentProposalPanel = new OpponentProposalPanel(this);
        // opponentProposalPanel.setBounds(435, 20, 165, 200);
        // opponentProposalPanel.setProposalText("Accept draw?");
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        // lobbyLabel.setText(lobbyCode);
        lobbyInfoPanel.setlobbyTitle(code);
    }

    public void setLobbyVisibility(String visibility) {
        this.lobbyVisibility = visibility;

        lobbyInfoPanel.setlobbyType(visibility + " Lobby");
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    public void setClient(String clientName) {
        this.currentClient = clientName;


        this.playerLabel.setText(clientName);
    }

    public boolean isAlone() {
        return isAlone;
    }

    public void setAlone(boolean isAlone) {
        this.isAlone = isAlone;
    }

    // Not sure if this will be redisgned when we get to chess
    // For texting purposes this works.
    public void addOther(String clientName) {
        this.otherClient = clientName;

        this.opponentLabel.setText(clientName);

        messagePanel.addTextMessage(clientName + " has joined the lobby.");

        setGameState(GameState.ONGOING);
    }

    // Text
    public void addMessageFromOther(String message) {
        messagePanel.addTextMessage(otherClient + ": " + message);
    }




    public void addTakebackRequest() {
        opponentProposalPanel.setProposalText("Accept takeback?");
        this.add(opponentProposalPanel);
        setActiveProposal(MessageTypes.TAKEBACK_REQUESTED);

        messagePanel.addTextMessage(otherClient + " wants a takeback");

        this.revalidate();
    }

    public void addDrawOffer() {
        opponentProposalPanel.setProposalText("Accept draw?");
        this.add(opponentProposalPanel);
        setActiveProposal(MessageTypes.DRAW_OFFERED);

        messagePanel.addTextMessage(otherClient + " offers a draw");

        this.revalidate();
    }

    public void performTakeback() {
        if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
            undoMove();
            undoMove();
        } else {
            undoMove();
        }
    }

    @Override
    public void processMove(String t1, String t2, String p) {
        Message message = new Message(MessageTypes.CHESS_MOVE);
        message.addParam(t1);
        message.addParam(t2);
        message.addParam(p);
        ServerConnection.sendMessage(message);
    }

    // handle end of game
    public void handleGameEnded() {
        System.out.println("game ended!");

        if(chessGame.stalemate()) {
            setGameState(GameState.STALEMATE);
            ServerConnection.sendMessage(new Message(MessageTypes.STALEMATE));
            this.boardPanel.gameResultOverlay.setMessage("Stalemate");

        } else if (chessGame.whiteWins()){
            setGameState(GameState.WHITE_VICTORY_CHECKMATE);
            ServerConnection.sendMessage(new Message(MessageTypes.WHITE_VICTORY_CHECKMATE));
            this.boardPanel.gameResultOverlay.setMessage("White wins by checkmate");
        
        } else if (chessGame.blackWins()) {
            setGameState(GameState.BLACK_VICTORY_CHECKMATE);
            ServerConnection.sendMessage(new Message(MessageTypes.BLACK_VICTORY_CHECKMATE));
            this.boardPanel.gameResultOverlay.setMessage("Black wins by checkmate");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == leaveLobby) {
            handleLeaveLobbyButton();
            return;
        }

        if (getGameState() == GameState.ONGOING) {

            if (e.getSource() == takebackButton) {
                handleTakebackButton();

            } else if (e.getSource() == drawButton) {
                handleDrawButton();

            } else if (e.getSource() == resignButton) {
                handleResignButton();
            
            } else if (e.getSource() == opponentProposalPanel.acceptButton) {

                if (getActiveProposal().equals(MessageTypes.DRAW_OFFERED)) {
                    handleDrawAcceptance();

                } else if (getActiveProposal().equals(MessageTypes.TAKEBACK_REQUESTED)) {
                    handleTakeBackAcceptance();
                }

                removeProposal();

            } else if (e.getSource() == opponentProposalPanel.declineButton){
                removeProposal();
            }
        }
        this.revalidate();
        this.repaint();
    }

    // Functions to handle each button when clicked
    public void handleLeaveLobbyButton() {
        Message message = new Message(MessageTypes.LEAVE_GAME);
        ServerConnection.sendMessage(message);
    }


    public void handleTakebackButton() {
        // Make sure the user has made a move
        if (movesPanel.getNumMoves() > getPlayerColour()) {
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));
            messagePanel.addTextMessage("Takeback requested.");
        }
    }


    public void handleDrawButton() {
        ServerConnection.sendMessage(new Message(MessageTypes.DRAW_OFFERED));
        messagePanel.addTextMessage("Draw offer sent.");
    }


    public void handleResignButton() {
        if (getPlayerColour() == 0) {
            setGameState(GameState.BLACK_VICTORY_RESIGN);
        } else {
            setGameState(GameState.WHITE_VICTORY_RESIGN);
        }

        boardPanel.gameResultOverlay.setMessage("You have resigned");
        ServerConnection.sendMessage(new Message(MessageTypes.RESIGNATION));
    }


    public void handleDrawAcceptance() {
        boardPanel.gameResultOverlay.setMessage("Game drawn");
        ServerConnection.sendMessage(new Message(MessageTypes.DRAW_ACCEPTED));
        setGameState(GameState.DRAW);
    }


    public void handleTakeBackAcceptance() {
        ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_ACCEPTED));

        // Takeback once if your turn, twice if their turn
        if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
            undoMove();
        } else {
            undoMove();
            undoMove();
        }
    }
}