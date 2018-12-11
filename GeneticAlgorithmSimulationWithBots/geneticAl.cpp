//Lucas D'Avila
//CISP400
//May 16, 2016
//
#include <iostream>
#include <time.h>
#include <vector>
#include <string>
#define MAX 1000
using namespace std;

int map[10][10]; //( x, y )
int temp[10][10];

class Bot
{
private:
	
	//DNA
	int north;
	int west;
	int east;
	int south;
	
	
public:
	int power;
	int peak;
	//Random Location on the map
	int think[81];
	//Array of random decision based off different possible locations. (ex: N = 0, W = 0, E = 0, S = 0; Decision[0] = #1-4)
	int x;
	int y;
	Bot();
	Bot(const Bot &x, const Bot &y);
	void look();
	void senseNorth();
	void senseWest();
	void senseEast();
	void senseSouth();
	void decide();
	void move(int a);
	void checkBattery();
	void checkPeak();
	int checkPowerLevel();
	void Go();
};

Bot::Bot()
{
	int a, b;
	a = rand() % 10;
	b = rand() % 10;
	//Will move the bot if he spawns on the wall
	if ( a == 0 )
		a++;
	if ( b == 0 )
		b++;
	if ( a == 9 )
		a--;
	if ( b == 9 )
		b--;

	x = a;
	y = b;
	//Initializations
	power = 5;
	peak = 5;

	north = 0;
	west = 0;
	east = 0;
	south = 0;
	//Randomizes the decsions for the 16 different possiblilties on map (0 = MN, 1 = MW, 2 = ME, 3 = MS)
	for (int i = 0; i < 81; i++)
	{
		think[i] = rand() % 4;
	}
}

Bot::Bot(const Bot &m, const Bot &f)
{
	int a, b;
	a = rand() % 10;
	b = rand() % 10;
	//Will move the bot if he spawns on the wall
	if ( a == 0 )
		a++;
	if ( b == 0 )
		b++;
	if ( a == 9 )
		a--;
	if ( b == 9 )
		b--;

	x = a;
	y = b;
	//Initializations
	power = 5;
	peak = 5;

	north = 0;
	west = 0;
	east = 0;
	south = 0;

	for (int i = 0; i < 41; i++)
		think[i] = m.think[i];
	for (int j = 41; j < 81; j++)
		think[j] = f.think[j];
}

void Bot::senseSouth()
{
	south = map[x][y-1];
	return;
}
void Bot::senseNorth()
{
	north = map[x][y+1];
	return;
}
void Bot::senseEast()
{
	east = map[x+1][y];
	return;
}
void Bot::senseWest()
{
	west = map[x-1][y];
	return;
}

void Bot::look()
{
	senseSouth();
	senseEast();
	senseWest();
	senseNorth();

	return;
}

void Bot::move(int a)
{
	//Move North and checks if it hits wall
	if (a == 0)
	{
		y++;
		if (y == 9)
		{
			y = 8;
			power = power - 3;
		}
		else
			power--;
	}
	//Move West and checks if it hits wall
	if (a == 1)
	{
		x--;
		if (x == 0)
		{
			x = 1;
			power = power - 3;
		}
		else
			power--;
	}
	//Move East and checks if it hits wall
	if (a == 2)
	{
		x++;
		if (x == 9)
		{
			x = 8;
			power = power - 3;
		}
		else
			power--;
	}
	//Move South and checks if it hits wall
	if (a == 3)
	{
		y--;
		if (y == 0)
		{
			y = 1;
			power = power - 3;
		}
		else
			power--;
	}
	return;
}

