package pokemon;
/**
 * Creates a Squirtle extending from the Pokemon class implementing water type
 * It's also one of the starter pokemon the trainer can choose from
 * @author Lucas D'Avila 2017
 *
 */
public class Squirtle extends Pokemon implements Water
{
	/**
	 * Constructor creating a Squirtle setting its health as a random number between 40 and 60 and setting its level to either 1 or 2
	 */
	Squirtle()
	{
		super("Squirtle",(int) ((Math.random() * 60) + 61),(int) ((Math.random() * 2) + 1));
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
