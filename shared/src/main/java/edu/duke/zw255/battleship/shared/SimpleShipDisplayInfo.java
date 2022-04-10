package edu.duke.zw255.battleship.shared;

import java.io.Serializable;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T>, Serializable {
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
