package pokemon;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
/**
 * a class that creates and loads a game map for the trainer to travel on during the game by only displaying areas the
 * trainer has already been on along with cities he travel to. The map also holds different pokemon and trainers that the
 * player can do battle with
 * @author Lucas
 *
 */
public class Map implements Serializable
{
	/**
	 * the map generated from a file that the trainer will play on
	 */
	private char [][] map;
	/**
	 * what the player can actually see on the map while playing
	 */
	private boolean [][] revealed;
	/**
	 * default constructor that sets the size of the map
	 */
	public Map()
	{
		map = new char[5][5];
		revealed = new boolean [5][5];
	}
	/**
	 * generates a map based on the given number from a text file along with setting what can and can't be seen
	 * by the player 
	 * @param map number
	 */
	public void generateArea(int mapNum)
	{
		char[] temp = new char[5];
		String mapLine;
		int count = 0;
		int count2;
		
		try{
			Scanner read = new Scanner(new File("Area" + mapNum + ".txt"));
			while(read.hasNextLine())
			{
				count2 = 0;
				mapLine = read.nextLine();
				temp = mapLine.toCharArray();
				for(int i = 0; i < 5; i++)
				{
					map[count][i] = temp[count2];
					count2 = count2 + 2;
				}//end for
				count++;
			}//end while
			read.close();
		}catch(IOException e){
			System.out.println("ERROR");
		}
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(map[i][j] == 's' || map[i][j] == 'c')
					revealed[i][j] = true;
				else
					revealed[i][j] = false;
			}//end for
		}//end for
	}
	/**
	 * displays the entire map of the region along with what point the player is currently on
	 * @param where the player is
	 */
	public void displayMap(Point p)
	{
		System.out.println(" ----------- ");
		for(int i = 0; i < 5; i++)
		{
			System.out.print("| ");
			for(int j = 0; j < 5; j++)
			{
				if((int)p.getX() == i && (int)p.getY()== j)
					System.out.print("* ");
				else if(revealed[i][j] == true )
					System.out.print(map[i][j] + " ");
				else
					System.out.print("x ");
			}//end for
			System.out.println("|");
		}//end for
		System.out.println(" ----------- ");
	}
	/**
	 * finds the starting location on the current map and returns it
	 * @return starting location
	 */
	public Point findStartLocation()
	{
		Point start = new Point();
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(map[i][j] == 's')
					start.setLocation(i, j);
			}//end for
		}//end for
		
		//displayMap(new Point(0,0));
		
		return start;
	}
	/**
	 * returns the character at a given specific location on the map
	 * @param point on map
	 * @return char at given point
	 */
	public char getCharAtLoc(Point p)
	{
		char location = 'x';
		
		if((int)p.getX() >= 0 && (int)p.getX() < 5 && (int)p.getY() >= 0 && (int)p.getY() < 5)
			location = map[(int)p.getX()][(int)p.getY()];
		
		return location;
	}
	/**
	 * reveals the character at a given point
	 * @param point
	 */
	public void reveal(Point p)
	{
		revealed[(int)p.getX()][(int)p.getY()] = true;
	}
	/**
	 * removes a wild pokemon or opponent at a given point
	 * @param point
	 */
	public void removeOppAtLoc(Point p)
	{
		char temp;
		temp = map[(int)p.getX()][(int)p.getY()];
		
		if(temp == 'o' || temp == 'w')
			map[(int)p.getX()][(int)p.getY()] = 'n';
		else
			System.out.println("Error. Can't Remove.");
	}
	
}
