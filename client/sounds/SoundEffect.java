package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import chesslogic.ChessGame;
import config.PathsConsts;
import config.UserInterface;

public class SoundEffect {

    Clip clip;

    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();

    }

    public static void playSound(String t1, String t2, String p, ChessGame game) {
        if (UserInterface.soundOn) {
            SoundEffect se = new SoundEffect();
            chesslogic.Board current = game.getCurrentPos();
            boolean soundChosen = false;

            if(t1.equals("")) {
                return;
            }

            System.out.println("checkingpoint 1");

            synchronized(game) {
                game.move(t1, t2, p);
            }
            if(game.stalemate()) {
                se.setFile(PathsConsts.STALEMATE);
                soundChosen = true;
            } else if(current.getKings()[0].isChecked(current, current.getKingTiles()[0]) || current.getKings()[1].isChecked(current, current.getKingTiles()[1])) {
                if(game.whiteWins() || game.blackWins()) {
                    se.setFile(PathsConsts.CHECKMATE);
                    soundChosen = true;
                } else {
                    se.setFile(PathsConsts.CHECK);
                    soundChosen = true;
                }
            }

            synchronized(game) {
                game.undo();
            }

            System.out.println("checkingpoint 2");

            if(!soundChosen) {

                System.out.println("checkingpoint 3");

                if(game.getCurrentPos().getTile(t1).getPiece().getName().equals("K") && Math.abs((t1.charAt(0) - '0') - (t2.charAt(0) - '0')) == 2) {
                    se.setFile(PathsConsts.CASTLE);
                } else if(game.getCurrentPos().getTile(t2).getPiece() != null) {
                    System.out.println("checking the tile: " + game.getCurrentPos().getTile(t2).getPiece());
                    se.setFile(PathsConsts.CAPTURE);
                } else {
                    se.setFile(PathsConsts.MOVE);
                }
            }

            se.play();
        }
    }
}