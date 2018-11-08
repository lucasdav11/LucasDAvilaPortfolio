package pokemon;
/**
 * Static methods that creates the starter and wild pokemon
 * @author Lucas D'Avila 2017
 *
 */
public class PokemonMaker 
{
	/**
	 * Creates a random pokemon based on what type gets sent to the makeTypePokemon method
	 * @return wild pokemon
	 */
	public static Pokemon makeWildPokemon()
	{
		int type = (int) (Math.random() * 3) + 1;
		return makeTypePokemon(type);
	}
	/**
	 * Creates a Ponyta, Staryu, or Oddish based on a random number between 1 to 3
	 * @param type
	 * @return Ponyta, Staryu, or Oddish
	 */
	public static Pokemon makeTypePokemon(int type)
	{
		if (type == 1)
		{
			Ponyta Ponyta = new Ponyta();
			return Ponyta;
		}//end if
		if (type == 2)
		{
			Staryu Staryu = new Staryu();
			return Staryu;
		}//end if
		if (type == 3)
		{
			Oddish Oddish = new Oddish();
			return Oddish;
		}//end if
		
		return new Charmander();
		
	}
	/**
	 * Creates the starting pokemon Squirtle, Charmander, or Bulbasaur based on what the user inputs 
	 * @param user input
	 * @return Charmander, Squirtle, or Bulbasaur
	 */
	public static Pokemon makeStartPokemon(int start)
	{
		if (start == 1)
			return new Charmander();
		if (start == 2)
			return new Squirtle();
		if (start == 3)
			return new Bulbasaur();
		
		return new Oddish();
	}
}
