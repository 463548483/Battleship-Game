package edu.duke.zw255.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T> > myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final T missInfo;
  
  public int getWidth(){
    return this.width;
  }
  public int getHeight(){
    return this.height;
  }

  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)),missInfo);
  }
   /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  public BattleShipBoard(int w,int h, PlacementRuleChecker<T> rule,T missInfo){
    if (w<=0){
      throw new IllegalArgumentException("width should be positive but is"+w);
    }
    if (h<=0){
      throw new IllegalArgumentException("height should be positive but is"+h);
    }
    width=w;
    height=h;
    myShips=new ArrayList<>();
    placementChecker=rule;
    enemyMisses=new HashSet<Coordinate>();
    this.missInfo=missInfo;
  }


  @Override
  public Ship<T> fireAt(Coordinate c){
    for (Ship<T> ship:myShips){
      if (ship.occupiesCoordinates(c)==true){
        ship.recordHitAt(c);
        return ship;
      }
    }
    enemyMisses.add(c);
    return null;
  }

  
  //provide ship status in where if there is a ship
  @Override
  public T whatIsAtForSelf(Coordinate where){
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf){
     for (Ship<T> s:myShips){
      if(s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where,isSelf);
      }
    }
     if (!isSelf){
       if(enemyMisses.contains(where)){
         return missInfo;
       }
     }
    return null;
  }

  @Override
  public T whatIsAtForEnemy(Coordinate where){
    return whatIsAt(where, false);
  }
  
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String res=placementChecker.checkPlacement(toAdd, this);
    if (res==null){
      myShips.add(toAdd);
        return res;
    }
    else{
      return res;
    }
  }

  @Override
  public boolean winlose(){
    for (Ship<T> ship:myShips){
      if(ship.isSunk()==false){
        return false;
      }
    }
    return true;
  }

  
}
