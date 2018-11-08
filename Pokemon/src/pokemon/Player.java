package pokemon;

import java.awt.Point;
import java.io.Serializable;

public class Player extends Trainer implements Serializable
{
	
	/**
	 * the amount of potions the trainer has
	 */
	private int potions;
	/**
	 * the amount of pokeballs the trainer has
	 */
	private int pokeball;
	/**
	 * the ammount of money the trainer has
	 */
	private int money;
	/**
	 * holds the players (x,y) coordinate on the map
	 */
	private Point location;
	/**
	 * records which map the player is currently in
	 */
	private int mapNum;
	/**
	 * constructor for trainer objects setting their health, name, amount of potions, pokeballs, and money
	 * @param takes in a name and sets it to the trainer
	 * @param takes in health amount and sets it to the trainer
	 */
	public Player(String n, int h) 
	{
		
		super(n, h);
		potions = 0;
		pokeball = 5;
		money = 100;
		mapNum = 1;
		location = new Point(2,0);
	}
	/**
	 * displays trainers stats including name, health, money, potions, pokemon captured and their stats
	 */
	public void displayStats()
	{
		System.out.println(getName() + "'s Stats:");
		System.out.println("HP: " + getHp() + "/ " + getMaxHP());
		System.out.println("Money: $" + money);
		System.out.println("Potions: " + getNumPotions());
		System.out.println("Pokeballs: " + getNumPokeballs());
		System.out.println("Pokemon:");
		displayAllPokemon();
	}
	/**
	 * spends money the trainer has
	 * @param amount of money being spent
	 */
	public void spendMoney(int mon)
	{
		money = money - mon;
	}
	/**
	 * returns how much money the trainer has
	 * @return how much money the trainer has
	 */
	public int getAmtMoney()
	{
		return money;
	}
	/**
	 * increases the amount of money the trainer has
	 * @param money won
	 */
	public void winAmtMoney(int mon)
	{
		money = money + mon;
	}
	/**
	 * uses a pokeball
	 */
	public void usePokeball()
	{
			pokeball--;
	}
	/**
	 * gains a pokeball
	 */
	public void gainPokeball()
	{
		pokeball++;
	}
	/**
	 * returns the amount of pokeballs the trainer has
	 * @return the number of pokeballs the trainer has
	 */
	public int getNumPokeballs()
	{
		return pokeball;
	}
	/**
	 * uses a potion
	 */
	public void usePotion()
	{
		potions--;
	}
	/**
	 * gains a potion
	 */
	public void gainPotion()
	{
		potions++;
	}
	/**
	 * returns the amount of potions the trainer has
	 * @return potions the trainer has
	 */
	public int getNumPotions()
	{
		return potions;
	}
	/**
	 * returns the location of the player on the map
	 * @return location
	 */
	public Point getLocation()
	{
		return location;
	}
	/**
	 * sets the players location on the map
	 * @param point on map
	 * @return true if completed
	 */
	public boolean setLocation(Point p)
	{
		location = p;
		return true;
	}
	/**
	 * returns what map number the player is on
	 * @return map number
	 */
	public int getMapNum()
	{
		return mapNum;
	}
	/**
	 * increments the map that the player is on
	 */
	public void incMapNum()
	{
		mapNum++;
	}
	/**
	 * sets the map number the player is on
	 * @param map number
	 */
	public void setMapNum(int x)
	{
		mapNum = x;
	}
	/**
	 * has the player go EAST if he is not going out of bounds
	 * @param map
	 * @return returns the char that the player landed on
	 */
	public char goEast(Map m)
	{
		char newLocation = 'x';
		
		if((int)location.getY() < 4)
		{
			location.setLocation(location.getX(), location.getY() + 1);
			newLocation = m.getCharAtLoc(location);
			m.reveal(location);
		}//end if
		else
			System.out.println("Can't go EAST!");
		
		return newLocation;
	}
	/**
	 * has the player go WEST if he is not going out of bounds
	 * @param map
	 * @return returns the char that the player landed on
	 */
	public char goWest(Map m)
	{
		char newLocation = 'x';
		
		if((int)location.getY() > 0)
		{
			location.setLocation(location.getX(), location.getY() - 1);
			newLocation = m.getCharAtLoc(location);
			m.reveal(location);
		}//end if
		else
			System.out.println("Can't go WEST!");
		
		return newLocation;
	}
	/**
	 * has the player go SOUTH if he is not going out of bounds
	 * @param map
	 * @return returns the char that the player landed on
	 */
	public char goSouth(Map m)
	{
		char newLocation = 'x';
		
		if((int)location.getX() < 4)
		{
			location.setLocation(location.getX() + 1, location.getY());
			newLocation = m.getCharAtLoc(location);
			m.reveal(location);
		}//end if
		else
			System.out.println("Can't go SOUTH!");
		
		return newLocation;
	}
	/**
	 * has the player go NORTH if he is not going out of bounds
	 * @param map
	 * @return returns the char that the player landed on
	 */
	public char goNorth(Map m)
	{
		char newLocation = 'x';
		
		if((int)location.getX() > 0)
		{
			location.setLocation(location.getX() - 1, location.getY());
			newLocation = m.getCharAtLoc(location);
			m.reveal(location);
		}//end if
		else
			System.out.println("Can't go NORTH!");
		
		return newLocation;
	}
	@Override
	public String getAttackSpeech() 
	{
		return getCurrentPokemon().getName() + " I choose you!";
	}
	@Override
	public String getWinSpeech() 
	{
		int speech = (int) ((Math.random() * 3) + 1); 
		if (speech == 1)
		{
			return "Better luck next time!";
		}
		if (speech == 2)
		{
			return "That's a win!";
		}
		if (speech == 3)
		{
			return "I'm one step closer to being a Pokemon Master!";
		}
		return null;
	}
	@Override
	public String getLossSpeech() 
	{
		return "Me and my pokemon will be back stronger then ever!";
	}
	
}
