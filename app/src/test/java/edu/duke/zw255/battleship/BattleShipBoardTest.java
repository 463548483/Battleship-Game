package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }
  
  @Test
  public void test_tryAddShip(){
    Board<Character> b=new BattleShipBoard<Character>(6, 2); 

    V1ShipFactory f=new V1ShipFactory ();
    Placement p1=new Placement("B2h");
    Placement p2=new Placement("A2v");
    Ship<Character> s1=f.makeBattleship(p1);
    Ship<Character> s2=f.makeSubmarine(p2);

    
    assertEquals(null,b.tryAddShip(s1));
    
    assertEquals("That placement is invalid: the ship overlaps another ship.\n",b.tryAddShip(s2));

  }
}
