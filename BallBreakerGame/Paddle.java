package gui;
import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * 
 * @author Lucas D'Avila
 * Paddle class that creates the paddle used in the Brick Breaker game. The paddle has a 
 * starting location, color, a width and height
 */
public class Paddle extends Rectangle implements Constants{
	private Color color;
	/**
	 * constructor that sets the paddles starting location, width, height, and color
	 * @param x coordinate
	 * @param y coordinate
	 * @param width 
	 * @param height
	 * @param color
	 */
	public Paddle(int x, int y, int width, int height, Color c) {
		setBounds(x, y, width, height);
		color = c;
	}
	/**
	 * overrides the draw function drawing the paddle based on its color x,y locations, width and height
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	/**
	 * overrides the move function moving the paddle only in the left and right direction and sets the
	 * screen as a boundary making sure the paddle doesn't leave the screen
	 * @param position of paddle
	 */
	public void move(int position) {
		if(x <= 0)
			x = 0 + 40;
		else if(x >= SCREEN_WIDTH - width)
			x = SCREEN_WIDTH - width - 40;
		else
			x = position;
	}
	/**
	 * resets the paddles location back to the starting position
	 */
	public void resetPaddle() {
		x = INIT_PADDLE_X;
		y = INIT_PADDLE_Y;
	}
}
