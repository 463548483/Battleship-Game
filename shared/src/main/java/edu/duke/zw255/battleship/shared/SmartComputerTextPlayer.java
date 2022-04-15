package edu.duke.zw255.battleship.shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SmartComputerTextPlayer extends ComputerTextPlayer {
    // this is the set for coordinate more likely to hit
    public HashSet<Coordinate> priorityHitSet;
    public Set<Coordinate> hitCoordinateSet;
    public Coordinate toMove;

    public SmartComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
            AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputSource, out, shipFactory);
        priorityHitSet = new HashSet<Coordinate>();
        hitCoordinateSet = new HashSet<Coordinate>();
    }

    @Override
    public void fireAction(Board<Character> enemyBoard) {
        while (true) {
            Coordinate c;
            if (priorityHitSet.size() > 0) {
                c = priorityHitSet.iterator().next();
                priorityHitSet.remove(c);
            } else {
                c = fireCoordinate.iterator().next();
            }

            fireCoordinate.remove(c);
            Ship<Character> ship = enemyBoard.fireAt(c);

            if (ship != null) {
                out.println("Player " + name + " hit enemy " + ship.getName() + " at " + c.print() + "!");
                Coordinate[] toAdd = new Coordinate[4];
                toAdd[0] = new Coordinate(c.getRow() + 1, c.getColumn());
                toAdd[1] = new Coordinate(c.getRow() - 1, c.getColumn());
                toAdd[2] = new Coordinate(c.getRow(), c.getColumn() + 1);
                toAdd[3] = new Coordinate(c.getRow(), c.getColumn() - 1);
                for (Coordinate needCheck : toAdd) {
                    if (needCheck.valid(theBoard.getWidth(), theBoard.getHeight())
                            && fireCoordinate.contains(needCheck)) {
                        priorityHitSet.add(needCheck);
                    }
                }

            } else {
                out.println("Player " + name + " missed!");
            }
            // if (enemyBoard.isLose()) {
            // out.println("Player " + name + " have won!");
            // }
            break;
        }
    }

    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) {
        while (true) {

            for (Object o : ((BattleShipBoard) theBoard).enemyHits.keySet()) {
                Coordinate currHit = (Coordinate) o;
                if (hitCoordinateSet.contains(currHit) == false) {
                    toMove = currHit;
                    moveAction();
                    hitCoordinateSet.add(currHit);
                    break;
                }
            }
            fireAction(enemyBoard);
            break;

        }

    }

    @Override
    public void moveAction() {
        Ship<Character> olds = theBoard.shipAt(toMove);
        theBoard.removeShip(olds);
            while (true) {
                try {
                    Function<Placement, Ship<Character>> createFn = shipCreationFns.get(olds.getName());
                    Ship<Character> news = createFn.apply(randomPlacement(olds.getName()));
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
        }


}
