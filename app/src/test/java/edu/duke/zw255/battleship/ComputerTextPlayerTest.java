package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerTextPlayerTest {
  @Test
  public void test_randomPlacement()  {
  ByteArrayOutputStream bytes=new ByteArrayOutputStream();
  ComputerTextPlayer play1=createTextPlayer(10,20,"B2V\nC8H\na4v\n",bytes);
  Placement p=play1.randomPlacement("Submarine");
  //assertEquals(p, new Placement("A0h"));
    
  }
  private ComputerTextPlayer createTextPlayer(int w, int h, String inputdata, OutputStream bytes){
    StringReader sr=new StringReader(inputdata);
    BufferedReader input=new BufferedReader(new StringReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(w, h,'X');
    V2ShipFactory shipFactory=new V2ShipFactory();
    return new ComputerTextPlayer("A",b,input, ps,shipFactory);
  }

}
