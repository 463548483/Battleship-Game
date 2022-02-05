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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
private TextPlayer createTextPlayer(int w, int h, String inputdata, OutputStream bytes){
 StringReader sr=new StringReader(inputdata);
 BufferedReader input=new BufferedReader(new StringReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(w, h,'X');
    V2ShipFactory shipFactory=new V2ShipFactory();
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

  @Disabled
  @Test
  void test_attacking() throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(3,3,"a0h\nBB\na2\na1\n",bytes);
      TextPlayer play2=createTextPlayer(3,3,"a1h\na1\n",bytes);
      play1.doOnePlacement("Submarines", (p)->play1.shipFactory.makeSubmarine(p));
       play2.doOnePlacement("Submarines", (p)->play2.shipFactory.makeSubmarine(p));
       String expected1 ="\n"+
          "     "+"Your ocean"+"             "+"Player A's ocean\n"+
          "  0|1|2                    0|1|2\n" +
          "A s|s|  A                A  | |  A\n"+
          "B  | |  B                B  | |  B\n"+
          "C  | |  C                C  | |  C\n"+
          "  0|1|2                    0|1|2\n\n"+
         
          "Player A, where do you want fire at?\n"+
          "Please enter valid choice\n"+
          "Player A, where do you want fire at?\n"+
         "You hit a Submarine!"+"\n";
    String expected2 =
      "Player A, where would you like to fire at?\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Player A's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  |s|* A                          A  | |  A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C  | |  C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
        "Player A, where do you want fire at?\n"+
          "You hit a Submarine!\n";
      String expected3 =
          
          "    "+"Your Ocean"+"                       "+"Player A's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A s|*|  A                          A  |s|s A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C  | |  C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "Player A, where do you want fire at?\n"+
         "You hit a Submarine\n"+
        "Player A have won!\n";
      bytes.reset();
      play1.playOneTurn(play2.theBoard, play2.view);
      //assertEquals(bytes, expected1);
      bytes.reset();
      play2.playOneTurn(play1.theBoard, play2.view);
      //assertEquals(bytes, expected2);
      bytes.reset();
      play1.playOneTurn(play2.theBoard, play2.view);
      //assertEquals(bytes, expected3);
    
  }
  
  @Test
  public void test_readcoo()throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(10,20,"",bytes);
      assertThrows(EOFException.class, ()->play1.readCoordinate("?"));
    
}

  @Test
  void test_moveaction() throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(3,3,"a0u\nb2\na2\na1\na2r\na1r\n",bytes);
      play1.doOnePlacement("Battleship",(p)->play1.shipFactory.makeBattleship(p));
      play1.fireAction(play1.theBoard);
      bytes.reset();
      String expected = "  0|1|2\n" + "A  |b|  A\n" + "B b|*|b B\n" + "C  | |  C\n" + "  0|1|2\n";
      //assertEquals(expected, bytes);
      bytes.reset();
      //assertEquals(Battleships.class, play1.theBoard.shipAt(new Coordinate("a1")));
      play1.moveAction();
      play1.out.print(play1.view.displayMyOwnBoard());
      play1.out.print(play1.view.displayEnemyBoard());
      
      //assertThrows(IllegalArgumentException.class,()-> play1.moveAction());
      expected = "  0|1|2\n" + "A  |b|  A\n" + "B  |b|b B\n" + "C  |*|  C\n" + "  0|1|2\n";
      String expected2 = "  0|1|2\n" + "A  | |  A\n" + "B  | |b B\n" + "C  | |  C\n" + "  0|1|2\n";
    String prompt="which ship do you want to move, please enter Coordinate?\n"
      +"There is no ship, please re-enter Coordinate\n"
      +"\n"
      +"which ship do you want to move, please enter Coordinate?\n"
      +"Where do you want to move Battleship\n" 
      +"That placement is invalid: the ship goes off the right of the board.\n"
      +"\n"
      + "Where do you want to move Battleship\n";
   
    assertEquals(prompt+expected+expected2,bytes.toString());
        bytes.reset();
      
        //assertEquals(null, null);
      
  }

  @Test
  void test_sonaraction() throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(5,5,"a0u\na3v\nbb\na4\na1\na2r\na1r\n",bytes);
      play1.doOnePlacement("Battleship",(p)->play1.shipFactory.makeBattleship(p));
      play1.doOnePlacement("Submarines", (p)->play1.shipFactory.makeSubmarine(p));      //play1.fireAction(play1.theBoard);
      bytes.reset();
      
      play1.sonarAction(play1.theBoard);
      String expected = "  0|1|2\n" + "A  |b|  A\n" + "B  |b|b B\n" + "C  |*|  C\n" + "  0|1|2\n";
      String expected2 = "Submarines occupy 2 square\n"
        +"Destroyers occupy 0 square\n"
        +"Battleships occupy 2 square\n"
        +"Carriers occupy 0 square\n";
    String prompt="which coordinate do you want to scan, please enter Coordinate?\n"
      +"Invalid Character, expect A0-Z9, but receiveBb\n"
      +"which coordinate do you want to scan, please enter Coordinate?\n";
   
    assertEquals(prompt+expected2+"\n",bytes.toString());
        bytes.reset();
      
      // TextPlayer play2=createTextPlayer(5,5,"",bytes);
      // assertThrows(IOException.class,()->play2.sonarAction());
      
  }

  @Test
  void test_playoneturn() throws IOException{
      ByteArrayOutputStream bytes=new ByteArrayOutputStream();
      TextPlayer play1=createTextPlayer(5,5,"a0u\na3v\nM\na4\na1\na2r\na1r\n",bytes);
      TextPlayer play2=createTextPlayer(5,5,"a0u\na3v\nS\na4\na1\na2r\na1r\n",bytes);
      play1.doOnePlacement("Battleship",(p)->play1.shipFactory.makeBattleship(p));
      play1.doOnePlacement("Submarines", (p)->play1.shipFactory.makeSubmarine(p));    
      bytes.reset();
      
      play1.playOneTurn(play2.theBoard,play2.view);
      String expected="";
      //assertEquals(bytes, "");
  }

  
}


