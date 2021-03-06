package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_createship() {
    Placement p1=new Placement("B2h");
    Placement p2=new Placement("B3v");
    Placement p3=new Placement("B3r");
    V1ShipFactory shipfactory=new V1ShipFactory();
    test_helper(shipfactory.makeSubmarine(p1),"Submarine",'s',new Coordinate(1,2),new Coordinate(1,3));
    test_helper(shipfactory.makeBattleship(p1),"Battleship",'b',new Coordinate(1,2),new Coordinate(1,4));
    test_helper(shipfactory.makeCarrier(p2),"Carrier",'c',new Coordinate(2,3),new Coordinate(5,3));
    test_helper(shipfactory.makeDestroyer(p2),"Destroyer",'d',new Coordinate(1,3),new Coordinate(3,3));
    assertThrows(IllegalArgumentException.class,()->shipfactory.createShip(p3,1,2,'s',"S"));
  }


  
  private void test_helper(Ship<Character> ship, String name, char letter, Coordinate c1,Coordinate c2){
    Coordinate[] pos=new Coordinate[]{c1,c2};
    
    
    assertEquals(ship.getName(),name);
    for (Coordinate c:pos){
      assertEquals(ship.getDisplayInfoAt(c,true),letter);
      assertEquals(true,ship.occupiesCoordinates(c));
    }
  }
}
