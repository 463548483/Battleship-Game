package edu.duke.zw255.battleship.shared;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BasicShip<T> implements Ship<T> {
  
  protected LinkedHashMap<Coordinate,Boolean> myPieces;//true=hit
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  final String name;
  
  public BasicShip(String name,Iterable<Coordinate> where,ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    myPieces=new LinkedHashMap<Coordinate,Boolean>();
    for (Coordinate c:where){
      myPieces.put(c,false);
    }
    this.myDisplayInfo=myDisplayInfo;
    this.enemyDisplayInfo=enemyDisplayInfo;
    this.name=name;
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
  public T getDisplayInfoAt(Coordinate where,boolean myShip) {
    checkCoordinateInThisShip(where);
    //look up the hit status of this coordinate
    if (myShip==true){
    return myDisplayInfo.getInfo(where, myPieces.get(where));

    }
    else{
      return enemyDisplayInfo.getInfo(where, myPieces.get(where));
    }
  }

  @Override
  public void moveShip(Ship<T> olds){
    Iterable<Coordinate> oldcs=olds.getCoordinates();
    Iterator<Coordinate> it=myPieces.keySet().iterator();
    Set<Coordinate> Cods=myPieces.keySet();
    for (Coordinate c:oldcs){
      
      myPieces.put(it.next(),olds.getValue(c));
    }
  }

  @Override
  public Boolean getValue(Coordinate c){
    return myPieces.get(c);
  } 


}
