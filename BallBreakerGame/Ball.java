package gui;
import java.awt.*;
/**
 * 
 * @author Lucas D'Avila
 * Ball class that creates a ball object used in the Ball Breaker game. The ball has a set
 * starting location along with a radius, color, and speed
 *
 */
public class Ball extends Rectangle implements Constants {
	/**
	 * private ints that determine the speed and direction of the ball if its positive or negative
	 */
	private int dx, dy;
	/**
	 * color of the ball
	 */
	private Color color;
	/**
	 * constructor that initializes the balls location, radius, color, and speed
	 * @param x coordinate of the starting location of the ball
	 * @param y coordinate of the starting location of the ball
	 * @param radius of the ball
	 * @param color of the ball
	 * @param speed of the ball
	 */
	public Ball(int x, int y, int radius, Color c, int speed) {
		setBounds(x, y, 2*radius, 2*radius);
		dx = speed;
		dy = -speed;
		color = c;
	}
	/**
	 * overrides the draw function in RECT and draws the ball based on the chosen color and radius
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
	/**
	 * moves the ball within the panel of the game based on speed and checks for collisions with the window and
	 * then changes the balls direction accordingly
	 */
	public void move(int winWidth, int winHeight) {
		if(dx > 0 && x > winWidth - width) {
			dx = -dx;
		} else if(dx < 0 && x < 0) {
			dx = -dx;
		}
		if(dy > 0 && y > winHeight - height) {
			dy = -dy;
		} else if(dy < 0 && y < 0) {
			dy = -dy;
		}
		
		translate(dx,dy);
		
	}
	/**
	 * resets the balls starting location
	 */
	public void resetBall() {
		x = INIT_BALL_X;
		y = INIT_BALL_Y;
	}
	/**
	 * sets the direction of the ball either right to left based on positive or negative input
	 * @param positive or negative input
	 */
	public void setDX(int x) {
		dx = dx * x;
	}
	/**
	 * sets the direction of the ball either up or down based on positive or negative input
	 * @param positive or negative input
	 */
	public void setDY(int y) {
		dy = dy * y;
	}
	/**
	 * returns the direction of the ball either left or right
	 * @return left or right direction
	 */
	public int getDX() {
		return dx;
	}
	/**
	 * returns the direction of the ball either up or down
	 * @return up or down direction
	 */
	public int getDY() {
		return dy;
	}
	
}
