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

    private final OpponentProposalPanel opponentProposalPanel;
    private String activeProposal;


    public MultiplayerPanel() {

        // CHESS GAME
        setGameState(GameState.WAITING);

        this.hostName = new JLabel();
        this.hostName.setForeground(UserInterface.TEXT_COLOUR);
        this.hostName.setBounds(100, 0, 400, 200);
        this.hostName.setFont(UserInterface.USERNAME_FONT);
        this.add(hostName);

        opponentProposalPanel = new OpponentProposalPanel(this);
        opponentProposalPanel.setBounds(435, 20, 165, 200);
        opponentProposalPanel.setProposalText("Accept draw?");
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        // lobbyLabel.setText(lobbyCode);
        lobbyInfoPanel.setlobbyTitle(code);
    }

    public void setLobbyVisibility(String visibility) {
        this.lobbyVisibility = visibility;

        lobbyInfoPanel.setlobbyType(visibility + " Lobby");
        // this.lobbyLabel.setText(this.lobbyCode + ":  " + visibility + " Lobby");
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


    public String getActiveProposal() {
        return this.activeProposal;
    }

    public void addTakebackRequest() {
        opponentProposalPanel.setProposalText("Accept takeback?");
        this.add(opponentProposalPanel);
        this.activeProposal = MessageTypes.TAKEBACK_REQUESTED;

        messagePanel.addTextMessage(otherClient + " wants a takeback");

        this.revalidate();
    }

    public void addDrawOffer() {
        opponentProposalPanel.setProposalText("Accept draw?");
        this.add(opponentProposalPanel);
        this.activeProposal = MessageTypes.DRAW_OFFERED;
        
        messagePanel.addTextMessage(otherClient + " offers a draw");

        this.revalidate();
    }

    public void performTakeback() {
        System.out.println("TAKEBACK PERFORMED COLOUR: " + getPlayerColour());
        if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
            undoMove();
            undoMove();
        } else {
            undoMove();
        }
    }

    public void removeProposal() {
        this.remove(opponentProposalPanel);
        this.activeProposal = null;
        this.revalidate();
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

        boolean isChecked = true;

        // someone fix this method pls
        // boolean isChecked = chessGame.getCurrentPos().getKings()[chessGame.getCurrentPos().getTurn()].isChecked(chessGame.getCurrentPos(), chessGame.getCurrentPos().getKingTiles()[chessGame.getCurrentPos().getTurn()]);

        if(isChecked) {
            System.out.println("checkmate!");

            setGameState(GameState.CHECKMATE);
            ServerConnection.sendMessage(new Message(MessageTypes.CHECKMATE));
            this.boardPanel.gameResultOverlay.setMessage("Checkmate");
        } else {
            System.out.println("stalemate!");

            setGameState(GameState.STALEMATE);
            ServerConnection.sendMessage(new Message(MessageTypes.STALEMATE));
            this.boardPanel.gameResultOverlay.setMessage("Stalemate");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == leaveLobby) {
            Message message = new Message(MessageTypes.LEAVE_GAME);
            ServerConnection.sendMessage(message);
            return;
        }


        // TODO SPLIT EACH IF STATEMENT INTO IT'S OWN FUNCTION

        if (getGameState() == GameState.ONGOING) {

            // Make sure the player has moved
            if ( (e.getSource() == takebackButton) && (movesPanel.getNumMoves() > 0 + getPlayerColour()) ) {

                ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));
                messagePanel.addTextMessage("Takeback requested.");


            } else if (e.getSource() == drawButton) {
                ServerConnection.sendMessage(new Message(MessageTypes.DRAW_OFFERED));
                messagePanel.addTextMessage("Draw offer sent.");


            } else if (e.getSource() == resignButton) {
                if (getPlayerColour() == 0) {
                    setGameState(GameState.BLACK_VICTORY_RESIGN);
                } else {
                    setGameState(GameState.WHITE_VICTORY_RESIGN);
                }
                boardPanel.gameResultOverlay.setMessage("You have resigned");
                ServerConnection.sendMessage(new Message(MessageTypes.RESIGNATION));
            
            
            } else if (e.getSource() == opponentProposalPanel.acceptButton) {

                if (activeProposal == MessageTypes.DRAW_OFFERED) {
                    boardPanel.gameResultOverlay.setMessage("Game drawn");
                    ServerConnection.sendMessage(new Message(MessageTypes.DRAW_ACCEPTED));
                    setGameState(GameState.DRAW);

                } else if (activeProposal == MessageTypes.TAKEBACK_REQUESTED) {

                    ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_ACCEPTED));
                    System.out.println("TAKEBACK ACCEPTED COLOUR: " + getPlayerColour());

                    // Takeback once if your turn, twice if their turn
                    if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
                        System.out.println("OUR TURN");
                        undoMove();
                    } else {
                        System.out.println("NOT OUR TURN");
                        undoMove();
                        undoMove();
                    }
                }

                removeProposal();

            } else if (e.getSource() == opponentProposalPanel.declineButton) {

                removeProposal();
            }
        }
        this.revalidate();
        this.repaint();
    }
}