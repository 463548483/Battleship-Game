package edu.duke.zw255.battleship.shared;

import java.util.HashMap;

public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public void removeShip(Ship<T> toRemove);
  public T whatIsAtForSelf(Coordinate where);
  public T whatIsAtForEnemy(Coordinate where);  
  public Ship<T> fireAt(Coordinate c);
  public boolean isLose();
  public Ship<T> shipAt(Coordinate c);
  public HashMap<String, Integer> sonarScan(Coordinate c);
}
