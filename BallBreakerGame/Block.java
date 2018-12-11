package gui;
import java.awt.*;
/**
 * 
 * @author Lucas D'Avila
 * Block class that creates blocks used in the Brick Breaker game. The blocks have a 
 * starting location, color, a set width and height, and a flag to determine if the blocks
 * have been destroyed or not
 */
public class Block extends Rectangle {
	private Color color;
	boolean destroyed;
	private int blockWidth, blockHeight;
	/**
	 * constructor that sets the blocks location and color. The width and height are already predetermined
	 * @param x coordinate
	 * @param y coordinate
	 * @param color
	 */
	public Block(int x, int y, Color c) {
		blockWidth = 39;
		blockHeight = 10;
		setBounds(x, y, blockWidth, blockHeight);
		color = c;
		destroyed = false;
	}
	/**
	 * flag that takes in true or false if the block is destroyed or not
	 * @param flag
	 */
	public void destroyBlock(boolean flag) {
		destroyed = flag;
	}
	/**
	 * returns if the block is destroyed or not
	 * @return if block is destroyed or not
	 */
	public boolean isDestroyed() {
		return destroyed;
	}
	/**
	 * overrides the draw function drawing the blocks based on color, location, and dimensions
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}
