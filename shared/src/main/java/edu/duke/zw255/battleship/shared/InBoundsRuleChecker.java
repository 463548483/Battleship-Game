package edu.duke.zw255.battleship.shared;


public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }
  
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard){
    Iterable<Coordinate> where=theShip.getCoordinates();
    for (Coordinate c:where){
      if (c.getRow()>=theBoard.getHeight()){
        return "That placement is invalid: the ship goes off the bottom of the board.\n";
      }
      if(c.getRow()<0){
        return   "That placement is invalid: the ship goes off the top of the board.\n";
        
      }
      if(c.getColumn()>=theBoard.getWidth()){
        return "That placement is invalid: the ship goes off the right of the board.\n";
      }
      if (c.getColumn()<0){
        return "That placement is invalid: the ship goes off the left of the board.\n";
      }
    }
    return null;
  }
}


