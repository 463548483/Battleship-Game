package edu.duke.zw255.battleship.shared;

public interface AbstractAction<T> {
  public void takeAction(Board<T> theBoard, Coordinate c);

}
