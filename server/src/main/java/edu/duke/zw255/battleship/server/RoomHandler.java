package edu.duke.zw255.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import edu.duke.zw255.battleship.shared.*;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RoomHandler implements Runnable {
    private String roomName;
    private ServerSocket serverSocket;
    protected int playerNum;
    protected Messenger[] playerMessengers;
    private Board[] boards;
    private BoardTextView[] views;
    protected static int roomContainer=2;

    public RoomHandler(String roomName, ServerSocket serverSocket) {
        this.roomName = roomName;
        this.serverSocket = serverSocket;
        this.playerNum = 0;
        playerMessengers = new Messenger[roomContainer];
        boards = new Board[roomContainer];
        views = new BoardTextView[roomContainer];
    }

    @Override
    public void run() {
        System.out.println("All players are connected");
        try {
            sendStartForPlayers();
            doAttackingPhaseForPlayers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public void acceptOnePlayer(Messenger messenger) {
        System.out.println(playerNum+" player connected");
        playerMessengers[playerNum] = messenger;
        playerNum++;    
    }

    public void sendStartForPlayers() throws IOException {
        for (int i = 0; i < roomContainer; i++) {
            playerMessengers[i].send(Flag.startFlag);
            System.out.println(playerMessengers[i]);
            System.out.println("send start to "+i);
        }
    }

    /**
     * This function make the server receive the updated gameMap.
     * 
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void doAttackingPhaseForPlayers() throws ClassNotFoundException, IOException {
        while (true) {
            for (int i = 0; i < roomContainer; i++) {
                boards[i] = (Board) playerMessengers[i].recv();
                views[i] = (BoardTextView) playerMessengers[i].recv();
                System.out.println("recv board from " + i);
                playerMessengers[i].send(Flag.correctFlag);
            }
            for (int i = 0; i < roomContainer; i++) {
                if (boards[i].isLose()) {
                    playerMessengers[i].send("You are lose");
                    playerMessengers[roomContainer - 1 - i].send("You are Win");
                    break;
                }
            }
            for (int i = 0; i < roomContainer; i++) {
                playerMessengers[i].send(boards[roomContainer - 1 - i]);
                playerMessengers[i].send(views[roomContainer - 1 - i]);
                System.out.println("send enemy board to " + i);
            }
        }

    }

}
