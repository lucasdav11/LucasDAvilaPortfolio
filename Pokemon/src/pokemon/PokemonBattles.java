package pokemon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Static methods that has the trainer battling random pokemon, other trainers and encountering angry pokemon and trainers
 * It also has static methods that output the main game menu along with the battle menu and travel options
 * @author Lucas
 *
 */
public class PokemonBattles 
{
	/**
	 * double array that stores the pokemons damage ration based on types for pokemon battle damage calculations
	 */
	private final static double [][] fightTable = {{1,.5,2}, {2,1,.5}, {.5,2,1}};
	/**
	 * Static method that has the trainers pokemon battle another pokemon displaying each pokemons stats along with
	 * calculating the damage they deal based on what type they are
	 * @param trainer pokemon
	 * @param pokemon trainer battles
	 */
	public static void pokemonBattle(Player player, Pokemon poke)
	{
			double playerDamage, pokeDamage;
			
			playerDamage = player.battle() * fightTable[player.getCurrentPokemon().getType()][poke.getType()];
			pokeDamage = poke.fight((int) (Math.random() * 2) + 1,(int) (Math.random() * 3) + 1) * fightTable[poke.getType()][player.getCurrentPokemon().getType()];
			
			player.getCurrentPokemon().loseHp((int) pokeDamage);
			poke.loseHp((int)playerDamage); 
			
			
			player.getCurrentPokemon().displayStats();
			poke.displayStats();
	}
	/**
	 * static method that has the trainer randomly encounter an angry pokemon that deals damage to the trainer himself
	 * @param trainer
	 */
	public static void angryPokemon(Player player)
	{
		int choice = (int) (Math.random() * 2) + 1;
		int damage = 0;
		
		switch(choice)
		{
		case 1:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You've come across a sick Pikachu!");
			System.out.println("You tried to help it\n Pikachu accientally shocked you! Causing " + damage + " damage!");
			break;
		case 2:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You've come across a flock of wild Spearow!");
			System.out.println("They all flew in and pecked at you! Causing " + damage + " damage!" );
			break;
		case 3:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You've come across MewTwo!");
			System.out.println("You tried throwing a pokeBall\nMewTwo sent it back flying at your head! Causing " + damage + " damage!");
			break;
		default:
			break;
		}
		
	}
	/**
	 * static method that has the trainer randomly encounter an angry person that deals damage to the trainer himslef
	 * @param trainer
	 */
	public static void angryPerson(Player player)
	{
		int choice = (int) (Math.random() * 3) + 1;
		int damage = 0;
		
		switch(choice)
		{
		case 1:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You run across Misty");
			System.out.println("Where's my bike twerp!?\nMisty whacks you in the head! Causing " + damage + " damage!");
			break;
		case 2:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You run across Team Rocket");
			System.out.println("You come across a Team Rocket\nPrepare for trouble!\nMake it double!\nTo protect the world from devastation!\nTo unite all peoples within our nation!\nTo denounce the evils of truth and love!\nTo extend our reach to the stars above!\nJessie!\nJames!\nTeam Rocket, blast off at the speed of light!\nSurrender now, or prepare to fight!\nMeowth!  That's right! \nMeowth scratches you! Causing " + damage + " damage!" );
			break;
		case 3:
			damage = (int) (Math.random() * 2) + 1;
			player.loseHp(damage);
			System.out.println("You run across Brock");
			System.out.println(" He says Nurse Joy!\nBrock tramples over you! Causing " + damage + " damage!");
			break;
		default:
			break;
		}
		
	}
	/**
	 * static function that takes in a player and his opponent found on the map and has them battle with
	 * their pokemon until one of their pokemon is unable to battle. the function the returns a boolean flag
	 * which helps determine if the opponent on the map should be removed or not
	 * @param player
	 * @param opponent
	 * @return flag to determine if the opponent on the flag should be removed
	 */
	public static boolean OpponentBattle(Player player, Opponent opponent)
	{
		int battleChoice = 0;
		boolean outCome = false;
		
		System.out.println(opponent.getName());
		System.out.println(opponent.getAttackSpeech());
		opponent.displayAllPokemon();
		player.getAttackSpeech();
		while(opponent.getCurrentPokemon().getHp() > 0 && battleChoice != 1 && player.getCurrentPokemon().getHp() > 0)
		{
			PokemonBattles.wildPokemonMenu();
			battleChoice = CheckInput.checkIntRange(1, 4);
			switch(battleChoice)
			{
				case 1:
					System.out.println("You ran away!");
					break;
				case 2:
					if(player.getCurrentPokemon().getHp() < 1)
					{
						int damage = (int) (Math.random() * 3) + 1;
						player.loseHp(damage);
						System.out.println(player.getCurrentPokemon().getName() + " is knocked out!");
						System.out.println(player.getName() + " used Tackle on you! Causing " + damage + " damage!");
					}//end if
					else
					{
						PokemonBattles.pokemonBattle(player, opponent.getCurrentPokemon());
					}//end else
					break;
				case 3:
					player.healCurrentPokemon();
					break;
				case 4:
					System.out.println("You can't catch someone else's Pokemon!");
					break;
				default:
					break;	
			}//end switch(battleChoice
		}//end while
		if (opponent.getCurrentPokemon().getHp() < 1)
		{
			int money = (int) (Math.random() * 60) + 30;
			System.out.println(opponent.getCurrentPokemon().getName() + " fainted!");
			opponent.getLossSpeech();
			System.out.println(player.getCurrentPokemon().getName() + " gained experience!");
			player.getCurrentPokemon().gainLevel();
			System.out.println("Trainer Timmy ran away crying!");
			player.getWinSpeech();
			System.out.println("You won $" + money);
			player.winAmtMoney(money);
			outCome = true;
		}//end if
		if (player.getCurrentPokemon().getHp() < 1)
		{
			System.out.println(player.getCurrentPokemon().getName() + " fainted!");
			player.getLossSpeech();
		}//end if
		
		return outCome;
	}
	
