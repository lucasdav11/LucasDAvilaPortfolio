package pokemon;
/**
 * An interface that sets a pokemons type to grass and gives them grass type special moves
 * @author Lucas D'Avila 2017
 *
 */
public interface Grass 
{
	public static final int type = 2;
	public static final String specialMenu = "1.Vine Whip\n2.Razor Leaf\n3.Solar Beam";
	/**
	 * grass pokemon use vine whip
	 * @return random damage * pokemon level
	 */
	public int vineWhip();
	/**
	 * grass pokemon uses razor leaf
	 * @return random damage * pokemon level
	 */
	public int razorLeaf();
	/**
	 * grass pokemon uses solar beam
	 * @return random damage * pokemon level
	 */
	public int solarBeam();
}
