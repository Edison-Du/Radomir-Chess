package game;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;


public class Pieces {
	public static Color WHITECOLOR = Color.WHITE;
	public static Color BLACKCOLOR = Color.BLACK;
	
	public static String PAWN = "♟";
	public static String ROOK = "♜";
	public static String KNIGHT = "♞";
	public static String BISHOP = "♝";
	public static String QUEEN = "♛";
	public static String KING = "♚";
	
	public static Image bBishop;
	public static Image bKing;
	public static Image bKnight;
	public static Image bPawn;
	public static Image bQueen;
	public static Image bRook;
	public static Image wBishop;
	public static Image wKing;
	public static Image wKnight;
	public static Image wPawn;
	public static Image wQueen;
	public static Image wRook;
	
	public Pieces() {
		try {
			bBishop = ImageIO.read(new File("./images/bbishop.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			bKing = ImageIO.read(new File("./images/bking.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			bKnight = ImageIO.read(new File("./images/bknight.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			bPawn = ImageIO.read(new File("./images/bpawn.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			bQueen = ImageIO.read(new File("./images/bqueen.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			bRook = ImageIO.read(new File("./images/brook.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wBishop = ImageIO.read(new File("./images/wbishop.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wKing = ImageIO.read(new File("./images/wking.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wKnight = ImageIO.read(new File("./images/wknight.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wPawn = ImageIO.read(new File("./images/wpawn.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wQueen = ImageIO.read(new File("./images/wqueen.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			wRook = ImageIO.read(new File("./images/wrook.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
