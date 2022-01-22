package edu.duke.zw255.battleship;


public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }
  
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard){
    Iterable<Coordinate> where=theShip.getCoordinates();
    for (Coordinate c:where){
      if (c.getRow()>=theBoard.getHeight()||c.getRow()<0||c.getColumn()>=theBoard.getWidth()||c.getRow()<0){
        return false;
      }
    }
    return true;
  }
}
