package views.chess;

import javax.swing.SwingWorker;

import chesslogic.ChessGame;
import config.GameState;
import chesslogic.Bot;
import views.pages.BotPanel;

/*
 * idea: check each branch
 * create a queue of moves & feed the moves into different threads, use alpha-beta pruning on each branch
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
            System.out.println("started next move");
            synchronized(chessGameClone) {
                synchronized(chessGame) {
                    botMove = bot.nextMove(chessGameClone);
                }
            }
            System.out.println(chessGame.toString());
            
            System.out.println("finished next move");

            System.out.println(botMove);
            System.out.println("Bot moved " + botMove.substring(0, 2) + ", " + botMove.substring(2, 4));

            System.out.println(botMove.substring(0,2) + ", " + botMove.substring(2, 4) + ", " + botMove.substring(4, 5) + "stop");

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
