package pokemon;
/**
 * Creates a Staryu extending from the Pokemon class implementing water type
 * It is also one of the random pokemon that the trainer can encounter on their journey
 * @author Lucas D'Avila 2017
 *
 */
public class Staryu extends Pokemon implements Water
{
	/**
	 * Constructor creating a Staryu setting its health as a random number between 15 and 30 and setting its level to either 1 or 2
	 */
	Staryu()
	{
		super("Staryu",(int) (Math.random() * 30) + 15,(int) (Math.random() * 2) + 1);
	}

	@Override
	public int waterGun() 
	{
		System.out.println(getName() + "uses Water Gun!");
		return (int) ((Math.random() * 5) + 1) * getLevel();
	}

	@Override
	public int bubbleBeam() 
	{
		System.out.println(getName() + "uses Bubble Beam!");
		return (int) ((Math.random() * 7) + 1) * getLevel();
	}

	@Override
	public int hydroPump() 
	{
		System.out.println(getName() + "uses Hydro Pump!");
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
			return waterGun();
		if (m == 2)
			return bubbleBeam();
		if (m == 3)
			return hydroPump();
		
		return 0;
	}
}
