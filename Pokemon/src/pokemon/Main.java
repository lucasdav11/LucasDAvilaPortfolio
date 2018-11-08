package pokemon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Main that plays Pokemon Game by outputting a series of menus and creating a trainer and several pokemon for the trainer to do battle
 * with until the trainers health goes down to 0 or the user decides to quit the game
 * @author Lucas D'Avila 2017
 *
 */
public class Main {

	public static void main(String[] args) 
	{
		
		Player player = null;
		char playerLocation = 'x';
		int gameChoice = 2;
		int pokeChoice = 0;
		int travelChoice = 0;
		int menuChoice = 0;
		Map gameMap = new Map();
		File f = new File("pokemon.dat");
		if(f.exists())
		{
			System.out.println("Do you wish to continue your saved game?");
			System.out.println("1. Yes\n2. No");
			gameChoice = CheckInput.checkIntRange(1, 2);
		}
		
		if(gameChoice == 1)
		{
			try{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
				player = (Player)in.readObject();
				gameMap.generateArea(player.getMapNum());
			}catch(IOException e){
				System.out.println("Error processing file");
			}catch(ClassNotFoundException e){
				System.out.println("Can't find class");
			}
			
		}
		else
		{
			player = new Player("Ash Ketchum", 10);
			System.out.println("You are a brand new trainer traveling the world in search of battles to win and pokemon to capture!");
			System.out.println("Choose your first pokemon!");
			System.out.println("1. Charmander\n2. Squirtle\n3. Bulbcasaur");
			pokeChoice = CheckInput.checkIntRange(1, 3);
			player.addPokemon(PokemonMaker.makeStartPokemon(pokeChoice));
			System.out.println("Congrats! You picked " + player.getCurrentPokemon().getName() + " to set on your adventure!");
			
		}
		player.displayStats();
		
		gameMap.generateArea(player.getMapNum());
		
		while((player.getHp() > 0) && menuChoice != 5)
		{
			PokemonBattles.menu();
			menuChoice = CheckInput.checkIntRange(1, 5);
			
			switch(menuChoice)
			{
				case 1:
					System.out.println("Where would you like to go?");
					System.out.println("Map:");
					gameMap.displayMap(player.getLocation());
					System.out.println("1. North\n2. East\n3. South\n4. West");
					travelChoice = CheckInput.checkIntRange(1,4);
					switch(travelChoice)
					{
						case 1:
							playerLocation = player.goNorth(gameMap);
							PokemonBattles.travel(playerLocation, player, gameMap);
							break;
						case 2:
							playerLocation = player.goEast(gameMap);
							PokemonBattles.travel(playerLocation, player, gameMap);
							break;
						case 3:
							playerLocation = player.goSouth(gameMap);
							PokemonBattles.travel(playerLocation, player, gameMap);
							break;
						case 4:
							playerLocation = player.goWest(gameMap);
							PokemonBattles.travel(playerLocation, player, gameMap);
							break;
						 
					}//end switch(travelChoice)
					travelChoice = 0;
					break;
				 //Allows the user to change his current pokemon to use for battle
				case 2:
					System.out.println("Pokemon:");
					player.displayAllPokemon();
					System.out.println("Which Pokemon do you want to use for battle?");
					player.setCurrentPokemon(CheckInput.checkIntRange(1, player.numberofPokemon()));
					break;
				 //heals the trainers current pokemon
				case 3:
					player.healCurrentPokemon();
					player.getCurrentPokemon().displayStats();
					break;
				 //displays the trainers stats
				case 4:
					player.displayStats();
					break;
				 //quits the game
				case 5:
					System.out.println("Game not saved! Goodbye!");
					break;
				default:
					System.out.println("INVALID");
					break;
			}//end switch(MenuChoice)
		}//end while
		 //displays a message if the trainers health falls below 0
		if (player.getHp() < 0)
			System.out.println("The adventure was to much for you and you lost all your health! Game Over!");
	}

}
