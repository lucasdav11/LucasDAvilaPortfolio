package pokemon;
/**
 * a class that extends the trainer class creating opponents that the player will do battle with while traveling and
 * playing the game. Each opponent has their own pokemon and different speeches
 * @author Lucas
 *
 */
public class Opponent extends Trainer
{
	/**
	 * speech opponent declares before doing battle
	 */
	private String atkSpeech;
	/**
	 * speech opponent declares after winning a battle
	 */
	private String winSpeech;
	/**
	 * speech opponent declares after losing a battle
	 */
	private String lossSpeech;
	/**
	 * constructor setting the opponents name, health, attack speech, loss speech, and winning speech
	 * @param name
	 * @param health
	 * @param atk speech
	 * @param loss speech
	 * @param win speech
	 */
	public Opponent(String n, int h, String atk, String loss, String win) 
	{
		super(n, h);
		atkSpeech = atk;
		winSpeech = win;
		lossSpeech = loss;
		
	}
	@Override
	public String getAttackSpeech() 
	{
		atkSpeech = atkSpeech.replace('#', '\n');
		return atkSpeech;
	}
	@Override
	public String getWinSpeech() 
	{
		winSpeech = winSpeech.replace('#', '\n');
		return winSpeech;
	}
	@Override
	public String getLossSpeech() 
	{
		return lossSpeech;
	}
	@Override
	public int chooseStyle()
	{
		int type = (int) ((Math.random() * 2) + 1);
		return type;
	}
	@Override
	public int chooseMove(int style)
	{
		int move = 0;
		
		if(style == 0)
			move = (int) ((Math.random() * 3) + 1);
		else if(style == 1)
			move = (int) ((Math.random() * 3) + 1);
			
		return move;
	}

}
