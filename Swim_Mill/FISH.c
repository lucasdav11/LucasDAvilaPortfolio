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

#define MIN_START 1000 //sets the initiak min to find the manhattan distance very high 
#define SEM_LOCATION "/semaphore"

int signalFlag = 1;
void sighandler(int signum);
//created semaphore and created function to remove
//semaphore from memory
sem_t(*semaphore);

int main(int agrc, const char *argv[]) {
    char *shmaddr;
    int shmid;
    int smallestDistance = 0;//keeps track of the current smallest distance from Fish to a pellet
    int bestLane = 0; //keeps track of the best possible lane discovered per iteration
    int manhattanDistance = 0;//distance from current pellet to fish

    printf("Fish Process Started.\n");
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
    //This while loop sets the smallest distance to the Fish as 1000 and initializes the best lane
    //it then constantly calculates the manhattan distance for each pellet in shared memory to the fish
    //keeping track of the smallest distance.  Once the absolute smallest distance is found it % by the
    //base 10 to find the best lane or best row.  Then moves fish left or right depending on what row
    //the best pellet is and where the fish is. Finally it sleeps for 1 sec to stay on the same pace as
    //the pellet process and swim mill process.
    //
    //By using semaphores the fish enters its critical region when it access any of the shared memory
    //So before the fish checks the best distance between it and all the pellets and then moves its
    //location the semaphore is "downed".  Then once all of that has been completed the fish releases
    //the semaphore.
    while(1) {
        smallestDistance = MIN_START;
        bestLane = 0;
        sem_wait(semaphore); //Entering critical region
        for (int i = 1; i < 11; i++) {
            if (shmaddr[i] > -1 && shmaddr[i] < 100) {
                manhattanDistance = abs((shmaddr[0]/10) - (shmaddr[i]/10)) + abs((shmaddr[0]%10) - (shmaddr[i]%10));
                if ( manhattanDistance < smallestDistance) {
                    smallestDistance = manhattanDistance;
                    bestLane = shmaddr[i] % 10;
                }
            }
        }
        if(bestLane < shmaddr[0] % 10) {
            shmaddr[0]--;
        }
        else if (bestLane > shmaddr[0] % 10) {
            shmaddr[0]++;
        }
        sem_post(semaphore); //Leaves critical region

        if(signalFlag == 0) {
            printf("Aborting Fish: %d\n", getpid());
            break;
        }
        sleep(1);
    }

    if(sem_close(semaphore) == -1) {
        perror("sem_close");
        exit(EXIT_FAILURE);
    }
    //cleans up memory
    shmdt(shmaddr);
    return 0;
}

void sighandler(int signum) {
    if (signum == SIGINT) {
        signalFlag = 0;
    }
    else if (signum == SIGUSR1) {
        printf("Killed Fish.\n");
        exit(1);
    }
}