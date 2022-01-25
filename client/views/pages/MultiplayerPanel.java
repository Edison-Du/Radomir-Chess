package views.pages;

import java.awt.event.ActionEvent;


import config.GameState;
import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import sounds.SoundEffect;

/**
 * [MultiplayerPanel.java]
 * Chess game panel for multiplayer games
 *
 * @author Alex Zhu
 * @version 1.0 Jan 24, 2022
 */
public class MultiplayerPanel extends AbstractGamePanel {

    private String otherClient;
    private boolean isAlone;

    public MultiplayerPanel() {
        setGameState(GameState.WAITING);
    }

    /**
     * setLobbyCode
     * sets the lobby code
     * @param String the lobby code
     */
    public void setLobbyCode(String code) {
        lobbyInfoPanel.setlobbyTitle(code);
    }

    /**
     * setLobbyVisibility
     * sets the lobby privacy/visibility
     * @param String with the lobby visibility (public/private)
     */
    public void setLobbyVisibility(String visibility) {
        lobbyInfoPanel.setlobbyType(visibility);
    }

    /**
     * setClient
     * updates the name of the client
     * @param String name of the client/player
     */
    public void setClient(String clientName) {
        this.playerLabel.setText(clientName);
    }
    
    /**
     * isAlone
     * returns the isAlone variable
     * return boolean whether the player is alone in the lobby
     */
    public boolean isAlone() {
        return isAlone;
    }

    /**
     * setAlone
     * updates the isAlone variable
     * @param boolean if the player is alone
     */
    public void setAlone(boolean isAlone) {
        this.isAlone = isAlone;
    }

    public void addOther(String clientName) {
        this.otherClient = clientName;
        this.opponentLabel.setText(clientName);
        messagePanel.addTextMessage(clientName + " has joined the lobby.");
        setGameState(GameState.ONGOING);
    }

    /**
     * removeOther
     * remove the opponent client
     */
    public void removeOther() {
        this.otherClient = null;
    }

    /**
     * getOpponent
     * get opponent name
     * @return String returns the opponent client's name
     */
    public String getOpponent() {
        return this.otherClient;
    }

    /**
     * addMessageFromOther
     * appends messages sent by opponent to chat
     * @param String the message the opponent sent
     */
    public void addMessageFromOther(String message) {
        messagePanel.addTextMessage(otherClient + ": " + message);
    }

    /**
     * addTakebackRequest
     * if opponent sends take back, create accept/decline button
     */
    public void addTakebackRequest() {
        opponentProposalPanel.setProposalText("Accept takeback?");
        this.add(opponentProposalPanel);
        setActiveProposal(MessageTypes.TAKEBACK_REQUESTED);

        messagePanel.addTextMessage(otherClient + " requests a takeback.");

        this.revalidate();
    }

    /**
     * addDrawOffer
     * if opponent offers a draw, create accept/decline button
     */
    public void addDrawOffer() {
        opponentProposalPanel.setProposalText("Accept draw?");
        this.add(opponentProposalPanel);
        setActiveProposal(MessageTypes.DRAW_OFFERED);

        messagePanel.addTextMessage(otherClient + " offers a draw.");

        this.revalidate();
    }

    /**
     * performTakeback
     * undos move once if opponent turn and twice if your turn
     */
    public void performTakeback() {
        if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
            undoMove();
            undoMove();
        } else {
            undoMove();
        }
    }

    /**
     * processMove
     * sends your move to other player
     * @param String 3 strings: starting tile, ending tile, and whether the move was a promotion
     */
    @Override
    public void processMove(String t1, String t2, String p) {

        SoundEffect.playSound(t1, t2, "", chessGame);
        movesPanel.addMove(chessGame.toAlgebraic(t1, t2, ""));
        chessGame.move(t1, t2, "");

        Message message = new Message(MessageTypes.CHESS_MOVE);
        message.addParam(t1);
        message.addParam(t2);
        message.addParam(p);
        ServerConnection.sendMessage(message);
    }

    /**
     * handleGameEnded
     * Checks/handles the ending of a game (checks who wins)
     */
    public void handleGameEnded() {
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

    /**
     * actionPerformed
     * action listener for game panel
     * @param ActionEvent the event that occured (mouse click)
     */
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

    /**
     * handleLeaveLobbyButton
     * functions to handle the leave lobby button
     */
    public void handleLeaveLobbyButton() {
        Message message = new Message(MessageTypes.LEAVE_GAME);
        ServerConnection.sendMessage(message);
    }

    /**
     * handleTakebackButton
     * function to handle the takeback button
     */
    public void handleTakebackButton() {
        // Make sure the user has made a move
        if (movesPanel.getNumMoves() > getPlayerColour()) {
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));
            messagePanel.addTextMessage("Takeback requested.");
        }
    }

    /**
     * handleDrawButton
     * handles the draw button
     */
    public void handleDrawButton() {
        ServerConnection.sendMessage(new Message(MessageTypes.DRAW_OFFERED));
        messagePanel.addTextMessage("Draw offer sent.");
    }

    /**
     * handleResignButton
     * handles the game resignation
     */
    public void handleResignButton() {
        if (getPlayerColour() == 0) {
            setGameState(GameState.BLACK_VICTORY_RESIGN);
        } else {
            setGameState(GameState.WHITE_VICTORY_RESIGN);
        }

        boardPanel.gameResultOverlay.setMessage("You have resigned");
        ServerConnection.sendMessage(new Message(MessageTypes.RESIGNATION));
    }

    /**
     * handleDrawAcceptance
     * handles the draw acceptance
     */
    public void handleDrawAcceptance() {
        boardPanel.gameResultOverlay.setMessage("Game Drawn");
        ServerConnection.sendMessage(new Message(MessageTypes.DRAW_ACCEPTED));
        setGameState(GameState.DRAW);
    }

    /**
     * handleTakeBackAcceptance
     * handles the take back aceeptance
     */
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