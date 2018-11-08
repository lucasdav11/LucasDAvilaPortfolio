package pokemon;
/**
 * Creates a Charmander extending from the Pokemon Class and implementing fire type
 * It's also one of the starter pokemon the trainer can choose from
 * @author Lucas D'Avila 2017
 *
 */
public class Charmander extends Pokemon implements Fire
{
	/**
	 * Constructor creating a Charmander setting its health as a random number between 40 and 60 and setting its level to either 1 or 2
	 */
	public Charmander()
	{
		super("Charmander",(int) ((Math.random() * 60) + 61),(int) ((Math.random() * 2) + 1));
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
