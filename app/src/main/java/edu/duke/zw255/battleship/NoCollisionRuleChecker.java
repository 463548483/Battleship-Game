package edu.duke.zw255.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> where=theShip.getCoordinates();
    for (Coordinate c:where){
      if (theBoard.whatIsAt(c)!=null){
        return false;
      }
    }
    return true;
  }

  
}
