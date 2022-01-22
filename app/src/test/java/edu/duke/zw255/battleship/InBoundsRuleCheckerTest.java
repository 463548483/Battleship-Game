package edu.duke.zw255.battleship;

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

    Board<Character> b=new BattleShipBoard(6,2);
    assertEquals(true,rule.checkMyRule(s1,b));
    assertEquals(false,rule.checkMyRule(s2,b));
    assertEquals(false,rule.checkMyRule(s3,b));
                 }

}
