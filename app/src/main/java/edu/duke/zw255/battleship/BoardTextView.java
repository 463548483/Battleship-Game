package edu.duke.zw255.battleship;

public class BoardTextView {
  private final Board toDisplay;
  public BoardTextView(Board toDisplay){
    this.toDisplay=toDisplay;
  }
  public String displayMyOwnBoard(){
    return "";
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
