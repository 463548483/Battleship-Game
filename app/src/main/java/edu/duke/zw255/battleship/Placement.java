package edu.duke.zw255.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;
  public Placement(Coordinate w,char o){
    where=w;
    orientation=Character.toUpperCase(o);
  }
  public Placement(String s){
    where=new Coordinate(s.substring(0,2));
    orientation=Character.toUpperCase(s.charAt(2));
  }
  public Coordinate getWhere(){
    return where;
  }
  public char getOrientation(){
    return orientation;
  }
  @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())){
      Placement p=(Placement)o;
      return p.where.equals(where)&&p.orientation==orientation;
 
    }
    return false;
  }
  @Override
  public String toString(){
    return where.toString()+" "+orientation;
  }
  @Override
  public int hashCode(){
    return toString().hashCode();
  }
}
