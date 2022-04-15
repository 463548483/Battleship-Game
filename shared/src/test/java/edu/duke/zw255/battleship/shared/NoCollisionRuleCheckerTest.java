package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkmyrule() {
    PlacementRuleChecker<Character> rule=new NoCollisionRuleChecker<Character>(null);
    V1ShipFactory f=new V1ShipFactory ();
    Placement p1=new Placement("B2h");
    Placement p2=new Placement("A2v");
    Ship<Character> s1=f.makeBattleship(p1);
    Ship<Character> s2=f.makeSubmarine(p2);

    Board<Character> b=new BattleShipBoard(6,2,'X');
    assertEquals(null,rule.checkMyRule(s1,b));
    b.tryAddShip(s1);
    assertEquals("That placement is invalid: the ship overlaps another ship.\n",rule.checkMyRule(s2,b));
    

  }

  @Test
  public void test_placementrulechecker(){


    PlacementRuleChecker<Character> colrule=new NoCollisionRuleChecker<Character>(null);
        PlacementRuleChecker<Character> boundrule=new InBoundsRuleChecker<Character>(colrule);

    V1ShipFactory f=new V1ShipFactory ();
    Placement p1=new Placement("B2h");
    Placement p2=new Placement("A2v");
    Ship<Character> s1=f.makeBattleship(p1);
    Ship<Character> s2=f.makeSubmarine(p2);

    Board<Character> b=new BattleShipBoard(6,2,'X');
    assertEquals(null,boundrule.checkPlacement(s1,b));
    b.tryAddShip(s1);
    assertEquals("That placement is invalid: the ship overlaps another ship.\n",boundrule.checkPlacement(s2,b));
  }

  }

