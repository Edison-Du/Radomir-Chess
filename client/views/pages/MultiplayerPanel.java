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

        // Lobby code
        // lobbyLabel = new JLabel(lobbyCode);
        // lobbyLabel.setFont(new Font("Serif", Font.BOLD, 20));
        // lobbyLabel.setForeground(Color.WHITE);
        // lobbyLabel.setBounds(660, 10, 500, 100);
        // this.add(lobbyLabel);

        // Showing lobby status (who is in and not)
        // otherClientLabel = new JLabel("You are alone in this lobby.");
        // otherClientLabel.setFont(new Font("Serif", Font.BOLD, 20));
        // otherClientLabel.setForeground(Color.WHITE);
        // otherClientLabel.setBounds(660, 42, 500, 100);
        // this.add(otherClientLabel);

        // Yikes
        // takebackButton = new CustomButton("Takeback");
        // takebackButton.setBounds(0, 600, 150, 25);
        // takebackButton.addActionListener(this);
        // this.add(takebackButton);

        this.hostName = new JLabel();
        this.hostName.setForeground(UserInterface.TEXT_COLOUR);
        this.hostName.setBounds(100, 0, 400, 200);
        this.hostName.setFont(UserInterface.USERNAME_FONT);
        this.add(hostName);
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

        // if (isHost) {
        //     otherClientLabel.setText(clientName + " is in this lobby.");
        // } else {
        //     otherClientLabel.setText(clientName + " is the host of this lobby.");
        // }

        setGameState(GameState.ONGOING);
    }

    // Text
    public void addMessageFromOther(String message) {
        messagePanel.addTextMessage(otherClient + ": " + message);
    }

    public void addTakeback() {
        this.add(takebackAcceptButton);
    }

    public void removeTakeback() {
        this.remove(takebackAcceptButton);
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
        System.out.println(getGameState());
        if (e.getSource() == takebackButton) {
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_ACCEPTED));
            this.undoMove();
            removeTakeback();

        } else if (e.getSource() == takebackButton){
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));

        } else if ((e.getSource() == resignButton) && (getGameState() == GameState.ONGOING)) {
            if (getPlayerColour() == 0) {
                setGameState(GameState.BLACK_VICTORY_RESIGN);
            } else {
                setGameState(GameState.WHITE_VICTORY_RESIGN);
            }
            boardPanel.gameResultOverlay.setMessage("You have resigned");
            ServerConnection.sendMessage(new Message(MessageTypes.RESIGNATION));

        } else if (e.getSource() == leaveLobby) {
            Message message = new Message(MessageTypes.LEAVE_GAME);
            ServerConnection.sendMessage(message);
        }
        this.revalidate();
        this.repaint();
    }
}