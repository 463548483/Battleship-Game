package edu.duke.zw255.battleship;

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

    Board<Character> b=new BattleShipBoard(6,2);
    assertEquals(true,rule.checkMyRule(s1,b));
    b.tryAddShip(s1);
    assertEquals(false,rule.checkMyRule(s1,b));
    

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

    Board<Character> b=new BattleShipBoard(6,2);
    assertEquals(true,boundrule.checkPlacement(s1,b));
    b.tryAddShip(s1);
    assertEquals(false,boundrule.checkPlacement(s1,b));
  }

  }

