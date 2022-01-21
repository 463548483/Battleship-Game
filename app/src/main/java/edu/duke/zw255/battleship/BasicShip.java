package edu.duke.zw255.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  
  protected HashMap<Coordinate,Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  
  public BasicShip(Iterable<Coordinate> where,ShipDisplayInfo<T> myDisplayInfo){
    myPieces=new HashMap<Coordinate,Boolean>();
    for (Coordinate c:where){
      myPieces.put(c,false);
    }
    this.myDisplayInfo=myDisplayInfo;
  }
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

}
