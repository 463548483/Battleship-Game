# Extra Feature

### 1. Network Game
        Players could join the game through network in different machine

### 2. Mutiple Room Handle
        Players could init their room when they enter init and specify the room name
        Players could join the exist room when they enter join and the room name
        Different rooms could play the game at the same time

### 3. Smart Computer
        Implement SmartComputerTextPlayer with 2 improvements:
            I. SmartComputerTextPlayer will take move action if its ship hit by enemy in the last round to avoid further hit;
            II. One SmartComputerTextPlayer hit a enemy ship at one coordinate, it will predict the possible coordinates near it and hits those coordinates first in the next several round;


# Run Command:

```java
./gradlew installdist

For Server: ./server/build/install/server/bin/server
For Client: ./client/build/install/client/bin/client playername hostname   ex:./client/build/install/client/bin/client b 127.0.0.1
