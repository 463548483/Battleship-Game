package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleshipsTest {
  @Test
  public void test_battleships() {
    BasicShip<Character> sd=new Battleships<Character>("b",new Coordinate(0,0),'D',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sd.getName());
    assertEquals(true,sd.myPieces.containsKey(new  Coordinate(1,1)));
    assertEquals(false,sd.myPieces.containsKey(new Coordinate(1,2)));
    BasicShip<Character> sl=new Battleships<Character>("b",new Coordinate(0,0),'L',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sl.getName());
    assertEquals(true,sl.myPieces.containsKey(new  Coordinate(2,1)));
    assertEquals(false,sl.myPieces.containsKey(new Coordinate(2,0)));
    BasicShip<Character> sr=new Battleships<Character>("b",new Coordinate(0,0),'R',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sr.getName());
    assertEquals(true,sr.myPieces.containsKey(new  Coordinate(2,0)));
    assertEquals(false,sr.myPieces.containsKey(new Coordinate(2,1)));
    BasicShip<Character> su=new Battleships<Character>("b",new Coordinate(0,0),'U',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", su.getName());
    assertEquals(true,su.myPieces.containsKey(new  Coordinate(1,2)));
    assertEquals(false,su.myPieces.containsKey(new Coordinate(0,2)));
    BasicShip<Character> st=new Battleships<Character>(new Coordinate(0,0),'b','*');
    assertEquals("testship", st.getName());
    assertEquals(true,st.myPieces.containsKey(new  Coordinate(1,2)));
    assertEquals(false,st.myPieces.containsKey(new Coordinate(0,2)));

  }

}