void Bot::decide()
{
	//This sets up every possibilty of the Bot DNA and has a random action depending on which variation it senses from the map
	if (north == 0 && east == 0 && west == 0 && south == 0)
		move(think[0]);
	if (north == 0 && east == 0 && west == 0 && south == 1)
		move(think[1]);
	if (north == 0 && east == 0 && west == 0 && south == 2)
		move(think[2]);
	if (north == 0 && east == 0 && west == 1 && south == 0)
		move(think[3]);
	if (north == 0 && east == 0 && west == 1 && south == 1)
		move(think[4]);
	if (north == 0 && east == 0 && west == 1 && south == 2)
		move(think[5]);
	if (north == 0 && east == 0 && west == 2 && south == 0)
		move(think[6]);
	if (north == 0 && east == 0 && west == 2 && south == 1)
		move(think[7]);
	if (north == 0 && east == 0 && west == 2 && south == 2)
		move(think[8]);
	if (north == 0 && east == 1 && west == 0 && south == 0)
		move(think[9]);
	if (north == 0 && east == 1 && west == 0 && south == 1)
		move(think[10]);
	if (north == 0 && east == 1 && west == 0 && south == 2)
		move(think[11]);
	if (north == 0 && east == 1 && west == 1 && south == 0)
		move(think[12]);
	if (north == 0 && east == 1 && west == 1 && south == 1)
		move(think[13]);
	if (north == 0 && east == 1 && west == 1 && south == 2)
		move(think[14]);
	if (north == 0 && east == 1 && west == 2 && south == 0)
		move(think[15]);
	if (north == 0 && east == 1 && west == 2 && south == 1)
		move(think[16]);
	if (north == 0 && east == 1 && west == 2 && south == 2)
		move(think[17]);
	if (north == 0 && east == 2 && west == 0 && south == 0)
		move(think[18]);
	if (north == 0 && east == 2 && west == 0 && south == 1)
		move(think[19]);
	if (north == 0 && east == 2 && west == 0 && south == 2)
		move(think[20]);
	if (north == 0 && east == 2 && west == 1 && south == 0)
		move(think[21]);
	if (north == 0 && east == 2 && west == 1 && south == 1)
		move(think[22]);
	if (north == 0 && east == 2 && west == 1 && south == 2)
		move(think[23]);
	if (north == 0 && east == 2 && west == 2 && south == 0)
		move(think[24]);
	if (north == 0 && east == 2 && west == 2 && south == 1)
		move(think[25]);
	if (north == 0 && east == 2 && west == 2 && south == 2)
		move(think[26]);
	if (north == 1 && east == 0 && west == 0 && south == 0)
		move(think[27]);
	if (north == 1 && east == 0 && west == 0 && south == 1)
		move(think[28]);
	if (north == 1 && east == 0 && west == 0 && south == 2)
		move(think[29]);
	if (north == 1 && east == 0 && west == 1 && south == 0)
		move(think[30]);
	if (north == 1 && east == 0 && west == 1 && south == 1)
		move(think[31]);
	if (north == 1 && east == 0 && west == 1 && south == 2)
		move(think[32]);
	if (north == 1 && east == 0 && west == 2 && south == 0)
		move(think[33]);
	if (north == 1 && east == 0 && west == 2 && south == 1)
		move(think[34]);
	if (north == 1 && east == 0 && west == 2 && south == 2)
		move(think[35]);
	if (north == 1 && east == 1 && west == 0 && south == 0)
		move(think[36]);
	if (north == 1 && east == 1 && west == 0 && south == 1)
		move(think[37]);
	if (north == 1 && east == 1 && west == 0 && south == 2)
		move(think[38]);
	if (north == 1 && east == 1 && west == 1 && south == 0)
		move(think[39]);
	if (north == 1 && east == 1 && west == 1 && south == 1)
		move(think[40]);
	if (north == 1 && east == 1 && west == 1 && south == 2)
		move(think[41]);
	if (north == 1 && east == 1 && west == 2 && south == 0)
		move(think[42]);
	if (north == 1 && east == 1 && west == 2 && south == 1)
		move(think[43]);
	if (north == 1 && east == 1 && west == 2 && south == 2)
		move(think[44]);
	if (north == 1 && east == 2 && west == 0 && south == 0)
		move(think[45]);
	if (north == 1 && east == 2 && west == 0 && south == 1)
		move(think[46]);
	if (north == 1 && east == 2 && west == 0 && south == 2)
		move(think[47]);
	if (north == 1 && east == 2 && west == 1 && south == 0)
		move(think[48]);
	if (north == 1 && east == 2 && west == 1 && south == 1)
		move(think[49]);
	if (north == 1 && east == 2 && west == 1 && south == 2)
		move(think[50]);
	if (north == 1 && east == 2 && west == 2 && south == 0)
		move(think[51]);
	if (north == 1 && east == 2 && west == 2 && south == 1)
		move(think[52]);
	if (north == 1 && east == 2 && west == 2 && south == 2)
		move(think[53]);
	if (north == 2 && east == 0 && west == 0 && south == 0)
		move(think[54]);
	if (north == 2 && east == 0 && west == 0 && south == 1)
		move(think[55]);
	if (north == 2 && east == 0 && west == 0 && south == 2)
		move(think[56]);
	if (north == 2 && east == 0 && west == 1 && south == 0)
		move(think[57]);
	if (north == 2 && east == 0 && west == 1 && south == 1)
		move(think[58]);
	if (north == 2 && east == 0 && west == 1 && south == 2)
		move(think[59]);
	if (north == 2 && east == 0 && west == 2 && south == 0)
		move(think[60]);
	if (north == 2 && east == 0 && west == 2 && south == 1)
		move(think[61]);
	if (north == 2 && east == 0 && west == 2 && south == 2)
		move(think[62]);
	if (north == 2 && east == 1 && west == 0 && south == 0)
		move(think[63]);
	if (north == 2 && east == 1 && west == 0 && south == 1)
		move(think[64]);
	if (north == 2 && east == 1 && west == 0 && south == 2)
		move(think[65]);
	if (north == 2 && east == 1 && west == 1 && south == 0)
		move(think[66]);
	if (north == 2 && east == 1 && west == 1 && south == 1)
		move(think[67]);
	if (north == 2 && east == 1 && west == 1 && south == 2)
		move(think[68]);
	if (north == 2 && east == 1 && west == 2 && south == 0)
		move(think[69]);
	if (north == 2 && east == 1 && west == 2 && south == 1)
		move(think[70]);
	if (north == 2 && east == 1 && west == 2 && south == 2)
		move(think[71]);
	if (north == 2 && east == 2 && west == 0 && south == 0)
		move(think[72]);
	if (north == 2 && east == 2 && west == 0 && south == 1)
		move(think[73]);
	if (north == 2 && east == 2 && west == 0 && south == 2)
		move(think[74]);
	if (north == 2 && east == 2 && west == 1 && south == 0)
		move(think[75]);
	if (north == 2 && east == 2 && west == 1 && south == 1)
		move(think[76]);
	if (north == 2 && east == 2 && west == 1 && south == 2)
		move(think[77]);
	if (north == 2 && east == 2 && west == 2 && south == 0)
		move(think[78]);
	if (north == 2 && east == 2 && west == 2 && south == 1)
		move(think[79]);
	if (north == 2 && east == 2 && west == 2 && south == 2)
		move(think[80]);

	return;
}


