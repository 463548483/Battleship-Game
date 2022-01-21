package edu.duke.zw255.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T myData;
  private T onHit;

  public SimpleShipDisplayInfo(T mydata, T onhit){
    myData=mydata;
    onHit=onhit;
  }
  
  @Override
  public T getInfo(Coordinate where, Boolean hit) {
    if (hit){
      return onHit;
    }
    else{
      return myData;
    }
  }

}
