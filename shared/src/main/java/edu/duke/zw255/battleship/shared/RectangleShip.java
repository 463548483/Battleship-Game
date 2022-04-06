package edu.duke.zw255.battleship.shared;

import java.util.ArrayList;
import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  static ArrayList<Coordinate> makeCoords(Coordinate upperLeft, int width, int height){
    ArrayList<Coordinate> set=new ArrayList<Coordinate>();
    for (int i=0;i<height;i++){
      for (int j=0;j<width;j++){
        Coordinate c=new Coordinate(upperLeft.getRow()+i,upperLeft.getColumn()+j);
        set.add(c);
      }
    }
    return set;
  }
  @Override
  public String getName(){
    return name;
  }

  
  
  public RectangleShip(String name,Coordinate upperLeft, int w, int h, ShipDisplayInfo<T> selfinfo,ShipDisplayInfo<T> enemyinfo){
    super(name,makeCoords(upperLeft, w,h),selfinfo,enemyinfo);
    
  }

  public RectangleShip(String name,Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name,upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),new SimpleShipDisplayInfo<T>(null, data));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft, 1, 1, data, onHit);
  }
  
}
