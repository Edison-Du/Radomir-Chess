package views.pages;

import chesslogic.ChessGame;
import chesslogic.DepthSearchBotP2;

public class BotThread extends Thread {

    private BotPanel panel;
    private ChessGame game;
    private DepthSearchBotP2 bot;
    private int botColour;

    public BotThread(BotPanel panel, int botColour) {
        this.panel = panel;
        this.game = new ChessGame();
        this.bot = new DepthSearchBotP2(5, botColour);
        this.botColour = botColour;
        this.start();
    }

    public void playerMove(String t1, String t2, String p) {
        game.move(t1, t2, p);
        System.out.println("PLAYER MOVEIN BOT: " + t1 + " " + t2 + " " + p);
        System.out.println(game.getCurrentPos().getToMove() + " " + botColour);
    }

    @Override
    public void run() {
        while (true) {
            // System.out.println(game.getCurrentPos().getToMove());
            if (game.getCurrentPos().getToMove() == botColour) {
                String botMove = bot.nextMove(game);

                String t1 = botMove.substring(0, 2);
                String t2 = botMove.substring(2, 4);
                String p  = botMove.substring(4, 5);

                System.out.println("BOT HERE: "+ t1 + " " + t2 + " " + p);

                int posX = (botMove.charAt(2) - 'a');
                int posY = (botMove.charAt(3) - '0') - 1;

                String chessMove = game.toAlgebraic(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4));
                panel.chessGame.move(t1, t2, p);
                panel.movesPanel.addMove(chessMove);
            }
        }
    }
}
