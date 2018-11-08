package pokemon;
/**
 * An interface that sets a pokemons type to fire and gives them fire type special moves
 * @author Lucas D'Avila 2017
 *
 */
public interface Fire 
{
	/**
	 * stores the pokemons type as an int
	 */
	public static final int type = 0;
	/**
	 * special fight menu
	 */
	public static final String specialMenu = "1.Ember\n2.Fire Punch\n3.Fire Blast";
	/**
	 * fire type pokemon use ember
	 * @return random damage * pokemons level
	 */
	public int ember();
	/**
	 * fire type pokemon use fire punch
	 * @return random damage * pokemons level
	 */
	public int firePunch();
	/**
	 * fire type pokemon use fire blast
	 * @return random damage * pokemons level
	 */
	public int fireBlast();
}
