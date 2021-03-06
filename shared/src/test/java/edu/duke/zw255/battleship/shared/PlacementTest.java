package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_placement() {
    Coordinate c1=new Coordinate("C8");
    Coordinate c2=new Coordinate("c8");
 Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    assertEquals(p1, p2);
    Placement p3 = new Placement("A9v");
    Placement p4 = new Placement("A9V");
    assertEquals(p3, p4);
    assertEquals(true, p3.equals(p4));
    assertEquals(false,p3.equals(""));
    assertNotEquals(p1, p3);
    assertEquals(p3.hashCode(), p4.hashCode());
    assertEquals("(0, 9) V", p3.toString());
    
    assertThrows(IllegalArgumentException.class, ()->new Placement("saer"));
  }
  

}
