package edu.duke.zw255.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import edu.duke.zw255.battleship.shared.*;

import java.util.*;
import java.util.Map;

public class RoomHandler implements Runnable {
    private String roomName;
    private ServerSocket serverSocket;
    protected int playerNum;
    protected Map<Integer, Messenger> playerMessengers;
    public BattleShipBoard[] boards;
    //public BoardTextView[] views;
    public ArrayList<HashSet<Coordinate> > myMiss;
    public ArrayList<LinkedHashMap<Coordinate,Character> > myHit;
    public ArrayList<ArrayList<Ship<Character> > > myShips;
    public static int roomContainer = 2;

    public RoomHandler(String roomName, ServerSocket serverSocket) {
        this.roomName = roomName;
        this.serverSocket = serverSocket;
        this.playerNum = 0;
        playerMessengers = new ConcurrentHashMap<>();
        boards = new BattleShipBoard[roomContainer];
        //views =new BoardTextView[roomContainer];
        myMiss=new ArrayList<HashSet<Coordinate> >();
        myHit=new ArrayList<LinkedHashMap<Coordinate,Character> >();
        myShips=new ArrayList<ArrayList<Ship<Character> > >();
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
            boards[i] = (BattleShipBoard) playerMessengers.get(i).recv();
            // System.out.println(boards[i]);
            //views[i] =(BoardTextView)playerMessengers.get(i).recv();
            myHit.add(i,null);
            myMiss.add(i,null);
            myShips.add(i,null);
        }
        for (int i = 0; i < roomContainer; i++) {
            playerMessengers.get(i).send(boards[roomContainer - 1 - i]);
           // playerMessengers.get(i).send(views[roomContainer - 1 - i]);
        }
    }

    private void sendHelper(Object o) throws IOException{
        for (int i = 0; i < roomContainer; i++) {
            playerMessengers.get(i).send(o);
        }
    }

    public Boolean endGameDetection(Board board){
        if (board.isLose()){
            return true;
        }
        else{
            return false;
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
                myMiss.set(i,(HashSet<Coordinate>) playerMessengers.get(i).recv());
                myHit.set(i, (LinkedHashMap<Coordinate,Character>) playerMessengers.get(i).recv());
                myShips.set(i,(ArrayList<Ship<Character> >) playerMessengers.get(i).recv());
                boards[i].myShips=myShips.get(i);
            }
            for (int i = 0; i < roomContainer; i++) {
                if (endGameDetection(boards[i])) {
                    System.out.println("game end for room " + roomName);
                    sendHelper(Flag.endFlag);
                    if (endGameDetection(boards[roomContainer - 1 - i])){
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
                playerMessengers.get(i).send(myMiss.get((roomContainer-i-1)));
                playerMessengers.get(i).send(myHit.get((roomContainer-i-1)));
                playerMessengers.get(i).send(myShips.get((roomContainer-i-1)));
                System.out.println("send player board to " + i);
            }

        }

    }

}
