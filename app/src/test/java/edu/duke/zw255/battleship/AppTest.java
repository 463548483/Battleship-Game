/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.zw255.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AppTest {
  
  @Test
  void test_main() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
        try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
   String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    
  }

private App createApp(int w, int h, InputStream inputdata, OutputStream bytes){
 
 BufferedReader input=new BufferedReader(new InputStreamReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b1 = new BattleShipBoard<Character>(w, h,'X');
        Board<Character> b2 = new BattleShipBoard<Character>(w, h,'X');

    V1ShipFactory shipFactory=new V1ShipFactory();
    return new App(b1,b2,input, ps);
  }


  
  @Test
  void test_parse() throws IOException{
          ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        InputStream input = getClass().getClassLoader().getResourceAsStream("input1.txt");
    assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output1.txt");
    App newapp=createApp(10,20,input,bytes);
    newapp.doPlacementPhase();
     String expected = new String(expectedStream.readAllBytes());
     assertEquals(expected, bytes.toString());
     bytes.reset();
  }

  
}


