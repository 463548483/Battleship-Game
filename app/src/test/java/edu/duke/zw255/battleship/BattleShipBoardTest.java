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
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected){
  assertEquals(b.whatIsAt(null), null);
  Coordinate pos=new Coordinate(2,3);
  Ship ship1=new BasicShip(pos);
  assertEquals(b.tryAddShip(ship1),true);
  assertEquals(b.whatIsAt(pos), 's');
  }
}
