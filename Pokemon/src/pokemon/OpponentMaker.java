package pokemon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * A class that creates an array of opponents for the player to do battle with from a previously created file
 * @author Lucas
 *
 */
public class OpponentMaker 
{
	/**
	 * an array list storing the opponents loaded from the file "OpponentList.txt"
	 */
	private ArrayList<Opponent> opponents = new ArrayList<Opponent>();
	/**
	 * default constructor creating the opponents from the file "OpponentList.txt"
	 */
	public OpponentMaker()
	{
		Opponent addNewOpponent;
		String[] temp = new String[5];
		try{
			Scanner read = new Scanner(new File("OpponentList.txt"));
			while(read.hasNextLine())
			{
				for(int i = 0; i<5; i++)
					temp[i] = read.nextLine();
				addNewOpponent = new Opponent(temp[0],Integer.parseInt(temp[1]),temp[2],temp[3],temp[4]);
				opponents.add(addNewOpponent);
			}//end while
			read.close();
		}catch(IOException e){
			System.out.println("Error processing file");
		}
	}
	/**
	 * picks a random opponent from the array list and creates an exact copy and then returns that opponent object for the player
	 * to do battle with
	 * @return random opponent
	 */
	public static Opponent makeRandomOpponent()
	{
		int index = (int) (Math.random() * 2) + 1;
		OpponentMaker opponentsList = new OpponentMaker();
		
		Opponent opponent = new Opponent(opponentsList.opponents.get(index).getName(),opponentsList.opponents.get(index).getHp(),opponentsList.opponents.get(index).getAttackSpeech(),opponentsList.opponents.get(index).getWinSpeech(),opponentsList.opponents.get(index).getLossSpeech());
		opponent.addPokemon(PokemonMaker.makeStartPokemon((int) (Math.random() * 2) + 1));
		return opponent;
	}
}
