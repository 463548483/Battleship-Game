package edu.duke.zw255.battleship.shared;

import java.util.ArrayList;
import java.util.HashSet;


public class Battleships<T> extends BasicShip<T> {
  
 /**
   *this is a helper function to add coordinate when add the ship with given direction
   *@upperLeft top left of ship
   *@dir direction of the ship
   */
  static ArrayList<Coordinate> makeCoords(Coordinate upperLeft, char dir){
    ArrayList<Coordinate> set=new ArrayList<Coordinate>();
    if (dir=='U'){
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()));
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()+2));
    }
    else if (dir=='R'){
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()));
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()));
      set.add(new Coordinate(upperLeft.getRow()+2,upperLeft.getColumn()));
    
    }
    else if (dir=='L'){
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()));
      set.add(new Coordinate(upperLeft.getRow()+2,upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()+1));
    }
    else { //dir=D
      set.add(new Coordinate(upperLeft.getRow()+1,upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()+2));
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()+1));
      set.add(new Coordinate(upperLeft.getRow(),upperLeft.getColumn()));
     
    }
    return set;
  }
  
  @Override
  public String getName() {
    return name;
  }

  
    public Battleships(String name,Coordinate upperLeft, char dir, ShipDisplayInfo<T> selfinfo,ShipDisplayInfo<T> enemyinfo){
      super(name,makeCoords(upperLeft, dir),selfinfo,enemyinfo);
    
    
  }

  public Battleships(String name,Coordinate upperLeft, char dir, T data, T onHit) {
    this(name,upperLeft, dir, new SimpleShipDisplayInfo<T>(data, onHit),new SimpleShipDisplayInfo<T>(null, data));
  }
  public Battleships(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft, 'U', data, onHit);
  }

}
