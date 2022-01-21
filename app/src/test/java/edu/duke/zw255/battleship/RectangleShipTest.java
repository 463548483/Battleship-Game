package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_rectangleship_constructor() {
    BasicShip<Character> s=new RectangleShip<Character>("s",new Coordinate(1,2), 3, 1,new SimpleShipDisplayInfo('s', '*'));
    assertEquals("s", s.getName());
    assertEquals(true,s.myPieces.containsKey(new  Coordinate(1,3)));
    assertEquals(false,s.myPieces.containsKey(new Coordinate(2,2)));
  }


  @Test
  public void test_hit_sunk(){
    BasicShip<Character> s=new RectangleShip<Character>("s",new Coordinate(1,2), 3,1,new SimpleShipDisplayInfo('s', '*'));
    assertEquals(false, s.isSunk());
    assertEquals(false, s.wasHitAt(new Coordinate(1,2)));
    s.recordHitAt(new Coordinate(1,2));
   assertEquals(true, s.wasHitAt(new Coordinate(1,2)));
     s.recordHitAt(new Coordinate(1,3));
    s.recordHitAt(new Coordinate(1,4));
    assertEquals(true, s.isSunk());
    
    assertThrows(IllegalArgumentException.class,()->s.recordHitAt(new Coordinate(2,2)));
   
  }
}
