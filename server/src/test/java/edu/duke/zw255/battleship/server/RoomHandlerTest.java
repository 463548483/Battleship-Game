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
import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

import org.checkerframework.checker.units.qual.m;
import org.checkerframework.checker.units.qual.s;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RoomHandlerTest {
  @Test
  public void test_GameContinue() throws InterruptedException, IOException, ClassNotFoundException {

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket serverSocket = new ServerSocket(12292);
          // Messenger messenger = new Messenger(serverSocket);

          // RoomHandler roomHandler = mock(RoomHandler.class);

          RoomHandler roomHandler = new RoomHandler("a", serverSocket);
          roomHandler.roomContainer = 1;
          // roomHandler.boards[0]=mock(BattleShipBoard.class);
          // doReturn(false).when(roomHandler.boards[0]).isLose();
          Messenger messenger1 = new Messenger(serverSocket);

          // String g1=(String) messenger1.recv();
          // System.out.println(g1);
          // Messenger messenger2 = mock(Messenger.class);//new Messenger(serverSocket);

          // String g2=(String) messenger2.recv();
          roomHandler.acceptOnePlayer(messenger1);
          // roomHandler.acceptOnePlayer(messenger2);
          RoomHandler spyHandler = spy(roomHandler);
          doReturn(false,true,true).when(spyHandler).endGameDetection(any());
          spyHandler.run();
          serverSocket.close();

        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(10); // a bit of hack
    Messenger client1 = new Messenger("127.0.0.1", 12292);
    // client1.send("hello");
    // Messenger client2 = new Messenger("127.0.0.1", 12289);
    // client2.send("hello");
    BattleShipBoard mockBoard = mock(BattleShipBoard.class);

    BoardTextView mockView = mock(BoardTextView.class);
    // // recv start flag
    client1.recv();
    // client2.recv();

    // // send my board and view
    client1.send(mockBoard);
    client1.send(mockView);
    // client2.send(mockBoard);
    // client2.send(mockView);

    // // recv enemy board and view
    client1.recv();
    client1.recv();
    // client2.recv();
    // client2.recv();

    // // attack
    doReturn(true).when(mockBoard).isLose();
    client1.send(mockBoard);
    client1.send(mockView);
    // client2.send(mockBoard);
    // client2.send(mockView);

    // // recv flag, board, view to start another round
    // assertEquals((Flag)client1.recv(),Flag.correctFlag);
    client1.recv();
    client1.recv();
    client1.recv();

    // client2.recv();
    // client2.recv();
    // client2.recv();

    // // sencond round
    when(mockBoard.isLose()).thenReturn(false);
    client1.send(mockBoard);
    client1.send(mockView);
    int flag = (int) client1.recv();
    assertEquals(Flag.endFlag, flag);
    assertEquals("Game end as equal", (String) client1.recv());
    // client2.send(mockBoard);
    // client2.send(mockView);
    // // recv endflag
    client1.closeMessenger();
    // client2.closeMessenger();
  }



  @Test
  public void test_OnePlayerLose() throws InterruptedException, IOException, ClassNotFoundException {

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket serverSocket = new ServerSocket(12299);
          RoomHandler roomHandler = new RoomHandler("a", serverSocket);
          roomHandler.roomContainer = 1;

          Messenger messenger1 = new Messenger(serverSocket);
          roomHandler.acceptOnePlayer(messenger1);
          // roomHandler.acceptOnePlayer(messenger2);
          RoomHandler spyHandler = spy(roomHandler);
          doReturn(true, false).when(spyHandler).endGameDetection(any());
          spyHandler.run();
          serverSocket.close();

        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(10); // a bit of hack
    Messenger client1 = new Messenger("127.0.0.1", 12299);

    BattleShipBoard mockBoard = mock(BattleShipBoard.class);

    BoardTextView mockView = mock(BoardTextView.class);
    // // recv start flag
    client1.recv();


    // // send my board and view
    client1.send(mockBoard);
    client1.send(mockView);


    // // recv enemy board and view
    client1.recv();
    client1.recv();

    // // attack
    // doReturn(true).when(mockBoard).isLose();
    client1.send(mockBoard);
    client1.send(mockView);

    // // recv flag, end message
    // assertEquals((Flag)client1.recv(),Flag.correctFlag);
    int flag = (int) client1.recv();
    assertEquals(Flag.endFlag, flag);
    assertEquals("You are lose", (String) client1.recv());
    assertEquals("You are Win", (String) client1.recv());

    client1.closeMessenger();
    // client2.closeMessenger();
  }

  @Test
    public void test_endGameDetection() throws IOException{
      ServerSocket serverSocket = new ServerSocket(12300);
      RoomHandler roomHandler = new RoomHandler("a", serverSocket);
      Board mockBoard=mock(Board.class);
      doReturn(true,false).when(mockBoard).isLose();
      assertEquals(true, roomHandler.endGameDetection(mockBoard));
      assertEquals(false, roomHandler.endGameDetection(mockBoard));
    }

}
