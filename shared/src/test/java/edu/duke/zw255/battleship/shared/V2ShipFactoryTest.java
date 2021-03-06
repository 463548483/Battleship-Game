package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  @Test
  public void test_createship() {
    Placement p1=new Placement("B2h");
    Placement p2=new Placement("B3v");
    Placement p4=new Placement("A0u");
    Placement p3=new Placement("B3o");
    V2ShipFactory<Character> shipfactory=new V2ShipFactory<Character>();
    test_helper(shipfactory.makeSubmarine(p1),"Submarine",'s',new Coordinate(1,2),new Coordinate(1,3));
    test_helper(shipfactory.makeBattleship(p4),"Battleship",'b',new Coordinate(0,1),new Coordinate(1,2));
    test_helper(shipfactory.makeCarrier(p4),"Carrier",'c',new Coordinate(0,0),new Coordinate(4,1));
    test_helper(shipfactory.makeDestroyer(p2),"Destroyer",'d',new Coordinate(1,3),new Coordinate(3,3));
    assertThrows(IllegalArgumentException.class,()->shipfactory.createDirectionShip(p3,'s',"S"));
    assertThrows(IllegalArgumentException.class,()->shipfactory.createDirectionShip(p4,'s',"S"));
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