void Bot::checkBattery()
{
	//Checks if Bot lands on batter pack
	if (map[x][y] == 2)
		{
			power = power + 3;
			map[x][y] = 1;
		}

	return;
}

void Bot::checkPeak()
{
	//Going to help keep treack of peak power value for breeding. Possible return value?
	if (peak < power)
		peak = power;

	return;
}

int Bot::checkPowerLevel()
{
	//Bool test to see if the Bot is alive or not
	if (power == 0 || power < 0)
	{
		cout << "\nBot Died";
		return 0;
	}
	else
		return 1;
}

void Bot::Go()
{
	while(checkPowerLevel() == 1)
	{
		checkBattery();
		look();
		decide();
		checkBattery();
		checkPeak();
	}
	power = 5;
	cout << "\nThis Bots peak amount of power was: " << peak << "\n";
	return;
}

bool operator<(const Bot &s1, const Bot &s2)
{
	if (s1.peak < s2.peak)
		return true;
	else
		return false;
}

void genMap()
{
	for (int i = 0; i < 10; i++)
	{
		for (int j = 0; j < 10; j++)
			map[i][j] = 0;
	}
	//Builds walls around map, walls = 1
	for (int i = 0; i < 10; i++)
	{
		map[i][0] = 1;
		map[i][9] = 1;
		map[0][i] = 1;
		map[9][i] = 1;
	}
	for (int i = 1; i < 9; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			int a = rand() % 10;
			//Makes sure the random number doesn't fall on the walls
			while (a == 0 || a == 9)
				a = rand() % 10;

			if (map[a][i] != 2)
				map[a][i] = 2;
		}
	}
	for (int i = 0; i < 10; i++)
	{
		//Sets up a temp map to hold locations of the batteries
		for (int j = 0; j < 10; j++)
			temp[i][j] = map[i][j];
	}
	cout << "The map has been generated!\n";
	cout << "\n\tMAP\n";
	cout << "---------------------------\n";
	for (int i = 0; i < 10; i++)
	{
		for (int j = 0; j < 10; j++)
		{
			if (map[j][i] == 1)
				cout << "*\t";
			if (map[j][i] == 2)
				cout << "B\t";
			if (map[j][i] == 0)
				cout << "\t";
		}
		cout << "\n";
	}
	return;
}

