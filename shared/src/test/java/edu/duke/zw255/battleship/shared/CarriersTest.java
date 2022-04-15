package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CarriersTest {
  @Test
 public void test_carriers() {
    BasicShip<Character> sd=new Carriers <Character>("b",new Coordinate(0,0),'D',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sd.getName());
    assertEquals(true,sd.myPieces.containsKey(new  Coordinate(3,1)));
    assertEquals(false,sd.myPieces.containsKey(new Coordinate(3,0)));
    BasicShip<Character> sl=new Carriers <Character>("b",new Coordinate(0,0),'L',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sl.getName());
    assertEquals(true,sl.myPieces.containsKey(new  Coordinate(1,3)));
    assertEquals(false,sl.myPieces.containsKey(new Coordinate(0,1)));
    BasicShip<Character> sr=new Carriers <Character>("b",new Coordinate(0,0),'R',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", sr.getName());
    assertEquals(true,sr.myPieces.containsKey(new  Coordinate(1,2)));
    assertEquals(false,sr.myPieces.containsKey(new Coordinate(1,3)));
    BasicShip<Character> su=new Carriers <Character>("b",new Coordinate(0,0),'U',new SimpleShipDisplayInfo('b', '*'),new SimpleShipDisplayInfo(null, 'b'));
    assertEquals("b", su.getName());
    assertEquals(true,su.myPieces.containsKey(new  Coordinate(4,1)));
    assertEquals(false,su.myPieces.containsKey(new Coordinate(0,4)));
    BasicShip<Character> st=new Carriers <Character>(new Coordinate(0,0),'b','*');
    assertEquals("testship", st.getName());
    assertEquals(true,st.myPieces.containsKey(new  Coordinate(3,1)));
    assertEquals(false,st.myPieces.containsKey(new Coordinate(1,1)));

  }

}