	/**
	 * static method that displays the games main menu
	 */
	public static void menu()
	{
		System.out.println("What would you like to do?");
		System.out.println("1. Travel\n2. Switch Pokemon\n3. Heal Current Pokemon\n4. View Stats\n5. Quit Game");
	}
	/**
	 * static method that displays the menu when the trainer encounters a battle
	 */
	public static void wildPokemonMenu()
	{
		System.out.println("What would you like to do?");
		System.out.println("1. Run Away\n2. Fight\n3. Use Potion\n4. Throw Pokeball");
	}
	/**
	 * static method that has the trainer do battle with a wild pokemon and the battle will end if 
	 * either the player's pokemon or the wild pokemon can no longer do battle, the player runs away,
	 * or if the wild pokemon has been caught by the trainer. it then returns a flag if the pokemon has
	 * been defeated or captured removing the wild pokemon from the map
	 * @param player
	 * @param wildPokeChoice
	 * @return a flag determing if the wild pokemon should be removed from the map or not
	 */
	public static boolean wildPokemonBattle(Player player, int wildPokeChoice)
	{
		boolean flag = false;
		Pokemon wild = PokemonMaker.makeWildPokemon();
		System.out.println("You come across a wild " + wild.getName());
		wild.displayStats();
		player.getAttackSpeech();
		while ((wild.getHp() > 0) && wildPokeChoice != 1 && (player.getCurrentPokemon().getHp() > 0))
		{
			PokemonBattles.wildPokemonMenu();
			wildPokeChoice = CheckInput.checkIntRange(1, 4);
			switch(wildPokeChoice)
			{
				case 1:
					System.out.println("You ran away!");
					break;
				case 2:
					if(player.getCurrentPokemon().getHp() < 1)
					{
						int damage = (int) (Math.random() * 3) + 1;
						player.loseHp(damage);
						System.out.println(player.getCurrentPokemon().getName() + " is knocked out!");
						System.out.println(wild.getName() + " used Tackle on you! Causing " + damage + " damage!");
					}//end if
					else
					{
						PokemonBattles.pokemonBattle(player, wild);
					}//end else
					break;
				case 3:
					player.healCurrentPokemon();
					break;
				case 4:
					if(player.getNumPokeballs() > 0)
					{
						System.out.println("You throw a Pokeball at " + wild.getName() + ".   Shake...Shake...Shake...");
						System.out.println("You have successfully captured " + wild.getName() + "!");
						player.usePokeball();
						player.addPokemon(wild);
						wildPokeChoice = 1;
						flag = true;
					}//end if
					else
						System.out.println("You are out of Pokeballs!");
					break;
				default:
					break;	
			}//end switch(wildPokeChoice)
		}//end while
		if (wild.getHp() < 1)
		{
			System.out.println(wild.getName() + " fainted!");
			System.out.println(player.getCurrentPokemon().getName() + " gained experience!");
			player.getCurrentPokemon().gainLevel();
			player.getWinSpeech();
			flag = true;
		}//end if
		if (player.getCurrentPokemon().getHp() < 1)
		{
			System.out.println(player.getCurrentPokemon().getName() + " fainted!");
			player.getLossSpeech();
		}//end if
		wildPokeChoice = 0;
		return flag;
	}
	/**
	 * static function that displays an interactive menu allowing the player to travel within a town to either go to the
	 * pokemon center to heal all their pokemon or go to the pokeMart to buy potions and pokeballs
	 * @param player
	 * @param townChoice
	 * @param pokeMartChoice
	 */
	public static void travelTown(Player player, int townChoice, int pokeMartChoice)
	{
		while (townChoice != 3)
		{
			System.out.println("You come across a town! What would you like to do here?");
			System.out.println("1. Go to the Pokemon Center\n2. Go to the Poke Mart\n3. Leave");
			townChoice = CheckInput.checkIntRange(1, 3);
			switch (townChoice)
			{
				case 1:
					System.out.println(" Welcome to the Pokemon Center!");
					System.out.println("--------------------------------");
					System.out.println("\nNurse Joy healed all your Pokemon!");
					player.healAllPokemon();
					break;
				case 2:
					while (pokeMartChoice != 3)
					{
						System.out.println(" Welcome to the Poke Mart!");
						System.out.println("---------------------------");
						System.out.println("\nWhat would you like to do here?");
						System.out.println("1. Buy a Pokeball ($10)\n2. Buy a Potion ($5)\n3. Leave");
						pokeMartChoice = CheckInput.checkIntRange(1, 3);
						switch (pokeMartChoice)
						{
							case 1:
								if(player.getAmtMoney() > 10)
								{
									player.gainPokeball();
									player.spendMoney(10);
									System.out.println("You purchased a Poke Ball!");
								}//end if
								else
									System.out.println("You don't have enough Money!");
								break;
							case 2:
								if(player.getAmtMoney() > 5)
								{
									player.gainPotion();
									player.spendMoney(5);
									System.out.println("You purchased a Potion!");
								}//end if
								else
									System.out.println("You don't have enough Money!");
								break;
							case 3:
								System.out.println("You are leaving the Poke Mart!");
								break;
						}//end switch(pokeMartChoice)
					
					}//end while
					pokeMartChoice = 0;
					break;
				case 3:
					System.out.println("You are leaving Town!");
					break;
			}//end switch(townChoice)

		}//end while
		townChoice = 0;
	}
	/**
	 * a static function that takes in the player, the game map, and the direction the player decided to go in and performs 
	 * other game functions based on what location of the map the player ended up in, whether it be 'c' city, 'n' nothing, 'f' end of the map
	 * 'w' a wild pokemon, 'o' a random opponent
	 * @param direction
	 * @param player
	 * @param gameMap
	 */
	public static void travel(char direction, Player player, Map gameMap)
	{
		boolean flag = false;
		
		switch(direction)
		{
			case 'n':
				System.out.println("You encounter nothing and continue to follow the path...");
				break;
			case 'c':
				travelTown(player, 0, 0);
				break;
			case 'o':
				flag = OpponentBattle(player, OpponentMaker.makeRandomOpponent());
				if (flag == true)
					gameMap.removeOppAtLoc(player.getLocation());
				else
				{
					int runAway = (int) (Math.random() * 4) + 1;
					switch (runAway)
					{
						case 1:
							player.goNorth(gameMap);
							break;
						case 2:
							player.goEast(gameMap);
							break;
						case 3:
							player.goSouth(gameMap);
							break;
						case 4:
							player.goWest(gameMap);
							break;
					}//end runAway switch
				}//end else
				break;
			case 'w':
				flag = wildPokemonBattle(player, 0);
				if (flag == true)
					gameMap.removeOppAtLoc(player.getLocation());
				else
				{
					int runAway = (int) (Math.random() * 4) + 1;
					switch (runAway)
					{
						case 1:
							player.goNorth(gameMap);
							break;
						case 2:
							player.goEast(gameMap);
							break;
						case 3:
							player.goSouth(gameMap);
							break;
						case 4:
							player.goWest(gameMap);
							break;
					}//end runAway switch
				}//end else
				break;
			case 'f':
				System.out.println("You found a trail moving you on to the next region!");
				if (player.getMapNum() == 3)
				{
					player.setMapNum(1);
					gameMap.generateArea(player.getMapNum());
					player.setLocation(gameMap.findStartLocation());
				}//end if
				else
				{
					player.incMapNum();
					gameMap.generateArea(player.getMapNum());
					player.setLocation(gameMap.findStartLocation());
				}//end else
				File file = new File("pokemon.dat");
				try{
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					out.writeObject(player);
					out.close();
					System.out.println("Game Saved!");
				}catch(IOException e){
					System.out.println("Error processing file");
				}
				break;
			default:
				break;
		}//end direction switch
	}
	
}
