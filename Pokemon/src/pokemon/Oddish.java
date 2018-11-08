package pokemon;
/**
 * Creates an Oddish extending from the Pokemon class implementing grass type
 * It's also one of the random pokemon that can appear on the trainers journey
 * @author Lucas D'Avila 2017
 *
 */
public class Oddish extends Pokemon implements Grass
{
	/**
	 * Constructor creating a Oddish setting its health as a random number between 15 and 30 and setting its level to either 1 or 2
	 */
	Oddish()
	{
		super("Oddish",(int) ((Math.random() * 30) + 31),(int) ((Math.random() * 2) + 1));
	}

	@Override
	public int vineWhip() 
	{
		System.out.println(getName() + "uses Vine Whip!");
		return (int) ((Math.random() * 5) + 1) * getLevel();
	}
	@Override
	public int razorLeaf() 
	{
		System.out.println(getName() + "uses Razor Leaf!");
		return (int) ((Math.random() * 7) + 1) * getLevel();
	}
	@Override
	public int solarBeam() 
	{
		System.out.println(getName() + "uses Solar Beam!");
		return (int) ((Math.random() * 10) + 1) * getLevel();
	}
	@Override
	public int getType() 
	{	
		return type;
	}

	@Override
	public void displaySpecialMenu() 
	{
		System.out.println(specialMenu);			
	}
	@Override
	public int specialFight(int m) 
	{
		if (m == 1)
			return vineWhip();
		if (m == 2)
			return razorLeaf();
		if (m == 3)
			return solarBeam();
		
		return 0;
	}
}
