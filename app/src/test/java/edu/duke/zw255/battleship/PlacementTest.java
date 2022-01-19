package edu.duke.zw255.battleship;

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
  }

}
