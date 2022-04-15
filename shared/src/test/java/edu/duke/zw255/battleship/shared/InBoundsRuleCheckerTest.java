package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkmyrule() {
    PlacementRuleChecker<Character> rule=new InBoundsRuleChecker<Character>(null);
    V1ShipFactory f=new V1ShipFactory ();
    Placement p1=new Placement("B2h");
  Placement p2=new Placement("C2v");
    Placement p3=new Placement("B2v");
  
    Ship<Character> s1=f.makeBattleship(p1);

    Ship<Character> s2=f.makeBattleship(p2);

    Ship<Character> s3=f.makeBattleship(p3);
  
    Ship<Character> s4=f.makeBattleship(new Placement(new Coordinate(-1,1),'h'));
    Ship<Character> s5=f.makeBattleship(new Placement(new Coordinate(1,-1),'h'));
    Ship<Character> s6=f.makeBattleship(new Placement(new Coordinate(1,8),'h'));


    Board<Character> b=new BattleShipBoard(6,2,'X');
    assertEquals(null,rule.checkMyRule(s1,b));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n",rule.checkMyRule(s2,b));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n",rule.checkMyRule(s3,b));
    
    assertEquals("That placement is invalid: the ship goes off the top of the board.\n",rule.checkMyRule(s4,b));
    assertEquals("That placement is invalid: the ship goes off the left of the board.\n",rule.checkMyRule(s5,b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.\n",rule.checkMyRule(s6,b));
                 }

}
