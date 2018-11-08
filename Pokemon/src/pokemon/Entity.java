package pokemon;

import java.io.Serializable;

/**
 * 
 * Parent class that extends out to Trainer and Pokemon setting a base for their health and name
 * @author Lucas D'Avila 2017
 *
 */
public abstract class Entity implements Serializable 
{
	/**
	 * String that holds objects name
	 */
	private String name;
	/**
	 * objects health points
	 */
	private int hp;
	/**
	 * objects max health to be used for healing 
	 */
	private int maxHp;
	/**
	 * constructor for any entity setting their name, starting health, and max health
	 * @param the name of the entity
	 * @param the amount of health of the entity 
	 */
	public Entity (String n, int h)
	{
		name = n;
		hp = h;
		maxHp = h;
	}
	/**
	 * returns the objects name
	 * @return entity name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * returns the current health amount
	 * @return amount of current health
	 */
	public int getHp()
	{
		return hp;
	}
	/**
	 * increases the max amount of HP, used in leveling up
	 */
	public void increaseMaxHp()
	{
		maxHp = maxHp + 3;
	}
	/**
	 * returns the max health points
	 * @return the maximum HP amount
	 */
	public int getMaxHP()
	{
		return maxHp;
	}
	/**
	 * has the object lose a determined amount of damage
	 * @param takes hit points and subtracts it to the amount of current health
	 * @return amount of health left
	 */
	public int loseHp(int hit)
	{
		hp = hp - hit;
		return hp;
	}
	/**
	 * sets the health to the amount of max health
	 */
	public void heal()
	{
		hp = maxHp;
	}
	
}
