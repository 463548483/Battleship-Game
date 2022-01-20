package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_dispaly_empty() {
    Board<Character> b1=new BattleShipBoard<Character>(2,2);
    BoardTextView view=new BoardTextView(b1);
        String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    Coordinate pos=new Coordinate("B1");
    Ship toadd=new BasicShip(pos);
    b1.tryAddShip(toadd);
    String expected=
      "  0|1\n"+
      "A  |  A\n"+
      "B  |s B\n"+
      "  0|1\n";
    assertEquals(expected, view.displayMyOwnBoard());
  }
  

}