int main()
{
	int average[MAX];
	int firstBotpeak = 0;
	int lastBotpeak = 0;
	srand((unsigned)time(NULL));
	Bot botFamily[200];

	for (int i = 0; i < MAX; i++)
		average[i] = 0;

	genMap();
	//Generates 200 1st Gen Bots
	for (int i = 0; i < 200; i++)
	{
		Bot bot;
		botFamily[i] = bot;
	}


	//Run 200 1st Gen Bots
	for (int i = 0; i < 200; i++)
	{
		genMap();
		botFamily[i].Go();
		botFamily[i].power = 5;
		for (int x = 0; x < 10; x++)
		{
			for (int y = 0; y < 10; y++)
				map[x][y] = temp[x][y];
		}
		average[0] = average[0] + botFamily[i].peak;
	}
	average[0] = average[0] / 200;
	cout << "\n1st Gen Average is: " << average[0] << "\n";
	//SORTED AND CHECKED OKAY
	sort(botFamily, botFamily + 200);
	cout << "1st Gen is sorted\n";
	for (int i = 0; i < 200; i++)
	{
		cout << i + 1 << " bot peak: " << botFamily[i].peak;
		cout << "\n";
	}

	firstBotpeak = botFamily[0].peak;
	//BREED
	for (int i = 0; i < 100; i++)
		botFamily[i] = Bot(botFamily[i+100], botFamily[i+101]);
	//RUNS REST OF THE GENERATIONS
	for (int i = 1; i < MAX; i++)
	{
		genMap();
		for (int j = 0; j < 200; j++)
		{
			botFamily[j].Go();
			botFamily[j].power = 5;
			for (int x = 0; x < 10; x++)
			{
				for (int y = 0; y < 10; y++)
					map[x][y] = temp[x][y];
			}
			average[i] = average[i] + botFamily[j].peak;
		}
		average[i] = average[i] / 200;

		sort(botFamily, botFamily + 200);
		cout << i+1 << "Gen is sorted\n";
		for (int k = 0; k < 200; k++)
			cout << k + 1 << " Bot peak: " << botFamily[k].peak << "\n";

	}

	lastBotpeak = botFamily[199].peak;
	
	for (int i = 0; i < MAX; i++)
		cout << "BOT AVERAGE\nGen " << i+1 << ": " << average[i] << "\n";
	cout << "\n\n\n\n\n";
	cout << "Power Collected by 1st Bot 1st Generation: " << firstBotpeak << "\n";
	cout << "Average Peak Power by 1st Generation: " << average[0] << "\n";
	cout << "\nPower Colleceted by Last Bot "<< MAX << "th Generation: " << lastBotpeak << "\n";
	cout << "Average Peak Power by "<< MAX << "th Generation: " << average[MAX - 1] << "\n";


	return 0;

}
//
// Peer Review Summary Form
// Complete and attach to the bottom of your source file and turn in to D2L.
//
// Reader: Yusuf Amani
// Recorder: Janet Jones
// Other: 
//
// Approx Time for this asignment: 2 weeks
// Use the following ranking for the following:
// 5 - Sophisticated, 4 - Highly Competent, 3 - Competent, 2 - Not Yet Competent, 1 - Unacceptable 
// Solution Fit with Client Needs: 5
// User Friendliness: 5
// Comments and Documentation: 5
// Overall Score: 5

