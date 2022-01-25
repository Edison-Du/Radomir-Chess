package config;

/**
 * [GameState.java]
 * 
 * @author 
 * @version 1.0 Jan 24, 2022
 */
public enum GameState {
    WAITING,
    ONGOING,
    WHITE_VICTORY_CHECKMATE,
    BLACK_VICTORY_CHECKMATE,
    WHITE_VICTORY_RESIGN,
    BLACK_VICTORY_RESIGN,
    STALEMATE,
    DRAW,
}