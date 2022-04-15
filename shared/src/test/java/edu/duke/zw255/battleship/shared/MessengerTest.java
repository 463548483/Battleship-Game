package edu.duke.zw255.battleship.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.Test;

public class MessengerTest {

  @Test
  public void test_sendInfo() throws IOException, Exception {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket serverSocket = new ServerSocket(12345);
          Messenger serverMessenger = new Messenger(serverSocket);
          serverMessenger.send("ABC");
        } catch (Exception e) {

        }
      }
    };
    th.start();
    Thread.sleep(10); 
    Messenger messenger = new Messenger("127.0.0.1", 12345);
    String message = (String) messenger.recv();
    assertEquals("ABC", message);
    
    messenger.closeMessenger();
    th.interrupt();
    th.join();
  }
}

