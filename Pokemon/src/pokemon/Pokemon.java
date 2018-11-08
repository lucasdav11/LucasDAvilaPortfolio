package pokemon;

import java.io.Serializable;

/**
 * An abstract class that extends from the Entity class that creates Pokemon that will do battle with other pokemon
 * @author Lucas D'Avila 2017
 *
 */
public abstract class Pokemon extends Entity implements Serializable
{
	/**
	 * pokemons battle level
	 */
	private int level;
	/**
	 * basic fighting menu
	 */
	private final String basicMenu = "1.Slam\n2.Tackle\n3.Scratch";
	/**
	 * Constructor that takes in a name, health, and level and sets it to the new pokemon
	 * @param name
	 * @param health
	 * @param level
	 */
	public Pokemon(String n, int h, int l)
	{
		super(n,h);
		level = l;
	}
	/**
	 * returns if the pokemons type, fire, water, or grass
	 * @return the pokemon type
	 */
	public abstract int getType();
	/**
	 * displays individual pokemon special moves based on type
	 */
	public abstract void displaySpecialMenu();
	/**
	 * deals damage based on special moves and user choice
	 * @param move
	 * @return damage from the special move
	 */
	public abstract int specialFight(int m);
	/**
	 * returns the pokemons level
	 * @return pokemon's level
	 */
	public int getLevel()
	{
		return level;
	}
	/**
	 * randomly increases the pokemons level out of a 1 to 3 chance
	 */
	public void gainLevel()
	{
		int levelup = (int)(Math.random() * 3) + 1;
		if (levelup == 2)
		{
			level++;
			increaseMaxHp();
			heal();
			System.out.println(getName() + " Leveled Up!");
			displayStats();
		}
	}
	/**
	 * displays the pokemons stats
	 */
	public void displayStats()
	{
		System.out.println(getName() + " Level: " + getLevel() + " HP: " + getHp() + "/" + getMaxHP());
	}
	/**
	 * displays the pokemons basic fight menu
	 */
	public void displayBasicMenu()
	{
		System.out.println(basicMenu);
	}
	/**
	 * uses a pokemons basic move based on user input
	 * @param move that user chose 
	 * @return damage done by chosen move
	 */
	public int basicFight(int move)
	{
		if (move == 1)
			return slam();
		if (move == 2)
			return tackle();
		if (move == 3)
			return scratch();
		
		return 0;
	}
	/**
	 * uses the basicFight method or specialFight method bases on user input
	 * @param style chosen, basic or special
	 * @param move chosen based on whether it was basic or special
	 * @return damage done 
	 */
	public int fight(int style, int move)
	{
		if (style == 1)
			return basicFight(move);
		if (style == 2)
			return specialFight(move);
		
		return 0;
	}
	/**
	 * pokemon uses slam
	 * @return random damage * the pokemons level
	 */
	public int slam()
	{
		System.out.println(getName() + " uses slam!");
		return (int) (((Math.random() * 3) + 2) * level);
	}
	/**
	 * pokemon uses tackel
	 * @return random damage * the pokemons level
	 */
	public int tackle()
	{
		System.out.println(getName() + " uses tackle!");
		return (int) (((Math.random() * 3) + 2) * level);
	}
	/**
	 * pokemon uses scratch
	 * @return damage done * the pokemons level
	 */
	public int scratch()
	{
		System.out.println(getName() + " uses scratch!");
		return (int) (((Math.random() * 3) + 2) * level);
	}

}
