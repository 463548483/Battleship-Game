package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_dispaly_empty() {
    Board b1=new BattleShipBoard(2,2);
    BoardTextView view=new BoardTextView(b1);
        String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      "  0|1\n"+
      "A  |  A\n"+
      "B  |  B\n"+
      "  0|1\n";
    assertEquals(expected, view.displayMyOwnBoard());
  }
  

}
