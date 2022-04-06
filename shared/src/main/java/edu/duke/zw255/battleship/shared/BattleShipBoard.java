package edu.duke.zw255.battleship.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T> > myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  private final HashMap<Coordinate,T> enemyHits;

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
    enemyHits=new HashMap<Coordinate,T>();
    this.missInfo=missInfo;
  }


  @Override
  public Ship<T> fireAt(Coordinate c){
    for (Ship<T> ship:myShips){
      if (ship.occupiesCoordinates(c)==true){
        ship.recordHitAt(c);
        enemyHits.put(c,ship.getDisplayInfoAt(c, false));
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
     if (!isSelf){
       if(enemyMisses.contains(where)){
         return missInfo;
       }
       if(enemyHits.containsKey(where)){
         return enemyHits.get(where);
       }
     }
     else{
      for (Ship<T> s:myShips){
        if(s.occupiesCoordinates(where)){
          return s.getDisplayInfoAt(where,isSelf);
        }
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
  public void removeShip(Ship<T> toRemove){
    myShips.remove(toRemove);
  }
  
  @Override
  public Ship<T>  shipAt(Coordinate where){
     for (Ship<T> s:myShips){
      if(s.occupiesCoordinates(where)){
        return s;
      }
     }
    return null;
  }


  
  @Override
  public boolean isLose(){
    for (Ship<T> ship:myShips){
      if(ship.isSunk()==false){
        return false;
      }
    }
    return true;
  }

  @Override
  public HashMap<String, Integer> sonarScan(Coordinate c){
    ArrayList<int []> scan=scanSet();
    HashMap<String, Integer> report=new HashMap<String, Integer>();
    
    for (int[] xy:scan){
      if(c.add(xy).valid(width, height)){
        Coordinate newc=c.add(xy);
        if (shipAt(newc)!=null){
          String scanship=shipAt(newc).getName();
          if (report.containsKey(scanship)){
            report.put(scanship,report.get(scanship)+1);
          }
          else{
            report.put(scanship,1);
          }
        }
        
      }
    }
    return report;
  }

  private ArrayList<int[]> scanSet(){
    ArrayList<int[]> scan=new ArrayList<int[]>();
    
    scan.add(new int[]{-3,0});
    scan.add(new int[]{3,0});
    for (int i=0;i<3;i++){
      scan.add(new int[]{-2,i-1});
      scan.add(new int[]{2,i-1});
    }
    for (int i=0;i<5;i++){
      scan.add(new int[]{-1,i-2});
      scan.add(new int[]{1,i-2});
    }
    for (int i=0;i<7;i++){
      scan.add(new int[]{0,i-3});
    }
    return scan;
  }
  
}
