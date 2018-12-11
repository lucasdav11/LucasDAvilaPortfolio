package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/**
 * 
 * @author Lucas D'Avila
 * Window class is a class that extends JFrame and contains the panel where ball breaker is loaded and played
 *
 */
public class Window extends JFrame implements Constants{
	
	public static void main(String [] args) {
		Window w = new Window();
	}
	/**
	 * constructor that sets the bounds for the window, sets the title to Ball Breaker, activates the EXIT ON CLOSE operation, creates a panel, and creates a thread where the game is played
	 */
	public Window() {
		setBounds(100,100,SCREEN_WIDTH,SCREEN_HEIGTH);
		setTitle("Brick Breaker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel();
		setContentPane(p);
		Thread t = new Thread(p);
		t.start();
		setVisible(true);
	}
	/**
	 * 
	 * @author Lucas D'Avila
	 * Panel class that implements runnable, constants, mouseListener, mouseMotionListner, and keyListener
	 * It creates the panel where Ball Breaker is played until the game is over
	 */
	public class Panel extends JPanel implements Runnable, Constants, MouseListener, MouseMotionListener, KeyListener {
		/**
		 * private boolean that determines if the ball should start moving or not
		 */
		private boolean ballStart = false;
		/**
		 * private boolean that determines if the game should start or not
		 */
		private boolean gameStart = true;
		/**
		 * creates the ball object for the game
		 */
		private Ball ball;
		/**
		 * creates the 40 block objects for the game that need to be destroyed
		 */
		private Block blocks[][] = new Block[4][10];
		/**
		 * creates the paddle object for the game
		 */
		private Paddle paddle;
		/**
		 * private int that keeps track of how many blocks are still in play
		 */
		private int numOfBlocks;
		/**
		 * private int that keep track of how many lives or attempts the player has until the game is over
		 */
		private int lifeCounter;
		/**
		 * created the physical image of how many lives the player has and is displayed in the panel
		 */
		private Ball lives[] = new Ball[3];
		/**
		 * constructor that initiates all the objects needed for the game along with setting the background color
		 */
		public Panel() {
			addMouseMotionListener(this);
			addMouseListener(this);
			addKeyListener(this);
			setFocusable(true);
			numOfBlocks = N_OF_BLOCKS;
			setBackground(Color.BLACK);
			ball = new Ball(INIT_BALL_X, INIT_BALL_Y, 5, Color.WHITE, 4);
			paddle = new Paddle(INIT_PADDLE_X, INIT_PADDLE_Y, 40, 10, Color.RED);
			for(int i = 0; i < 10; i++) {
				blocks[0][i] = new Block(i*40+50,60,Color.BLUE);
				blocks[1][i] = new Block(i*40+50,80,Color.RED);
				blocks[2][i] = new Block(i*40+50,100,Color.GREEN);
				blocks[3][i] = new Block(i*40+50,120,Color.YELLOW);
			}
			lifeCounter = 3;
			for(int i = 0; i < 3; i++) {
				lives[i] = new Ball(i*15+10, 10, 5, Color.WHITE, 0);
			}
						
		}
		/**
		 * overrides the paintComponent and draws all the objects of the game on the screen and continues to draw them
		 * until either the blocks are destroyed or the player runs out of lives
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(ballStart && lifeCounter > 0 && numOfBlocks > 0) {
				ball.move(getWidth(), getHeight());
				if(ball.getDY() > 0 && ball.getY() > getHeight() - ball.getHeight()) {
					ballStart = false;
					ball.resetBall();
					ball.setDY(-1);
					lifeCounter--;
				}
			}
			if(lifeCounter == 0 || numOfBlocks == 0)
				gameStart = false;
			checkCollision();
			ball.draw(g);
			paddle.draw(g);
			for(int i = 0; i < 10; i++) {
				if(!blocks[0][i].isDestroyed())
					blocks[0][i].draw(g);
				if(!blocks[1][i].isDestroyed())
					blocks[1][i].draw(g);
				if(!blocks[2][i].isDestroyed())
					blocks[2][i].draw(g);
				if(!blocks[3][i].isDestroyed())
					blocks[3][i].draw(g);
			}
			for(int i = 0; i < lifeCounter; i++)
				lives[i].draw(g);
			
		}
		/**
		 * overrides the run in runnable allowing the game to run until the player either wins or loses then
		 * a message appears on the screen accordingly
		 */
		public void run() {
			while(gameStart) {
				repaint();
				try {
					Thread.sleep(16);
				} catch(InterruptedException e) {}
			}//end while(true)
			if(lifeCounter == 0) {
				JOptionPane.showMessageDialog(this, "You Lose!");
			}
			if(numOfBlocks == 0) {
				JOptionPane.showMessageDialog(this, "You Win!");
			}
		}
		/**
		 * checks the collision of the ball with either the paddle or one of the bricks and if the ball makes
		 * contact with one of the bricks then it is destroyed
		 */
		public void checkCollision() {
			int ballLeft = (int) ball.getMinX();
			int ballHeight = (int) ball.getHeight();
			int ballWidth = (int) ball.getWidth();
			int ballTop = (int) ball.getMinY();
			Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
			Point pointLeft = new Point(ballLeft - 1, ballTop);
			Point pointTop = new Point(ballLeft, ballTop - 1);
			Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
			if(ball.getMaxY() > Constants.BOTTOM_EDGE) {
				//stopGame();
			}
			if(ball.intersects(paddle)) {
				if (paddle.contains(pointRight)) {
					ball.setDX(-1);
				} else if (paddle.contains(pointLeft)) {
					ball.setDX(-1);
				}

				if (paddle.contains(pointTop)) {
					ball.setDY(-1);
				} else if (paddle.contains(pointBottom)) {
					ball.setDY(-1);
				}
				
			}
			for(int i = 0; i < 4; i++) {
				for (int j = 0; j < 10; j++) {
					if (ball.intersects(blocks[i][j])) {
						if (!blocks[i][j].isDestroyed()) {
							if (blocks[i][j].contains(pointRight)) {
								ball.setDX(-1);
							} else if (blocks[i][j].contains(pointLeft)) {
								ball.setDX(-1);
							}
							if (blocks[i][j].contains(pointTop)) {
								ball.setDY(-1);
							} else if (blocks[i][j].contains(pointBottom)) {
								ball.setDY(-1);
							}
							blocks[i][j].destroyBlock(true);
							numOfBlocks--;	
						}
					}
				}
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * overrides keyPressed allowing the player to start the game by using the space bar
		 * and moving the paddle with the left and right keys
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				ballStart = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				paddle.move((int)paddle.getX() - 40);
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				paddle.move((int)paddle.getX() + 40);
			}
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * overrides the mouseMoved allowing the player to move the paddle with the mouse
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			paddle.move((int)e.getX()-20);
	
		}
		/**
		 * overrides the mouseClicked allowing the player to start the game by clicking the mouse
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			ballStart = true;
			
		}
		/**
		 * overrides the mouseClicked allowing the player to start the game by clicking the mouse
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			ballStart = true;
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
			
		}
	}
}