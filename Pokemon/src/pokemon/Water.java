package pokemon;
/**
 * An interface that sets a pokemons type to water and gives them water type special moves
 * @author Lucas D'Avila 2017
 *
 */
public interface Water 
{
	public static final int type = 1;
	public static final String specialMenu = "1.Water Gun\n2.Bubble Beam\n3.Hydro Pump";
	/**
	 * water type pokemon uses water gun
	 * @return random damage * pokemon level
	 */
	public int waterGun();
	/**
	 * water type pokemon uses bubble beam
	 * @return random damage * pokemon level
	 */
	public int bubbleBeam();
	/**
	 * water type pokemon uses hydro pump
	 * @return random damage * pokemon level
	 */
	public int hydroPump();
}
