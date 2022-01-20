package views.pages;

import java.awt.image.BufferedImage;

import chesslogic.Bot;
import chesslogic.DepthSearchBotP1;
import config.GameState;
import config.UserInterface;

public class BotPanel extends AbstractGamePanel {

    final int tileSize = 60;

    BufferedImage heldPieceImage;

    Bot depthSearchBot;
    
    public int playerColour;

    public BotPanel() {
        this.setBackground(UserInterface.BACKGROUNDS[UserInterface.activeBackground]);
        
        playerColour = (int)(Math.random() * 2);

        this.boardPanel.setPlayerColour(playerColour);

        depthSearchBot = new DepthSearchBotP1(3, (playerColour + 1) % 2);

        // Bot goes first
        if (playerColour == 1) {
            processMove("", "", "");
        }

        setGameState(GameState.ONGOING);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void processMove(String tile1, String tile2, String promotion) {
        String botMove = depthSearchBot.nextMove(chessGame);
        System.out.println(botMove);
        System.out.println("Bot moved " + botMove.substring(0, 2) + ", " + botMove.substring(2, 4));

        System.out.println(botMove.substring(0,2) + ", " + botMove.substring(2, 4) + ", " + botMove.substring(4, 5) + "stop");
        
        System.out.println(chessGame.getCurrentPos());

        String chessMove = chessGame.getCurrentPos().toAlgebraic(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4));
        movesPanel.addMove(chessMove);

        chessGame.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));

    }
}