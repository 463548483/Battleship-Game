package edu.duke.zw255.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  
  protected HashMap<Coordinate,Boolean> myPieces;//true=hit
  protected ShipDisplayInfo<T> myDisplayInfo;
  
  public BasicShip(Iterable<Coordinate> where,ShipDisplayInfo<T> myDisplayInfo){
    myPieces=new HashMap<Coordinate,Boolean>();
    for (Coordinate c:where){
      myPieces.put(c,false);
    }
    this.myDisplayInfo=myDisplayInfo;
  }
  
@Override
public Iterable<Coordinate> getCoordinates(){
  return myPieces.keySet();
}
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    
    return myPieces.containsKey(where);
  }

  protected void checkCoordinateInThisShip(Coordinate C){
    if (!occupiesCoordinates(C)){
      throw new IllegalArgumentException("Not a valid Coordinate");
    }
  }
  
  @Override
  public boolean isSunk() {
    if (myPieces.containsValue(false)==false){
      return true;
    }
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where,true);
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    
    return myPieces.get(where);
  }
  @Override
  public T getDisplayInfoAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, myPieces.get(where));
  }

}
