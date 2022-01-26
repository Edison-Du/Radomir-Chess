package views.chess;

import javax.swing.SwingWorker;
import chesslogic.ChessGame;
import config.GameState;
import chesslogic.Bot;
import views.pages.BotPanel;

/**
 * [BotThread.java]
 * 
 * Uses SwingWorker to run the bot in the background
 * so that the UI is still responsive
 * @author Alex Zhu
 * @version 1.0 Jan 24, 2022
 */
public class BotThread extends SwingWorker<String, Void> {

    private ChessGame chessGame;
    private ChessGame chessGameClone;
    private Bot bot;
    private MovesPanel movesPanel;
    private BotPanel gamePanel;
    private String botMove;

    public BotThread(ChessGame chessGame, ChessGame chessGameClone, Bot bot, MovesPanel movesPanel, BotPanel gamePanel) {
        this.chessGame = chessGame;
        this.chessGameClone = chessGameClone;
        this.bot = bot;
        this.movesPanel = movesPanel;
        this.gamePanel = gamePanel;
    }

    @Override
    protected String doInBackground() throws Exception {
        synchronized(chessGameClone) {
            synchronized(chessGame) {
                botMove = bot.nextMove(chessGameClone);
            }
        }

        if (chessGame.getCurrentPos().getToMove() != gamePanel.getPlayerColour() && 
            gamePanel.getGameState() == GameState.ONGOING) {
            synchronized(chessGame) {
                synchronized(chessGameClone) {
                    chessGameClone.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));
                    String chessMove = chessGame.toAlgebraic(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4));

                    movesPanel.addMove(chessMove);
                    chessGame.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));

                    gamePanel.handleGameEnded();
                }
            }
        }
        return null;
    }
}