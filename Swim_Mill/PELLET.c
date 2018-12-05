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

#define SEM_LOCATION "/semaphore"

int signalFlag = 2;
void sighandler(int signum);
//created semaphore and created function to remove
//semaphore from memory
sem_t(*semaphore);

int main(int argc, const char *argv[]) {
    char *shmaddr;
    int shmid;
    srand(time(0));
    FILE *swimFile;
    //opens a file to append to or creates one if it doesn't exist
    swimFile = fopen("lab2.txt", "a");

    signal(SIGINT, sighandler);
    signal(SIGUSR1, sighandler);

    if((shmid = shmget(ftok(".", 'a'), sizeof(char[11]), IPC_CREAT | 0660)) == -1) {
        perror("could not create memory segment.");
    }
    shmaddr = shmat(shmid, NULL, 0);
    //Initializes semaphore
    if((semaphore = sem_open(SEM_LOCATION, O_CREAT, 0644, 1)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }
    //Keeps track of how many active pellet processes there are
    int pelletCount = 1;
    //Initiates a new pellet to a random location in the top row of the swim mill
    for (; pelletCount < 21; pelletCount++) {
        if(shmaddr[pelletCount] == -1) {
            shmaddr[pelletCount] = rand() % 10;
            break;
        }
    }
    //This while loop moves the pellets downward by a "multiple" of 10, since the base
    //is 10 until it reaches the last row, 90-99.  If pellet and the fish have the same
    //location that pellet is set to -1 and the pellet has been eaten.  The processID is
    //then logged.  If the Fish misses the pellet meaning the pellet value got way above 90
    //then the pellet is still set to -1 and is logged as missed in the file. If the signal
    //flag gets set to 0 then the pellet is aborted and killed.  The while loop then sleeps
    //1 second. Allowing the it to wait for the other processes.
    //
    //With semphores, each pellet process downs the semaphore when the pellet enters its
    //critical region.  It's critical region is when each pellet is moving
    //down and when the pellet is checking to see if it has been eaten by the fish or missed.
    while(1) {
        if(shmaddr[pelletCount] < 90) {
            sem_wait(semaphore); //Enters critical region
            shmaddr[pelletCount] += 10;
            sem_post(semaphore); //leaves critical region
        }
        else {
            if(shmaddr[pelletCount] == shmaddr[0]) {
                sem_wait(semaphore); //Enters critical region
                printf("Fish ate pellet: %d\n", getpid());
                fprintf(swimFile, "Fish ate pellet: ");
                fprintf(swimFile, "%d\n",getpid());
                shmaddr[pelletCount] = -1;
                sem_post(semaphore); //leaves critical region
                break;
            }
            else {
                sem_wait(semaphore); //Enters critical region
                printf("Missed pellet: %d\n", getpid());
                fprintf(swimFile, "Missed pellet: ");
                fprintf(swimFile, "%d\n",getpid());
                shmaddr[pelletCount] = -1;
                sem_post(semaphore); //Leaves critical region
                break;
            }
        }
        if(signalFlag == 0) {
            printf("Aborting Pellet: %d\n", getpid());
            break;
        }
        if(signalFlag == 1) {
            break;
        }
        sleep(1);
    }
    //Once a pellet is killed, the number of processes is decremented
    printf("Killed pellet: %d\n", getpid());
    shmaddr[21]--;

    if(sem_close(semaphore) == -1) {
        perror("sem_close");
        exit(EXIT_FAILURE);
    }
    //Cleans up shared memory and closes the file
    shmdt(shmaddr);
    fclose(swimFile);
    return 0;

}

void sighandler(int signum) {
    if(signum == SIGINT) {
        signalFlag = 0;
    }
    else if(signum == SIGUSR1) {
        signalFlag = 1;
    }
}