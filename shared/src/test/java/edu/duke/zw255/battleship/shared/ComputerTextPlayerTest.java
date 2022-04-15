package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;

public class ComputerTextPlayerTest {
  @Test
  public void test_randomPlacement() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer play1 = createTextPlayer(10, 20, "B2V\nC8U\na4v\n", bytes);
    ComputerTextPlayer spyPlayer = spy(play1);
    when(spyPlayer.randomCoordinate()).thenReturn(new Coordinate("A0"));
    Placement p = spyPlayer.randomPlacement("Submarine");
    Placement p2 = spyPlayer.randomPlacement("Carrier");
    assertEquals(p.getWhere().equals(new Coordinate("A0")), true);
    assertEquals(p2.getWhere().equals(new Coordinate("A0")), true);
  }

  @Test
  public void test_doOnePlacement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer play1 = createTextPlayer(10, 20, "B2V\nC8U\na4v\n", bytes);
    Board<Character> mockBoard=mock(BattleShipBoard.class);
    play1.theBoard=mockBoard;
    ComputerTextPlayer spyPlayer = spy(play1);
    doReturn(new Placement("A0H")).when(spyPlayer).randomPlacement(any());
    doReturn("error", null).when(spyPlayer.theBoard).tryAddShip(any());
    spyPlayer.doOnePlacement("Destroyer", (p) -> play1.shipFactory.makeDestroyer(p));
  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer play1 = createTextPlayer(10, 20, "B2V\nC8U\na4v\n", bytes);
    ComputerTextPlayer spyPlayer = spy(play1);
    doNothing().when(spyPlayer).doOnePlacement(any(), any());
    spyPlayer.doPlacementPhase();
  }

  @Test
  public void test_fireAction() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer play1 = createTextPlayer(10, 20, "B2V\nC8U\na4v\n", bytes);
    ComputerTextPlayer spyPlayer = spy(play1);
    Ship<Character> mockShip = mock(Ship.class);
    Board<Character> mockBoard = mock(BattleShipBoard.class);
    doReturn(mockShip, null).when(mockBoard).fireAt(any());
    bytes.reset();
    spyPlayer.fireAction(mockBoard);
    assertEquals("Player A hit enemy null at A0!\n", bytes.toString());
    bytes.reset();
    spyPlayer.fireAction(mockBoard);
    assertEquals("Player A missed!\n", bytes.toString());
  }

  @Test
  public void test_playOneTurn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer play1 = createTextPlayer(10, 20, "B2V\nC8U\na4v\n", bytes);
    ComputerTextPlayer spyPlayer = spy(play1);
    doNothing().when(spyPlayer).fireAction(any());
    spyPlayer.playOneTurn(play1.theBoard, play1.view);
  }

  private ComputerTextPlayer createTextPlayer(int w, int h, String inputdata, OutputStream bytes) {
    StringReader sr = new StringReader(inputdata);
    BufferedReader input = new BufferedReader(new StringReader(inputdata));
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new ComputerTextPlayer("A", b, input, ps, shipFactory);
  }

}
