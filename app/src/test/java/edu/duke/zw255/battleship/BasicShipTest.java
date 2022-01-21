package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_Basicship() {
    RectangleShip<Character> p1=new RectangleShip<Character>(new Coordinate(2,1),'s','*');
    assertEquals(true,p1.myPieces.containsKey(new Coordinate(2,1)));
    Coordinate where=new Coordinate(2,5);
    RectangleShip<Character> p2=new RectangleShip<Character>(where,'s','*');
    
    for (Coordinate c:p2.myPieces.keySet()){
      assertEquals(true,p2.myPieces.containsKey(c));
      
    }
  }

}
