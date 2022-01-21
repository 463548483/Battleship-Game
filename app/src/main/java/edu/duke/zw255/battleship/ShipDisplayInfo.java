package edu.duke.zw255.battleship;

public interface ShipDisplayInfo<T> {
  public T getInfo(Coordinate where, Boolean hit);
}
