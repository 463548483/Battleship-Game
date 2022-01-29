package edu.duke.zw255.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final String name;
    final Board<Character> theBoard;
      final BoardTextView view;
      final BufferedReader inputReader;
      final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String,Function<Placement,Ship<Character>>> shipCreationFns;
  
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,AbstractShipFactory<Character> shipFactory) {
    this.name=name;//player's name
    this.theBoard = theBoard;//own board
    this.view = new BoardTextView(theBoard);
    this.inputReader =inputSource;
    this.out = out;
    this.shipFactory=shipFactory;
    this.shipsToPlace=new ArrayList<String>();
    this.shipCreationFns=new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
  setupShipCreationList();
    
  }

  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine",(p)->shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer",(p)->shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship",(p)->shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier",(p)->shipFactory.makeCarrier(p));
    
  }
  protected void setupShipCreationList(){
     shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
     shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
     shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
     shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
 
  }
  
  public void doPlacementPhase() throws IOException{
    out.print(view.displayMyOwnBoard());
    String prompt="Player "+name+": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n \"Submarines\" ships that are 1x2 \n \"Destroyers\" that are 1x3 \n \"Battleships\" that are 1x4 \n \"Carriers\" that are 1x6\n";
    out.print(prompt);
    for (String s:shipsToPlace){
      doOnePlacement(s,shipCreationFns.get(s));
    }
  }

  
  public Placement readPlacement(String prompt) throws IOException {
    while(true){
      try{
        out.println(prompt);
        String s = inputReader.readLine();
        if (s==null){
          throw new EOFException("Emptry Input");
        }
        return new Placement(s);
        
      }catch(IllegalArgumentException e){
        out.println(e.getMessage());
      }
    }
  }

  
  public void doOnePlacement(String shipName,Function<Placement,Ship<Character>> createFn) throws IOException{
    while(true){
      try{
        String prompt="Player "+name+" where would you like to put your "+shipName+" ?";
        Placement p=readPlacement(prompt);
        Ship<Character> p1=createFn.apply(p);
        String res=theBoard.tryAddShip(p1);
        if (res!=null){
          throw new IllegalArgumentException(res);
        }
        break;
      }catch(IllegalArgumentException e){
        out.println(e.getMessage());
      }
    }
    out.print(view.displayMyOwnBoard());
    
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException{
    String myHeader="Your ocean";
    String enemyHeader="Enemy's ocean";
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    fireAction(enemyBoard);
   
  }
  public Coordinate readCoordinate(String prompt) throws IOException{
    out.println(prompt);
    String s = inputReader.readLine();
    if (s==null){
      throw new EOFException("Emptry Input");
    }
    return new Coordinate(s);
  }

  public void fireAction(Board<Character> enemyBoard) throws IOException{
    while(true){
    try{
      Coordinate c= readCoordinate("Player "+name+", where do you want fire at?");
      Ship<Character> ship=enemyBoard.fireAt(c);
      
        if (ship!=null){
          out.println("You hit a "+ship.getName()+"!");
        }
        else{
          out.println("You missed!");
        }
      if (enemyBoard.isLose()){
        out.println("Player "+name+" have won!");
        System.exit(0);
      }
      break;

    }catch(IllegalArgumentException e){
      out.println("Please enter valid choice");
    }
  }
  }
}
