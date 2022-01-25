package views.pages;

import java.awt.event.ActionEvent;

import chesslogic.*;
import config.GameState;
import config.Page;
import views.Window;
import views.chess.BotThread;

/**
 * [BotPanel.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class BotPanel extends AbstractGamePanel {

    private final int INITIAL_DEPTH = 1;
    private final String EASY_TEXT = "Jeffrey Bot: Easy";
    private final String MEDIUM_TEXT = "Peter Bot: Medium";
    private final String HARD_TEXT = "Radomir Bot: Hard";
    private final int EASY_DEPTH = 1;
    private final int MEDIUM_DEPTH = 3;
    private final int HARD_DEPTH = 5;
    private final String LOBBY_INFO_PANEL_TEXT = "Bot Game";

    private Bot depthSearchBot;
    private Window window;
    private ChessGame chessGameClone;
    private int depth;

    public BotPanel(Window window) {
        this.window = window;
        this.depth = INITIAL_DEPTH;

        lobbyInfoPanel.setlobbyTitle(LOBBY_INFO_PANEL_TEXT);
        resetGame();
    }

    public void setPlayerName(String name) {
        this.playerLabel.setText(name);
    }

    public void setDepth(int depth){
        this.depth = depth;
        resetGame();
        resetChat();
    }

    @Override
    public void resetGame() {
        
        chessGame = new ChessGame();
        chessGameClone = new ChessGame();

        boardPanel.setChessGame(chessGame);
        movesPanel.clearMoves();

        capturedPiecesPanelBlack.setChessGame(chessGame);
        capturedPiecesPanelWhite.setChessGame(chessGame);

        setPlayerColour((int)(Math.random() * 2));

        depthSearchBot = new RadomirBot(depth, (getPlayerColour() + 1) % 2, 4);
        
        if(depth == EASY_DEPTH) {
            opponentLabel.setText(EASY_TEXT);  

        } else if(depth == MEDIUM_DEPTH) {
            opponentLabel.setText(MEDIUM_TEXT);
            
        } else if(depth == HARD_DEPTH) {
            opponentLabel.setText(HARD_TEXT);
        }

        // Bot goes first
        if (getPlayerColour() == 1) {
            processMove("", "", "");
        }

        setGameState(GameState.ONGOING);

        playerLabel.setText(window.navigationBar.getUsername());

        this.revalidate();
    }
    
    @Override
    public void processMove(String tile1, String tile2, String promotion) {

        if(!chessGame.getCurrentPos().ended()) {

            if(!tile1.equals("")) {
                chessGameClone.move(tile1, tile2, promotion);
            }

            // bot move used to be here
            BotThread newBot = new BotThread(chessGame, chessGameClone, depthSearchBot, movesPanel, this);
            newBot.execute();

        }
    }

    @Override
    public void handleGameEnded() {
        if(chessGame.stalemate()) {
            setGameState(GameState.STALEMATE);
            this.boardPanel.gameResultOverlay.setMessage("Stalemate");

        } else if (chessGame.whiteWins()){
            setGameState(GameState.WHITE_VICTORY_CHECKMATE);
            this.boardPanel.gameResultOverlay.setMessage("White wins by checkmate");
        
        } else if (chessGame.blackWins()) {
            setGameState(GameState.BLACK_VICTORY_CHECKMATE);
            this.boardPanel.gameResultOverlay.setMessage("Black wins by checkmate");
        }
        setOpponentPlayAgain(true);
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
            
            }
        }
        this.revalidate();
        this.repaint();
    }

    // Functions to handle each button when clicked
    public void handleLeaveLobbyButton() {
        window.setInBotGame(false);
        window.changePage(Page.PLAY);
        resetGame();
    }


    public void handleTakebackButton() {
        // Make sure the user has made a move
        if (movesPanel.getNumMoves() > getPlayerColour()) {
            synchronized(chessGame) {
                synchronized(chessGameClone) {
                    if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
                        undoMove();
                        undoMove();
                        chessGameClone.undo();
                        chessGameClone.undo();
                    } else {
                        undoMove();
                        chessGameClone.undo();
                    }
                }
            }
        }
    }


    public void handleDrawButton() {
        setGameState(GameState.DRAW);
        setOpponentPlayAgain(true); // Bot always plays again
        boardPanel.gameResultOverlay.setMessage("Game Drawn");
    }


    public void handleResignButton() {
        if (getPlayerColour() == 0) {
            setGameState(GameState.BLACK_VICTORY_RESIGN);
        } else {
            setGameState(GameState.WHITE_VICTORY_RESIGN);
        }

        setOpponentPlayAgain(true); // Bot always plays again
        boardPanel.gameResultOverlay.setMessage("You have resigned");
    }

}