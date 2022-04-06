/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.zw255.battleship.server;
import edu.duke.zw255.battleship.shared.*;

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

class ServerTest {
  
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
      Server.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
   String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    
  }

    @Test
  void test_main2() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("inputCH.txt");
    assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("outputCH.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
        try {
      System.setIn(input);
      System.setOut(out);
      Server.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
   String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    
  }

  @Test
  void test_main3() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("inputCC.txt");
    assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("outputCC.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
        try {
      System.setIn(input);
      System.setOut(out);
      Server.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
   String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    String prompt="Welcome to Battleship Game!\n"+
"What would you like for Player A\n"+
"0 for Human\n"+
"1 for Computer\n"+
"What would you like for Player B\n"+
"0 for Human\n"+
"1 for Computer\n";
    assertEquals(prompt+expected,actual);
    
  }

  
  private Server createServer(int w, int h, InputStream inputdata, OutputStream bytes,Boolean A,Boolean B){
 
 BufferedReader input=new BufferedReader(new InputStreamReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b1 = new BattleShipBoard<Character>(w, h,'X');
        Board<Character> b2 = new BattleShipBoard<Character>(w, h,'X');

    V1ShipFactory shipFactory=new V1ShipFactory();
    return new Server(b1,b2,input, ps,A,B);
  }




  @Test
  void test_computer() throws IOException{
     ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        InputStream input = getClass().getClassLoader().getResourceAsStream("inputCC.txt");
    assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("outputCC.txt");
        Server newapp=createServer(10,20,input,bytes,true,true);
    newapp.doPlacementPhase();
    newapp.doAttackingPhase();
     String expected = new String(expectedStream.readAllBytes());
     assertEquals(expected, bytes.toString());
     bytes.reset();
  }

}

