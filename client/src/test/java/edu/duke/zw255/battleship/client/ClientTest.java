package edu.duke.zw255.battleship.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.zw255.battleship.shared.*;
import edu.duke.zw255.battleship.shared.Board;
import edu.duke.zw255.battleship.shared.Flag;
import edu.duke.zw255.battleship.shared.Messenger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.*;
import java.net.ServerSocket;

import javax.swing.border.Border;

public class ClientTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test_main() throws ClassNotFoundException, IOException {
        setUpStreams();
        Client client = mock(Client.class);
        String[] args = { "a" };
        Client.main(args);
        assertEquals(outContent.toString().equals("There should be two arguments: player name and server IP address"),false);
        restoreStreams();
        // doNothing().when(client).initOrJoinChoice();

    }

    @Test
    public void test_main2() throws InterruptedException, ClassNotFoundException, IOException {
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(12345);
                    Messenger serverMessenger = new Messenger(serverSocket);
                    assertEquals(((String)serverMessenger.recv()),"join");
                    assertEquals(((String)serverMessenger.recv()),"a");
                    serverMessenger.send(Flag.errorFlag);
                    assertEquals(((String)serverMessenger.recv()),"init");
                    assertEquals(((String)serverMessenger.recv()),"a");
                    serverMessenger.send(Flag.startFlag);
                    Board mockBoard= new BattleShipBoard<Character>(10, 20, 'X');
                    BoardTextView mockView=mock(BoardTextView.class);
                    serverMessenger.recv();
                    serverMessenger.recv();
                    serverMessenger.send(mockBoard);
                    serverMessenger.send(mockView);
                    serverMessenger.send(Flag.correctFlag);
                    serverMessenger.send(mockBoard);
                    serverMessenger.send(mockView);
                    serverMessenger.recv();
                    serverMessenger.recv();
                    serverMessenger.send(Flag.endFlag);
                    serverMessenger.send("win");
                    serverSocket.close();
                } catch (Exception e) {
                }
            }
        };
        th.start();
        Thread.sleep(10); 
        InputStream input = getClass().getClassLoader().getResourceAsStream("inputb.txt");
        assertNotNull(input);
        System.setIn(input);
        String[] param = { "a", "127.0.0.1" };
        Client.main(param);

    }

    @Test
    public void test_readPlayerInit() throws IOException{
        Client mockClient=mock(Client.class);
        PrintStream mockOut=mock(PrintStream.class);
        Messenger mockMessenger=mock(Messenger.class);
        mockClient.out=System.out;
        mockClient.messenger=mockMessenger;
        //doNothing().when(mockClient.out).println();
        doNothing().when(mockClient.messenger).send(anyString());
        BufferedReader mockIn=mock(BufferedReader.class);
        mockClient.inputReader=mockIn;
        BattleShipBoard mockBoard=mock(BattleShipBoard.class);
        mockClient.myboard=mockBoard;


        when(mockIn.readLine()).thenReturn("a,human");
        mockClient.readPlayerInit();
        assertEquals(mockClient.p.getClass(), TextPlayer.class);
        when(mockIn.readLine()).thenReturn("a,computer");
        mockClient.readPlayerInit();
        assertEquals(mockClient.p.getClass(), ComputerTextPlayer.class);
    }

    @Test
    public void test_main3() throws InterruptedException, ClassNotFoundException, IOException {
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(12345);
                    Messenger serverMessenger = new Messenger(serverSocket);
                    assertEquals(((String)serverMessenger.recv()),"join");
                    assertEquals(((String)serverMessenger.recv()),"a");
                    serverMessenger.send(Flag.correctFlag);
                    serverMessenger.send(Flag.startFlag);
                    Board mockBoard= new BattleShipBoard<Character>(10, 20, 'X');
                    BoardTextView mockView=mock(BoardTextView.class);
                    serverMessenger.recv();
                    serverMessenger.recv();
                    serverMessenger.send(mockBoard);
                    serverMessenger.send(mockView);
                    serverMessenger.send(Flag.correctFlag);
                    serverMessenger.send(mockBoard);
                    serverMessenger.send(mockView);
                    serverMessenger.recv();
                    serverMessenger.recv();
                    serverMessenger.send(Flag.endFlag);
                    serverMessenger.send("win");
                    serverSocket.close();
                } catch (Exception e) {
                }
            }
        };
        th.start();
        Thread.sleep(10); 
        InputStream input = getClass().getClassLoader().getResourceAsStream("inputa.txt");
        assertNotNull(input);
        System.setIn(input);
        String[] param = { "a", "127.0.0.1" };
        Client.main(param);

    }

}
