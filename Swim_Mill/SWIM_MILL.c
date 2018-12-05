#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <signal.h>
#include <time.h>
#include <semaphore.h>

#define FISH_INIT_POS 94 // 94 % 10 = 4 (x-axis) & 94 / 10 = 9 (y-axis) -> [4, 9]
#define BASE 10 //Base number that I divide and mode the Pellet and Fish positions
#define X 10 //X axis max for SWIM MILL
#define Y 10 //Y axis max for SWIM MILL
#define MAX_PELLETS 10
#define TIME_LIMIT 30 //Amount of time process will run for
#define MAX_PROCESS 20 //Max number of processes allowed
#define SEM_LOCATION "/semaphore"

//signal handler to abort and kill processes
int signalFlag = 1;
void sighandler(int signum);
//created semaphore and created function to remove
//semaphore from memory
sem_t(*semaphore);
void UnlinkSemaphore();

int main(int argc, const char *argv[]) {

    int swim_mill[X][Y];
    int fishProcess = 0;
    int pelletProcess[MAX_PELLETS]; 
    int timePassed = 0;
    int pelletIDIndex = 0;
    char *shmaddr;
    int shmid;

    //to kill the process
    signal(SIGINT, sighandler);
    //Will create memory to be shared between FISH and PELLET  with a size of 22
    //With 22, a max of 20 processes can be handled... 19 positions for Fish + Pellet...
    //+ a location to keep track of the number of processes being handled... + some buffer
    if((shmid = shmget(ftok(".", 'a'), sizeof(char[22]), IPC_CREAT | 0660)) == -1) {
        perror("could not create memory segment.");
    }
    shmaddr = shmat(shmid, NULL, 0);
    //Initializes all the memory loctions to -1, because could be a potential location
    //for a pellet or the fish
    for(int i = 0; i < 22; i++) {
        shmaddr[i] = -1;
    }
    //Initialize array to store all pellet processes
    for (int i = 0; i < MAX_PELLETS; i++) {
        pelletProcess[i] = 0;
    }
    //Initializes memory location 0 to store the initial position of the fish
    shmaddr[0] = FISH_INIT_POS;
    shmaddr[21] = 2; // shaddr[21] holds the number of current active processes
    //Initializes semaphore
    if((semaphore = sem_open(SEM_LOCATION, O_CREAT, 0644, 1)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }

    fishProcess = fork();
    if (fishProcess == -1) {
        perror("Fish Process Failed.\n");
    } else if (fishProcess == 0) {
        execv("fish", NULL);
    }
    //While the amount of time passed is less then or equal to the time limit
    //...30 secs... this while loop will continue.  The wile loop will keep 
    //creating pellet processes until the max number allowed is reached. if that
    //number is reached will stop creating pellets until the number of pellets is
    //decremented.
    //The while loop then copies the locations of the pellets and the fish into
    //swim mill, a 2D array, and displays it on the terminal.  The X and Y locations
    //of the pellets and fish are determined by a number between 0-99 which is then
    // /10 and %10 to get the corresponding X and Y values.
    //After it displays the while loop sleeps for 1 second and then increments the
    //amount of time passed
    //By Utilizing semaphore, SWIM_MILL 'downs' the semaphore when entering the
    //shared memory location to copy all of its contents into the "swim mill".
    //Once all the locations have been obtained, the semaphore is then "upped".
    while(timePassed <= TIME_LIMIT) {
        if(shmaddr[21] < MAX_PROCESS) {
            shmaddr[21]++;
            pelletProcess[pelletIDIndex] = fork();
            if(pelletProcess[pelletIDIndex] == -1) {
                perror("Pellet Process Failed.\n");
            } else if(pelletProcess[pelletIDIndex] == 0) {
                execv("pellet", NULL);
            }
            pelletIDIndex++;
        }

        for(int i = 0; i < X; i++) {
            for(int j = 0; j < Y; j++) {
                swim_mill[i][j] = ' ';
            }
        }
        //SWIM_MILL enters its critical region.
        sem_wait(semaphore);
        for(int k = 1; k < MAX_PELLETS; k++) {
            if(shmaddr[k] > -1 && shmaddr[k] < 100) {
                swim_mill[shmaddr[k] / BASE][shmaddr[k] % BASE] = 'P';
            }
        }
        swim_mill[shmaddr[0] / BASE][shmaddr[0] % BASE] = 'F';
        sem_post(semaphore);

        for(int i = 0; i < X; i++) {
            for(int j = 0; j < Y; j++) {
                printf("%c", swim_mill[i][j]);
            }
            printf("\n");
        }

        if (signalFlag == 0) {
            break;
        }

        sleep(1);
        timePassed++;
    }
    //Once the signal flag is set to 0 the swim mill kills and aborts the fish
    //and all the pellet processes.
    if (signalFlag == 0) {
        kill(fishProcess, SIGINT);
        for (int i = 0; i < MAX_PELLETS; i++) {
            if(pelletProcess[i] != 0) {
                kill(pelletProcess[i], SIGINT);
            }
        }
    }
    else {
        kill(fishProcess, SIGUSR1);
        for (int i = 0; i < MAX_PELLETS; i++) {
            if(pelletProcess[i] != 0) {
                kill(pelletProcess[i], SIGUSR1);
            }
        }
    }

    if(sem_close(semaphore) == -1) {
        perror("sem_close");
        exit(EXIT_FAILURE);
    }
    UnlinkSemaphore();
    //Cleans up and deletes the shared memory space
    shmdt(shmaddr);
    shmctl(shmid, IPC_RMID, NULL);

    printf("Swim Mill is finished.\n");

    return 0;
}

//Clears semaphore from memory
void UnlinkSemaphore() {
    if (sem_unlink(SEM_LOCATION) == -1) {
        perror("sem_unlink");
        exit(EXIT_FAILURE);
    }
}

void sighandler(int signum) {
    if(signum == SIGINT) {
        signalFlag = 0;
    }
}