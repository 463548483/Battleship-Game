package edu.duke.zw255.battleship;
/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the 
 * enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;
  /**
   * Constructs a BoardView, given the board it will display.
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay){
    this.toDisplay=toDisplay;
  }
  public String displayMyOwnBoard(){
    String expected=makeHeader();
    for (int row=0;row<toDisplay.getHeight();row++){
      expected+=Character.toString(row+'A');
      String sep=" ";
      for (int column=0;column<toDisplay.getWidth();column++){
   
        Coordinate pos=new Coordinate(row,column);
        expected+=sep;
        if (toDisplay.whatIsAt(pos)!=null){
          expected+=Character.toString(toDisplay.whatIsAt(pos));
        }
        else{
          expected+=" ";
        }
        sep="|";
        }
      expected+=" ";
      expected+=Character.toString(row+'A');
      expected+="\n";
    }
    expected+=makeHeader();
    return expected;
  }
   /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep=""; //start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
    
      ans.append(i);
       sep = "|";
    }
     ans.append("\n");

    return ans.toString();
  }
}
