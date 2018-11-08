package pokemon;
/**
 * Creates a Ponyta extending from the Pokemon class implementing fire type
 * It is also one of the pokemon that the trainer can encounter randomly on their journey
 * @author Lucas D'Avila 2017
 *
 */
public class Ponyta extends Pokemon implements Fire
{
	/**
	 * Constructor creating a Ponyta setting its health as a random number between 15 and 30 and setting its level to either 1 or 2
	 */
	Ponyta()
	{
		super("Pontya",(int) (Math.random() * 30) + 15,(int) (Math.random() * 2) + 1);
	}

	@Override
	public int ember() 
	{
		System.out.println(getName() + "uses Ember!");
		return (int) ((Math.random() * 5) + 1) * getLevel();
	}
	@Override
	public int firePunch() 
	{
		System.out.println(getName() + "uses Fire Punch!");
		return (int) ((Math.random() * 7) + 1) * getLevel();
	}
	@Override
	public int fireBlast() 
	{
		System.out.println(getName() + "uses Fire Blast!");
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
			return ember();
		if (m == 2)
			return firePunch();
		if (m == 3)
			return fireBlast();
		
		return 0;
	}
}
