package edu.duke.zw255.battleship;

public interface AbstractAction<T> {
  public void takeAction(Board<T> theBoard, Coordinate c);

}
