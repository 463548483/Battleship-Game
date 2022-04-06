package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_dispaly_empty() {
    Board<Character> b1=new BattleShipBoard<Character>(2,2,'X');
    BoardTextView view=new BoardTextView(b1);
        String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    Coordinate pos=new Coordinate("B1");
    Ship toadd=new RectangleShip<Character>(pos,'s','*');
    b1.tryAddShip(toadd);
    b1.fireAt(new Coordinate(0,0));
    b1.fireAt(new Coordinate(1,1));
              
    String expectedown=
      "  0|1\n"+
      "A  |  A\n"+
      "B  |* B\n"+
      "  0|1\n";
    assertEquals(expectedown, view.displayMyOwnBoard());
               
    String expectedenemy=
      "  0|1\n"+
      "A X|  A\n"+
      "B  |s B\n"+
      "  0|1\n";
  
      assertEquals(expectedenemy, view.displayEnemyBoard());
  }

  
  @Test
  public void test_mix(){
    Board<Character> b1=new BattleShipBoard<Character>(10, 2, 'X');
    Board<Character> b2=new BattleShipBoard<Character>(10, 2, 'X');

    String expected="\n"+
      "     Your ocean                           Player B's ocean\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | | | | | | | |  A                A  | | | | | | | | |  A\n"+
      "B  | | | | | | | | |  B                B  | | | | | | | | |  B\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n";
    BoardTextView own=new BoardTextView(b1);
    BoardTextView enemy=new BoardTextView(b2);
    String myHeader="Your ocean";
    String enemyHeader="Player B's ocean";
    assertEquals(expected, own.displayMyBoardWithEnemyNextToIt(enemy,myHeader,enemyHeader));
   
  }
  

}
