package views.chess;

import javax.swing.SwingWorker;

import chesslogic.ChessGame;
import config.PathsConsts;
import chesslogic.Board;
import chesslogic.Bot;
import views.pages.BotPanel;

/*
 * idea: check each branch
 * create a queue of moves & feed the moves into different threads, use alpha-beta pruning on each branch
 */

public class ThreadBotP1 extends SwingWorker<String, Void> {

    private ChessGame chessGame;
    private ChessGame chessGameClone;
    private Bot bot;
    private MovesPanel movesPanel;
    private BotPanel gamePanel;

    public ThreadBotP1(ChessGame chessGame, ChessGame chessGameClone, Bot bot, MovesPanel movesPanel, BotPanel gamePanel) {
        this.chessGame = chessGame;
        this.chessGameClone = chessGameClone;
        this.bot = bot;
        this.movesPanel = movesPanel;
        this.gamePanel = gamePanel;
    }

    @Override
    protected String doInBackground() throws Exception {
            String botMove;
            System.out.println("started next move");
            synchronized(chessGameClone) {
                synchronized(chessGame) {
                    botMove = bot.nextMove(chessGameClone);
                }
            }
            System.out.println(chessGame.toString());
            
            System.out.println("finished next move");

            int posX = (botMove.charAt(2) - 'a');
            int posY = (botMove.charAt(3) - '0') - 1;

            System.out.println(botMove);
            System.out.println("Bot moved " + botMove.substring(0, 2) + ", " + botMove.substring(2, 4));

            System.out.println(botMove.substring(0,2) + ", " + botMove.substring(2, 4) + ", " + botMove.substring(4, 5) + "stop");

            if (chessGame.getCurrentPos().getToMove() != gamePanel.getPlayerColour()) {
                synchronized(chessGame) {
                    synchronized(chessGameClone) {
                        chessGameClone.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));
                        String chessMove = chessGame.toAlgebraic(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4));

                        playSound(botMove.substring(0,2), botMove.substring(2, 4), botMove.substring(4,5));

                        movesPanel.addMove(chessMove);
                        chessGame.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));

                        gamePanel.handleGameEnded();
                    }
                }
            }

        return null;
    }

    public void playSound(String t1, String t2, String p) {
        SoundEffect se = new SoundEffect();
        Board current = chessGameClone.getCurrentPos();
        boolean soundChosen = false;

        if(t1.equals("")) {
            return;
        }

        chessGameClone.move(t1, t2, p);
        if(chessGameClone.stalemate()) {
            se.setFile(PathsConsts.STALEMATE);
            chessGameClone.undo();
            soundChosen = true;
        } else if(current.getKings()[0].isChecked(current, current.getKingTiles()[0]) || current.getKings()[1].isChecked(current, current.getKingTiles()[1])) {
            if(chessGameClone.whiteWins() || chessGameClone.blackWins()) {
                se.setFile(PathsConsts.CHECKMATE);
                chessGameClone.undo();
                soundChosen = true;
            } else {
                se.setFile(PathsConsts.CHECK);
                chessGameClone.undo();
                soundChosen = true;
            }
        }

        if(!soundChosen) {
            chessGameClone.undo();

            if(chessGameClone.getCurrentPos().getTile(t1).getPiece().getName().equals("K") && Math.abs((t1.charAt(0) - '0') - (t2.charAt(0) - '0')) == 2) {
                se.setFile(PathsConsts.CASTLE);
            } else if(chessGameClone.getCurrentPos().getTile(t2).getPiece() != null) {
                System.out.println("checking the tile: " + chessGameClone.getCurrentPos().getTile(t2).getPiece());
                se.setFile(PathsConsts.CAPTURE);
            } else {
                se.setFile(PathsConsts.MOVE);
            }
        }

        se.play();
        System.out.println("DEEZ NUTS ARE HUUUUUUUUUUUGE" + se.toString());
    }

}
