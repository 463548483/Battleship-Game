package edu.duke.zw255.battleship.shared;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> where=theShip.getCoordinates();
    for (Coordinate c:where){
      if (theBoard.whatIsAtForSelf(c)!=null){
        return "That placement is invalid: the ship overlaps another ship.\n";
      }
    }
    return null;
  }

  
}
