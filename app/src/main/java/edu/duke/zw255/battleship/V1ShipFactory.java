package edu.duke.zw255.battleship;

import org.checkerframework.common.reflection.qual.NewInstance;

public class V1ShipFactory<T> implements AbstractShipFactory<T> {

  protected Ship<T> createShip(Placement where, int w, int h, char letter, String name){
    if (where.getOrientation()=='V'){
    }
    else if(where.getOrientation()=='H'){
      int temp=w;
      w=h;
      h=temp;
    }
    else{
      throw new IllegalArgumentException("Invalid Orientation Input, expect 'V' or 'H' but get"+where.getOrientation());
    }
    
    return new RectangleShip(name,where.getWhere(),w,h,letter,'*');
    
  }
  
  @Override
  public Ship<T> makeSubmarine(Placement where) {
    //create ship for Submarine 1x2 s
    return createShip(where,1,2,'s',"Submarine");
  }

  @Override
  public Ship<T> makeBattleship(Placement where) {
    // create ship for Battleship 1x4 b
    return createShip(where,1,4,'b',"Battleship");
  }

  @Override
  public Ship<T> makeCarrier(Placement where) {
    // create ship for Carries 1x6 c
    return createShip(where,1,6,'c',"Carrier");
  }

  @Override
  public Ship<T> makeDestroyer(Placement where) {
    // create ship for Destroyer 1x3 'd'
    return createShip(where, 1,3 ,'d',"Destroyer");
  }

}
