package edu.duke.zw255.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import edu.duke.zw255.battleship.shared.*;

import java.util.List;
import java.util.Map;

public class RoomHandler implements Runnable {
    private String roomName;
    private ServerSocket serverSocket;
    protected int playerNum;
    protected Map<Integer, Messenger> playerMessengers;
    private Board[] boards;
    private BoardTextView[] views;
    protected static int roomContainer = 2;

    public RoomHandler(String roomName, ServerSocket serverSocket) {
        this.roomName = roomName;
        this.serverSocket = serverSocket;
        this.playerNum = 0;
        playerMessengers = new ConcurrentHashMap<>();
        boards = new Board[roomContainer];
        views = new BoardTextView[roomContainer];
    }

    @Override
    public void run() {
        System.out.println("All players are connected");
        try {
            StartGameForPlayers();
            doAttackingPhaseForPlayers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public void acceptOnePlayer(Messenger messenger) {
        System.out.println(playerNum + " player connected");
        playerMessengers.put(playerNum, messenger);
        playerNum++;
    }

    public void StartGameForPlayers() throws IOException, ClassNotFoundException {
        sendHelper(Flag.startFlag);
        for (int i = 0; i < roomContainer; i++) {
            boards[i] = (Board) playerMessengers.get(i).recv();
            views[i] = (BoardTextView) playerMessengers.get(i).recv();
        }
        for (int i = 0; i < roomContainer; i++) {
            playerMessengers.get(i).send(boards[roomContainer - 1 - i]);
            playerMessengers.get(i).send(views[roomContainer - 1 - i]);
        }
    }

    private void sendHelper(Object o) throws IOException{
        for (int i = 0; i < roomContainer; i++) {
            playerMessengers.get(i).send(o);
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
                if (boards[i].isLose()) {
                    System.out.println("game end for room " + roomName);
                    sendHelper(Flag.endFlag);
                    if (boards[roomContainer - 1 - i].isLose()){
                        sendHelper("Game end as equal");
                    }
                    else{
                        playerMessengers.get(i).send("You are lose");
                        playerMessengers.get(roomContainer - 1 - i).send("You are Win");
                    }
                    return;
                }
            }
            for (int i = 0; i < roomContainer; i++) {
                playerMessengers.get(i).send(Flag.correctFlag);
                playerMessengers.get(i).send(boards[i]);
                playerMessengers.get(i).send(views[i]);
                System.out.println("send player board to " + i);
            }
            for (int i = 0; i < roomContainer; i++) {
                boards[roomContainer - 1 - i] = (Board) playerMessengers.get(i).recv();
                views[roomContainer - 1 - i] = (BoardTextView) playerMessengers.get(i).recv();
            }
        }

    }

}
