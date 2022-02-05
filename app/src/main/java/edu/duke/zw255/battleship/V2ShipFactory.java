package edu.duke.zw255.battleship;

import org.checkerframework.common.reflection.qual.NewInstance;

public class V2ShipFactory<T> extends V1ShipFactory<T> {

  protected Ship<T> createDirectionShip(Placement where, char letter, String name){
    char dir=where.getOrientation();
    if (dir=='L'||dir=='R'||dir=='U'||dir=='D'){
      if (name=="Battleship"){
        return new Battleships(name, where.getWhere(), dir, letter,'*');
      }
      else if (name=="Carrier"){
        return new Carriers(name, where.getWhere(), dir, letter,'*');
      }
      else{
        throw new IllegalArgumentException("Invalid placement, only BattleShip and Carrie have direction");
        }
    }
    throw new IllegalArgumentException("Invalid Orientation Input, expect 'U/D/L/R' but get"+where.getOrientation());
    
  }
  

  @Override
  public Ship<T> makeBattleship(Placement where) {
    // create ship for direction Battleship 1x4 b
    return createDirectionShip(where,'b',"Battleship");
  }

  @Override
  public Ship<T> makeCarrier(Placement where) {
    // create ship for direction Carries 1x6 c
    return createDirectionShip(where,'c',"Carrier");
  }


}
