package edu.duke.zw255.battleship.shared;

public interface ShipDisplayInfo<T> {
  public T getInfo(Coordinate where, Boolean hit);
}
