package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
private TextPlayer createTextPlayer(int w, int h, String inputdata, OutputStream bytes){
 StringReader sr=new StringReader(inputdata);
 BufferedReader input=new BufferedReader(new StringReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(w, h,'X');
    V1ShipFactory shipFactory=new V1ShipFactory();
    return new TextPlayer("A",b,input, ps,shipFactory);
  }


  @Test
  void test_Read_placement() throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(10,20,"B2V\nC8H\na4v\n",bytes);
        String prompt = "Please enter a location for a ship:";
      Placement[] expected = new Placement[3];
      expected[0] = new Placement(new Coordinate(1, 2), 'V');
      expected[1] = new Placement(new Coordinate(2, 8), 'H');
      expected[2] = new Placement(new Coordinate(0, 4), 'V');
      for (int i = 0; i < expected.length; i++) {
        Placement p = play1.readPlacement(prompt);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
        //play1.doOnePlacement();
      }
      
         TextPlayer play2=createTextPlayer(10,20,"",bytes);
         assertThrows(EOFException.class, ()->play2.readPlacement(prompt));
  }

  @Test
  void test_dooneplacement() throws IOException{
ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(3,3,"C0H\nb0h\n",bytes);
      play1.doOnePlacement("Destroyer",(p)->play1.shipFactory.makeDestroyer(p));
        BoardTextView btv=new BoardTextView(new BattleShipBoard<Character>(10,20,'X'));
        String[] expected = new String[2];
    expected[0] = "  0|1|2\n" + "A  | |  A\n" + "B  | |  B\n" + "C d|d|d C\n" + "  0|1|2\n";
    expected[1] = "  0|1|2\n" + "A  | |  A\n" + "B d|d|d B\n" + "C d|d|d C\n" + "  0|1|2\n";
                      play1.doOnePlacement("Destroyer",(p)->play1.shipFactory.makeDestroyer(p));
    String prompt="Player A where would you like to put your Destroyer ?\n";
         assertEquals(prompt+expected[0]+prompt+expected[1],bytes.toString());
        bytes.reset();
  }

  @Test
  void test_parse() throws IOException{
    
  } 


}


