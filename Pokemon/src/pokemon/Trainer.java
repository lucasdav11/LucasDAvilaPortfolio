package pokemon;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Trainer class used to create a trainer object which uses a number of pokemon to battle other pokemon and trainers within the game
 * Extened from the Entity class 
 * @author Lucas D'Avila 2017
 *
 */
public abstract class Trainer extends Entity implements Serializable
{
	/**
	 * Holds the all the trainers pokemon
	 */
	private ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
	/**
	 * the index for the current pokemon being used in battle
	 */
	private int currentPokemon;
	/**
	 * 
	 * @param n
	 * @param h
	 */
	public Trainer(String n, int h)
	{
		super(n,h);
		currentPokemon = 0;
	}
	/**
	 * displays trainers stats including name, health, money, potions, pokemon captured and their stats
	 */
	public void displayStats()
	{
		System.out.println(getName() + "'s Stats:");
		System.out.println("HP: " + getHp() + "/ " + getMaxHP());
		System.out.println("Pokemon:");
		displayAllPokemon();
	}
	/**
	 * displays an attack speech that appears when the trainer enters a battle
	 */
	public abstract String getAttackSpeech();
	/**
	 * displays a winning speech that appears when the trainer wins a battle
	 */
	public abstract String getWinSpeech();
	/**
	 * displays a losing speech when the trainer loses a battle
	 */
	public abstract String getLossSpeech();
	/**
	 * has the user choose to use a basic or special type of attack
	 * @return user input
	 */
	public int chooseStyle()
	{
		int input = 0;
		
		System.out.println("Choose Attack Type: ");
		System.out.println("1. Basic");
		System.out.println("2. Special");
		input = CheckInput.checkIntRange(1, 2);
		
		return input;
	}
	/**
	 * has the user choose a move based on the style they picked previously whether it was special or basic
	 * @param style chosen by the user
	 * @return user input for what move they want to use
	 */
	public int chooseMove(int style)
	{
		int input = 0;
		
		if (style == 1)
		{
			getCurrentPokemon().displayBasicMenu();
			input = CheckInput.checkIntRange(1, 3);
			return input;
		}
		if (style == 2)
		{
			getCurrentPokemon().displaySpecialMenu();
			input = CheckInput.checkIntRange(1, 3);
			return input;
		}
		
		return 0;
	}
	/**
	 * uses the chooseStyle and chooseMove methods to have the trainers current pokemon battle
	 * @return battle damage
	 */
	public int battle()
	{
		int style, move, damage;
		
		style = chooseStyle();
		move = chooseMove(style);
		damage = getCurrentPokemon().fight(style, move);
		
		return damage;
	}
	/**
	 * displays all pokemon the trainer has and their stats
	 */
	public void displayAllPokemon()
	{
		for (int i = 0; i < pokemon.size(); i++)
		{
			System.out.print((i + 1) + ". " );
			pokemon.get(i).displayStats();
		}
	}
	/**
	 * displays the stats of the trainers current pokemon
	 */
	public void displayCurrentPokemon()
	{
		pokemon.get(currentPokemon).displayStats();
	}
	/**
	 * returns the trainers current pokemon
	 * @return the trainers current pokemon
	 */
	public Pokemon getCurrentPokemon()
	{
		return pokemon.get(currentPokemon);
	}
	/**
	 * adds a pokemon to the trainers team when attempting to catch them
	 * @param new pokemon being caught
	 */
	public void addPokemon(Pokemon p)
	{
		int slot = pokemon.size();
		pokemon.add(slot, p);
	}
	/**
	 * heals the trainers current pokemon if he has a potion available
	 */
	public void healCurrentPokemon()
	{
			System.out.println("Healing " + getCurrentPokemon().getName());
			pokemon.get(currentPokemon).heal();
	}
	/**
	 * heals all the trainers pokemon
	 */
	public void healAllPokemon()
	{
		for (int i = 0; i < pokemon.size(); i++)
			pokemon.get(i).heal();
	}
	/**
	 * changes the trainers current pokemon being used for battle
	 * @param the number value of the pokemon the trainer wants to use
	 * @return that pokemons number value in the array list
	 */
	public int setCurrentPokemon(int cur)
	{
		currentPokemon = cur;
		currentPokemon--;
		return currentPokemon;
	}
	/**
	 * returns how many pokemon the trainer has
	 * @return how many pokemon the trainer has
	 */
	public int numberofPokemon()
	{
		return pokemon.size();
	}
	
}
