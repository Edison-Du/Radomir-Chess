package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import chesslogic.ChessGame;
import config.PathConsts;
import config.UserInterface;

/**
 * [SoundEffect.java]
 * 
 * @author Alex Zhu
 * @version 1.0 Jan 24, 2022
 */
public class SoundEffect {

    Clip clip;

    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            System.out.println("Audio file could not be opened");
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

            synchronized(game) {
                game.move(t1, t2, p);
            }
            if(game.stalemate()) {
                se.setFile(PathConsts.STALEMATE);
                soundChosen = true;
            } else if(current.getKings()[0].isChecked(current, current.getKingTiles()[0]) || current.getKings()[1].isChecked(current, current.getKingTiles()[1])) {
                if(game.whiteWins() || game.blackWins()) {
                    se.setFile(PathConsts.CHECKMATE);
                    soundChosen = true;
                } else {
                    se.setFile(PathConsts.CHECK);
                    soundChosen = true;
                }
            }

            synchronized(game) {
                game.undo();
            }

            if(!soundChosen) {
                if(game.getCurrentPos().getTile(t1).getPiece().getName().equals("K") && Math.abs((t1.charAt(0) - '0') - (t2.charAt(0) - '0')) == 2) {
                    se.setFile(PathConsts.CASTLE);
                } else if(game.getCurrentPos().getTile(t2).getPiece() != null) {
                    se.setFile(PathConsts.CAPTURE);
                } else {
                    se.setFile(PathConsts.MOVE);
                }
            }

            se.play();
        }
    }
}