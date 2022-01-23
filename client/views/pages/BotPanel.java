package views.pages;

import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

import chesslogic.Bot;
import chesslogic.ChessGame;
import chesslogic.DepthSearchBotP2;
import chesslogic.RandomBot;
import config.GameState;
import config.Page;
import config.UserInterface;
import java.awt.Color;
import views.Window;

public class BotPanel extends AbstractGamePanel {

    final int tileSize = 60;

    BufferedImage heldPieceImage;

    DepthSearchBotP2 depthSearchBot;

    private Window window;
    
    public BotPanel(Window window) {

        this.window = window;
        resetGame();
    }

    @Override
    public void resetGame() {
        chessGame = new ChessGame();

        boardPanel.setChessGame(chessGame);
        movesPanel.clearMoves();

        capturedPiecesPanelBlack.setChessGame(chessGame);
        capturedPiecesPanelWhite.setChessGame(chessGame);

        setPlayerColour((int)(Math.random() * 2));

        depthSearchBot = new DepthSearchBotP2(5, (getPlayerColour() + 1) % 2);

        // Bot goes first
        if (getPlayerColour() == 1) {
            processMove("", "", "");
        }

        setGameState(GameState.ONGOING);

        this.revalidate();
    }

    
    @Override
    public void processMove(String tile1, String tile2, String promotion) {
        if(!chessGame.getCurrentPos().ended()) {
            double s = System.currentTimeMillis();
            ChessGame c = chessGame.copy();
            String botMove = depthSearchBot.nextMove(c);
            double y = System.currentTimeMillis();
            System.out.println(y - s);

            int posX = (botMove.charAt(2) - 'a');
            int posY = (botMove.charAt(3) - '0') - 1;

            System.out.println(botMove);
            System.out.println("Bot moved " + botMove.substring(0, 2) + ", " + botMove.substring(2, 4));

            System.out.println(botMove.substring(0,2) + ", " + botMove.substring(2, 4) + ", " + botMove.substring(4, 5) + "stop");
            
            String chessMove = chessGame.toAlgebraic(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4));
            movesPanel.addMove(chessMove);

            chessGame.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));
        }
    }

    @Override
    public void handleGameEnded() {
        // TODO Auto-generated method stub
        System.out.println("game ended!");
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
            if (chessGame.getCurrentPos().getToMove() == getPlayerColour()) {
                undoMove();
                undoMove();
            } else {
                undoMove();
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