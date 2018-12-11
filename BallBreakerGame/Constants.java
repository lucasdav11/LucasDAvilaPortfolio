package gui;
/**
 * 
 * @author Lucas D'Avila
 * An interface of constants that are implemented in all the classes for the game Ball Breaker
 */
public interface Constants {
	/**
	 * constant value for the screen width
	 */
	public static final int SCREEN_WIDTH = 500;
	/**
	 * constant value for the screen height
	 */
    public static final int SCREEN_HEIGTH = 500;
    /**
     * constant value for the bottom edge that if the ball crosses the player loses
     * a life
     */
    public static final int BOTTOM_EDGE = 490;
    /**
     * constant value for how many blocks are used in the game
     */
    public static final int N_OF_BLOCKS = 40;
    /**
     * constant x coordinate value for the initial paddle location when the game starts
     */
    public static final int INIT_PADDLE_X = 240;
    /**
     * constant y coordinate value for the initial paddle location when the game starts
     */
    public static final int INIT_PADDLE_Y = 360;
    /**
     * constant x coordinate value for the initial ball location when the game starts
     */
    public static final int INIT_BALL_X = 240;
    /**
     * constant y corrdiante value for the initial ball location when the game starts
     */
    public static final int INIT_BALL_Y = 345;    
}
