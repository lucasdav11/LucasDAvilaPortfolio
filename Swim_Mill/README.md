# Swim Mill

This project demonstrates shared resources between multiple processes using semaphores.  The Swim Mill creates a Fish that
can only swim left or right at the bottom of the mill.  The Swim Mill process then creates several Pellet process that move
down the river towards the fish.  The Fish process must decide which pellet is the closest to its current location and move
towards it to eat the pellet.  Every pellet process is terminated after each pellet is either eaten or missed.  The Swim Mill
process then terminates every process after 30 seconds or after an interrupt signal is given. Each process uses shared memory
to store the location of the fish and each individual pellet and use semaphores for turn control when each process accesses
said shared memory.

## Built With

* [VSCode](https://code.visualstudio.com/)
