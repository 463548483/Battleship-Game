package edu.duke.zw255.battleship.shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class TextPlayer {
  final String name;
  public BattleShipBoard theBoard;
  public BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  public HashMap<String, Integer> actionTimes;

  public TextPlayer(String name, BattleShipBoard theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.name = name;// player's name
    this.theBoard = theBoard;// own board
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    this.actionTimes = new HashMap<String, Integer>();
    setupShipCreationMap();
    setupShipCreationList();
    setupActionTime();
  }

  protected void setupActionTime() {
    actionTimes.put("F", theBoard.getHeight() * theBoard.getWidth());
    actionTimes.put("M", 3);
    actionTimes.put("S", 3);
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));

  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }

  public void doPlacementPhase() throws IOException {
    out.print(view.displayMyOwnBoard());
    String prompt = "Player " + name
        + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n \"Submarines\" ships that are 1x2 \n \"Destroyers\" that are 1x3 \n \"Battleships\" that are 1x4 \n \"Carriers\" that are 1x6\n";
    out.print(prompt);
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  public Placement readPlacement(String prompt) throws IOException {
    while (true) {
      try {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
          throw new EOFException("Emptry Input");
        }
        return new Placement(s);

      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }
    }
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    while (true) {
      try {
        String prompt = "Player " + name + " where would you like to put your " + shipName + " ?";
        Placement p = readPlacement(prompt);
        Ship<Character> p1 = createFn.apply(p);
        String res = theBoard.tryAddShip(p1);
        if (res != null) {
          throw new IllegalArgumentException(res);
        }
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }
    }
    out.print(view.displayMyOwnBoard());

  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    String myHeader = "Your ocean";
    String enemyHeader = "Enemy's ocean";
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    String prompt = "Possible actions for Player " + name + ":\n"
        + "F Fire at a square\n";
    if (actionTimes.get("M") > 0) {
      prompt += "M Move a ship to another square (" + actionTimes.get("M") + " remaining)\n";
    }
    if (actionTimes.get("S") > 0) {
      prompt += "S Sonar scan (" + actionTimes.get("S") + " remaining)\n";
    }
    prompt += "Player " + name + ", what would you like to do?\n";
    while (true) {
      try {
        out.println(prompt);
        String s = inputReader.readLine();
        if (actionTimes.containsKey(s) == false || actionTimes.get(s) == 0) {
          throw new EOFException("Action not exist");
        }
        actionTimes.put(s, actionTimes.get(s) - 1);
        if (s.equals("M")) {
          moveAction();
        } else if (s.equals("S")) {
          sonarAction(enemyBoard);
        } else {
          fireAction(enemyBoard);
        }
        break;
      } catch (EOFException e) {
        out.println(e.getMessage());
      }
    }

  }

  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Emptry Input");
    }
    Coordinate c = new Coordinate(s);
    if (c.valid(theBoard.getWidth(), theBoard.getHeight()) == false) {
      throw new IllegalArgumentException("Invalid Coordinate");
    }
    return c;
  }

  public void fireAction(Board<Character> enemyBoard) throws IOException {
    while (true) {
      try {
        Coordinate c = readCoordinate("Player " + name + ", where do you want fire at?");
        Ship<Character> ship = enemyBoard.fireAt(c);

        if (ship != null) {
          out.println("You hit a " + ship.getName() + "!");
        } else {
          out.println("You missed!");
        }
        if (enemyBoard.isLose()) {
          out.println("Player " + name + " have won!");
        }
        break;

      } catch (IllegalArgumentException e) {
        out.println("Please enter valid choice");
      }
    }
  }

  public void moveAction() throws IOException {
    while (true) {
      try {
        Coordinate c = readCoordinate("which ship do you want to move, please enter Coordinate?");
        Ship<Character> olds = theBoard.shipAt(c);
        theBoard.removeShip(olds);
        if (olds == null) {
          throw new IllegalArgumentException("There is no ship, please re-enter Coordinate\n");
        } else {
          while (true) {
            try {
              Placement p = readPlacement("Where do you want to move " + olds.getName());
              Function<Placement, Ship<Character>> createFn = shipCreationFns.get(olds.getName());

              Ship<Character> news = createFn.apply(p);
              String res = theBoard.tryAddShip(news);
              // out.print(view.displayMyOwnBoard());
              if (res != null) {
                throw new IllegalArgumentException(res);
              }
              news.moveShip(olds);
              break;
            } catch (IllegalArgumentException e) {
              out.println(e.getMessage());
            }
          }

          break;
        }
      } catch (IllegalArgumentException w) {
        out.println(w.getMessage());
      }
    }
  }

  public void sonarAction(Board<Character> enemyBoard) throws IOException {
    while (true) {
      try {
        Coordinate c = readCoordinate("which coordinate do you want to scan, please enter Coordinate?");
        HashMap<String, Integer> report = enemyBoard.sonarScan(c);
        String[] ships = { "Submarine", "Destroyer", "Battleship", "Carrier" };
        String prompt = "";
        for (String s : ships) {
          if (report.containsKey(s)) {
            prompt += s + "s occupy " + report.get(s) + " square\n";
          } else {
            prompt += s + "s occupy " + 0 + " square\n";
          }
        }
        out.println(prompt);
        break;
      } catch (IllegalArgumentException w) {
        out.println(w.getMessage());
      }
    }
  }

}
