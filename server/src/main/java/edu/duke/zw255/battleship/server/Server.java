/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.zw255.battleship.server;
import edu.duke.zw255.battleship.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class Server {
  final TextPlayer player1;
  final TextPlayer player2;
  
  public Server(Board<Character> Board1,Board<Character> Board2, BufferedReader inputSource, PrintStream out,Boolean Aiscomputer,Boolean Biscomputer) {
    V2ShipFactory shipFactory=new V2ShipFactory();
    if (Aiscomputer){
      this.player1=new ComputerTextPlayer("A",Board1, inputSource, out, shipFactory);
    }
    else{
      this.player1=new TextPlayer("A",Board1, inputSource, out, shipFactory);
    }
    if (Biscomputer){
    this.player2=new ComputerTextPlayer("B",Board2, inputSource, out, shipFactory);
    
    }
    else{
      this.player2=new TextPlayer("B",Board2, inputSource, out, shipFactory);
    }
  }

  public void doPlacementPhase() throws IOException{
    player1.doPlacementPhase();
    player2.doPlacementPhase();
  }

  public void doAttackingPhase() throws IOException{
    while(true){
    player1.playOneTurn(player2.theBoard,player2.view);
    if (player2.theBoard.isLose()){
      break;
    }
    player2.playOneTurn(player1.theBoard, player1.view);
    if (player1.theBoard.isLose()){
      break;
    }
    }
  }

  

  public static void main(String[] args) throws IOException {
   Board b1=new BattleShipBoard<>(10, 20,'X');
   Board b2=new BattleShipBoard<>(10, 20,'X');
   BufferedReader inputSource=new BufferedReader(new InputStreamReader(System.in));
   PrintStream out=System.out;
   out.println("Welcome to Battleship Game!");
   
   Boolean Aiscomputer=false;
   Boolean Biscomputer=false;
   while(true){
     try{
     out.println("What would you like for Player A\n"+"0 for Human\n"+"1 for Computer");
     String s=inputSource.readLine();
     if (s.equals("0")){
       break;
     }
     else if (s.equals("1")){
       Aiscomputer=true;
       break;
     }
     else{
       throw new IllegalArgumentException("Invalid Input, please enter 0 or 1");
     }
     }catch(IllegalArgumentException e){
       out.println(e.getMessage());
     }
   }
   while(true){
     try{
     out.println("What would you like for Player B\n"+"0 for Human\n"+"1 for Computer");
     String s=inputSource.readLine();
     if (s.equals("0")){
       break;
     }
     else if (s.equals("1")){
       Biscomputer=true;
       break;
     }
     else{
       throw new IllegalArgumentException("Invalid Input, please enter 0 or 1");
     }
     }catch(IllegalArgumentException e){
       out.println(e.getMessage());
     }
   }
;
   
   Server server=new Server(b1,b2,inputSource,out,Aiscomputer,Biscomputer);
   server.doPlacementPhase();
   server.doAttackingPhase();
  }
}
