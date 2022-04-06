package edu.duke.zw255.battleship.shared;

public class Coordinate {
  private final  int row;
  private final  int column;
  public int getRow(){
    return this.row;
  }
  public int getColumn(){
    return this.column;
  }
    
  public Coordinate(int r,int c){
    this.row=r;
    this.column=c;
  }
  public Coordinate(String descr){
    if (descr.length()!=2){
      throw new IllegalArgumentException("Invalid Input, should only contain row and column");
    }
    char rowLetter=descr.toUpperCase().charAt(0);
 char colLetter=descr.charAt(1);

    if (rowLetter<'A'||rowLetter>'Z'||colLetter-'0'<0||colLetter-'0'>9){
      throw new IllegalArgumentException("Invalid Character, expect A0-Z9, but receive"+rowLetter+colLetter);
    }
    row=rowLetter-'A';
    column=colLetter-'0';
  }

  public Coordinate add(int[] toadd){
    return new Coordinate(row+toadd[0], column+toadd[1]);
  }

  public Boolean valid(int w,int h){
    if (row>=0&&row<h&&column>=0&&column<w){
      return true;
    }
    else{
      return false;
    }
  }
  
  @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())){
      Coordinate c=(Coordinate )o;
      return c.row==row&&c.column==column;
    }
    return false;
  }
  @Override
  public String toString(){
    return "("+row+", "+column+")";
  }
  @Override
  public int hashCode(){
    return toString().hashCode();
  }


  public String print(){
    char r=(char)(row+(int)'A');
    String res=r+Integer.toString(column);
    return res;
  }
}
