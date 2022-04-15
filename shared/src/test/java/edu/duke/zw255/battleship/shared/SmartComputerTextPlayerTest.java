package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SmartComputerTextPlayerTest {
  @Test
  public void test_fireAction() {
    ByteArrayOutputStream bytes=new ByteArrayOutputStream();
    SmartComputerTextPlayer play1=createTextPlayer(10,20,"B2V\nC8H\na4v\n",bytes);
    BattleShipBoard enemyBoard=new BattleShipBoard<Character>(10, 20,'X');//mock(BattleShipBoard.class);
    bytes.reset();
    play1.fireAction(enemyBoard);
    assertEquals(enemyBoard.enemyMisses.size(),1);
    //assertEquals("Player A missed!", bytes.toString());
    bytes.reset();
    BattleShipBoard enemyBoard2=mock(BattleShipBoard.class);
    Ship ship=new Carriers<Character>(new Coordinate("A0"), 'c', 'X');
    doReturn(ship).when(enemyBoard2).fireAt(any());
    play1.fireAction(enemyBoard2);
    assertEquals(play1.priorityHitSet.size(), 2);
    play1.fireAction(enemyBoard);
    assertEquals(play1.priorityHitSet.size(), 1);
    
  }

  @Test
  public void test_moveAction() throws IOException {
    ByteArrayOutputStream bytes=new ByteArrayOutputStream();
    SmartComputerTextPlayer play1=createTextPlayer(10,20,"A0H\nC8H\na4v\n",bytes);
    BattleShipBoard<Character> mockBoard=mock(BattleShipBoard.class);
    play1.theBoard=mockBoard;
    Carriers<Character> mockShip=mock(Carriers.class);
    doReturn("Destroyer").when(mockShip).getName();
    doNothing().when(mockShip).moveShip(any());
    doReturn(mockShip).when(mockBoard).shipAt(any());
    doNothing().when(mockBoard).removeShip(any());
    doReturn("error",null).when(mockBoard).tryAddShip(any());
    SmartComputerTextPlayer spyPlayer=spy(play1);
    doReturn(new Placement(new Coordinate("A0"), 'H')).when(spyPlayer).randomPlacement(any());
    // Ship<Character> ship=new Carriers<Character>(new Coordinate("A0"), 'c', 'X');
    // play1.toMove=new Coordinate("A0");
    // play1.doOnePlacement("Destroyer",(p)->play1.shipFactory.makeDestroyer(p));
    spyPlayer.moveAction();
    
  }

  @Test
  public void test_playOneTurn()  {
    ByteArrayOutputStream bytes=new ByteArrayOutputStream();
    SmartComputerTextPlayer play1=createTextPlayer(10,20,"A0H\nC8H\na4v\n",bytes);
    BattleShipBoard<Character> mockBoard=new BattleShipBoard<Character>(10, 20,'X');
    play1.theBoard=mockBoard;
    mockBoard.enemyHits.put(new Coordinate("A0"),'x');
    SmartComputerTextPlayer spyPlayer=spy(play1);
    doNothing().when(spyPlayer).moveAction();
    BoardTextView mockView=mock(BoardTextView.class);
    spyPlayer.playOneTurn(mockBoard,mockView);
    
  }

  @Test
  public void test_playOneTurn2()  {
    ByteArrayOutputStream bytes=new ByteArrayOutputStream();
    SmartComputerTextPlayer play1=createTextPlayer(10,20,"A0H\nC8H\na4v\n",bytes);
    BattleShipBoard<Character> mockBoard=new BattleShipBoard<Character>(10, 20,'X');
    play1.theBoard=mockBoard;
    mockBoard.enemyHits.put(new Coordinate("A0"),'x');
    play1.hitCoordinateSet.add(new Coordinate("A0"));
    SmartComputerTextPlayer spyPlayer=spy(play1);
    doNothing().when(spyPlayer).moveAction();
    BoardTextView mockView=mock(BoardTextView.class);
    spyPlayer.playOneTurn(mockBoard,mockView);
    
  }

  private SmartComputerTextPlayer createTextPlayer(int w, int h, String inputdata, OutputStream bytes){
    StringReader sr=new StringReader(inputdata);
    BufferedReader input=new BufferedReader(new StringReader(inputdata));
    PrintStream ps=new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(w, h,'X');
    V2ShipFactory shipFactory=new V2ShipFactory();
    return new SmartComputerTextPlayer("A",b,input, System.out,shipFactory);
  }

}
