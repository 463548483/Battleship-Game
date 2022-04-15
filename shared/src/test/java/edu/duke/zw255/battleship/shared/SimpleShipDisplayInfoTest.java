package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> s=new SimpleShipDisplayInfo<Character>('*','s');
    Coordinate c=new Coordinate("B2");
    assertEquals('s',s.getInfo(c,true));
    assertEquals('*',s.getInfo(c,false));
  }

}
