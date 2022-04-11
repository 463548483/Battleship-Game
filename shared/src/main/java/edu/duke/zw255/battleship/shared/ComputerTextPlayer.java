package edu.duke.zw255.battleship.shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

public class ComputerTextPlayer extends TextPlayer {
  private final Random randomFn;
  protected final HashSet<Coordinate> fireCoordinate;

  public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    super(name, theBoard, inputSource, out, shipFactory);
    randomFn = new Random(5);
    fireCoordinate = new HashSet<Coordinate>();
    setupFireCooridnate();
  }

  protected void setupFireCooridnate() {

    for (int r = 0; r < theBoard.getHeight(); r++) {
      for (int c = 0; c < theBoard.getWidth(); c++) {
        fireCoordinate.add(new Coordinate(r, c));
      }
    }
  }

  private Coordinate randomCoordinate() {
    int row = randomFn.nextInt(theBoard.getHeight());
    int column = randomFn.nextInt(theBoard.getWidth());
    return new Coordinate(row, column);
  }

  public Placement randomPlacement(String shipname) {
    if (shipname.equals("Submarine")  || shipname.equals("Destroyer")) {
      int i = randomFn.nextInt(2);
      char[] dir = { 'H', 'V' };
      return new Placement(randomCoordinate(), dir[i]);

    } else {
      int i = randomFn.nextInt(4);
      char[] dir = { 'U', 'D', 'L', 'R' };
      return new Placement(randomCoordinate(), dir[i]);
    }
  }

  @Override
  public void doPlacementPhase() throws IOException {
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  @Override
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    while (true) {
      try {
        Placement p = randomPlacement(shipName);
        Ship<Character> p1 = createFn.apply(p);
        String res = theBoard.tryAddShip(p1);
        if (res != null) {
          throw new IllegalArgumentException(res);
        }
        break;
      } catch (IllegalArgumentException e) {
        // out.println(e.getMessage());
      }
    }
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    while (true) {
      try {
        fireAction(enemyBoard);

        break;
      } catch (EOFException e) {
        // out.print(e.getMessage());
      }
    }

  }

  @Override
  public void fireAction(Board<Character> enemyBoard) throws IOException {
    while (true) {
      try {
        Coordinate c = fireCoordinate.iterator().next();
        fireCoordinate.remove(c);
        Ship<Character> ship = enemyBoard.fireAt(c);

        if (ship != null) {
          out.println("Player " + name + " hit enemy " + ship.getName() + " at " + c.print() + "!");
        } else {
          out.println("Player " + name + " missed!");
        }
        // if (enemyBoard.isLose()) {
        //   out.println("Player " + name + " have won!");
        // }
        break;

      } catch (IllegalArgumentException e) {
        // out.println("Please enter valid choice");
      }
    }
  }

}
